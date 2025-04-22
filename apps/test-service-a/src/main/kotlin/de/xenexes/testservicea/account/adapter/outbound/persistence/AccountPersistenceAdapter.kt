package de.xenexes.testservicea.account.adapter.outbound.persistence

import de.xenexes.testservicea.account.adapter.outbound.persistence.mapper.toDomain
import de.xenexes.testservicea.account.domain.model.Account
import de.xenexes.testservicea.account.domain.model.AccountId
import de.xenexes.testservicea.account.domain.ports.outbound.AccountRepositoryPort
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class AccountPersistenceAdapter(
    private val accountJpaRepository: AccountJpaRepository,
    private val roleJpaRepository: RoleJpaRepository,
) : AccountRepositoryPort {

    @Transactional
    override fun save(account: Account): Account {

        val roles = roleJpaRepository.findAllById(account.roles.map { it.id.value }).toSet()

        val entity =
            AccountJpaEntity(
                id = account.id.value,
                email = account.email,
                passwordHash = account.passwordHash,
                activated = account.activated,
                activationToken = account.activationToken,
                roles = roles,
            )
        return accountJpaRepository.save(entity).toDomain()
    }

    override fun findById(accountId: AccountId): Account? {
        val entity = accountJpaRepository.findById(accountId.value).orElse(null)
        return entity?.toDomain()
    }

    override fun delete(accountId: AccountId) {
        accountJpaRepository.deleteById(accountId.value)
    }

    override fun findByEmail(email: String): Account? {
        val entity = accountJpaRepository.findByEmail(email)
        return entity?.toDomain()
    }

    override fun findAll(): List<Account> {
        return accountJpaRepository.findAll().map { it.toDomain() }
    }
}
