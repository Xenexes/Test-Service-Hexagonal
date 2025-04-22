package de.xenexes.testservicea.account.domain.ports.outbound

import de.xenexes.testservicea.account.domain.model.Account
import de.xenexes.testservicea.account.domain.model.AccountId

interface AccountRepositoryPort {
    fun save(account: Account): Account

    fun findById(accountId: AccountId): Account?

    fun delete(accountId: AccountId)

    fun findByEmail(email: String): Account?

    fun findAll(): List<Account>
}
