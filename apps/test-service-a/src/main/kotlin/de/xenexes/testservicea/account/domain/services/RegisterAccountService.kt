package de.xenexes.testservicea.account.domain.services

import de.xenexes.testservicea.account.domain.events.AccountCreatedEvent
import de.xenexes.testservicea.account.domain.exceptions.AccountNotFoundException
import de.xenexes.testservicea.account.domain.exceptions.ActivationTokenNotFoundException
import de.xenexes.testservicea.account.domain.model.Account
import de.xenexes.testservicea.account.domain.model.AccountId
import de.xenexes.testservicea.account.domain.ports.inbound.RegisterAccountUseCase
import de.xenexes.testservicea.account.domain.ports.outbound.AccountRepositoryPort
import de.xenexes.testservicea.account.domain.ports.outbound.RoleRepositoryPort
import de.xenexes.testservicea.account.domain.validation.AccountValidator
import de.xenexes.testservicea.common.DEFAULT_ROLE_USER
import de.xenexes.testservicea.common.domain.ports.DomainEventPublisher
import java.util.UUID

class RegisterAccountService(
    private val domainEventPublisher: DomainEventPublisher,
    private val accountRepository: AccountRepositoryPort,
    private val roleRepository: RoleRepositoryPort,
    private val passwordEncoder: (String) -> String,
) : RegisterAccountUseCase {
    override fun registerAccount(email: String, password: String): Account {
        AccountValidator.validateEmail(email)
        AccountValidator.validatePassword(password)

        accountRepository.findByEmail(email)?.let {
            throw IllegalArgumentException("An account with email $email already exists.")
        }

        val encodedPassword = passwordEncoder(password)
        val userRole =
            roleRepository.findById(DEFAULT_ROLE_USER)
                ?: throw AccountNotFoundException("Role with id USER not found")
        val account =
            Account(
                id = AccountId(),
                email = email,
                passwordHash = encodedPassword,
                activated = false,
                activationToken = UUID.randomUUID().toString(),
                roles = setOf(userRole),
            )
        val savedAccount = accountRepository.save(account)
        domainEventPublisher.publish(
            AccountCreatedEvent(
                accountId = savedAccount.id.value,
                email = savedAccount.email,
                activationToken =
                    savedAccount.activationToken
                        ?: throw ActivationTokenNotFoundException(
                            "Activation token for account ${savedAccount.id.value} not found"
                        ),
            )
        )

        return savedAccount
    }
}
