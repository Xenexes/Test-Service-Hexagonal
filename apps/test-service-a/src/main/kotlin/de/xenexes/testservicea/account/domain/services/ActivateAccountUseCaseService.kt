package de.xenexes.testservicea.account.domain.services

import de.xenexes.testservicea.account.domain.exceptions.AccountNotFoundException
import de.xenexes.testservicea.account.domain.model.Account
import de.xenexes.testservicea.account.domain.model.AccountId
import de.xenexes.testservicea.account.domain.ports.inbound.ActivateAccountUseCase
import de.xenexes.testservicea.account.domain.ports.outbound.AccountRepositoryPort
import java.util.UUID

class ActivateAccountUseCaseService(private val accountRepository: AccountRepositoryPort) :
    ActivateAccountUseCase {
    override fun activateAccount(accountId: String, token: String): Account {
        val id = AccountId(UUID.fromString(accountId))
        val account =
            accountRepository.findById(id)
                ?: throw AccountNotFoundException("Account with id $accountId not found")
        val activatedAccount = account.activate(token)
        return accountRepository.save(activatedAccount)
    }
}
