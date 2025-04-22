package de.xenexes.testservicea.account.domain.services

import de.xenexes.testservicea.account.domain.events.AccountCreatedEvent
import de.xenexes.testservicea.account.domain.exceptions.AccountNotFoundException
import de.xenexes.testservicea.account.domain.exceptions.ActivationTokenNotFoundException
import de.xenexes.testservicea.account.domain.model.Account
import de.xenexes.testservicea.account.domain.model.AccountId
import de.xenexes.testservicea.account.domain.ports.inbound.RegisterAdminAccountUseCase
import de.xenexes.testservicea.account.domain.ports.outbound.AccountRepositoryPort
import de.xenexes.testservicea.account.domain.ports.outbound.RoleRepositoryPort
import de.xenexes.testservicea.common.DEFAULT_ROLE_ADMIN
import de.xenexes.testservicea.common.domain.ports.DomainEventPublisher
import java.util.UUID
import org.slf4j.LoggerFactory

@Suppress("LongParameterList")
class RegisterAdminAccountService(
    private val domainEventPublisher: DomainEventPublisher,
    private val accountRepository: AccountRepositoryPort,
    private val roleRepository: RoleRepositoryPort,
    private val passwordEncoder: (String) -> String,
    private val serviceEmail: String,
    private val adminEmail: String,
    private val adminPassword: String,
) : RegisterAdminAccountUseCase {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun registerAdminAccount() {
        if (accountRepository.findByEmail(adminEmail) != null) {
            logger.info("✅ Admin account already exists with email: $adminEmail")
            return
        }

        createAndPublishAdminAccount()

        logger.info("✅ Admin account created successfully with email: $adminEmail")
    }

    private fun createAndPublishAdminAccount() {
        val adminRole =
            roleRepository.findById(DEFAULT_ROLE_ADMIN)
                ?: throw AccountNotFoundException("Role $DEFAULT_ROLE_ADMIN not found")

        val account =
            Account(
                id = AccountId(),
                email = adminEmail,
                passwordHash = passwordEncoder(adminPassword),
                activated = false,
                activationToken = UUID.randomUUID().toString(),
                roles = setOf(adminRole),
            )

        val savedAccount = accountRepository.save(account)

        domainEventPublisher.publish(
            AccountCreatedEvent(
                accountId = savedAccount.id.value,
                email = serviceEmail,
                activationToken =
                    savedAccount.activationToken
                        ?: throw ActivationTokenNotFoundException(
                            "Activation token for account ${savedAccount.id.value} not found"
                        ),
            )
        )
    }
}
