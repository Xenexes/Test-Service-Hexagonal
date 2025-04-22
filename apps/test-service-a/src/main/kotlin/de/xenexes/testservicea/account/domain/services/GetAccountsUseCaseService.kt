package de.xenexes.testservicea.account.domain.services

import de.xenexes.testservicea.account.domain.model.Account
import de.xenexes.testservicea.account.domain.model.AccountQueryFilter
import de.xenexes.testservicea.account.domain.ports.inbound.GetAccountsUseCase
import de.xenexes.testservicea.account.domain.ports.outbound.AccountRepositoryPort
import org.springframework.security.core.context.SecurityContextHolder

class GetAccountsUseCaseService(private val accountRepository: AccountRepositoryPort) :
    GetAccountsUseCase {

    override fun getAccounts(filter: AccountQueryFilter): List<Account> {
        val auth = SecurityContextHolder.getContext().authentication
        val isAdmin = auth.authorities.any { it.authority == "ROLE_ADMIN" }

        return if (isAdmin) {
            // Admin can see all accounts, optionally filtered by accountId.
            val accounts = accountRepository.findAll()
            filter.searchTerm?.let { id -> accounts.filter { it.id.value.toString() == id } }
                ?: accounts
        } else {
            // Non-admin: allow only their own account.
            val currentEmail = auth.name
            val account = accountRepository.findByEmail(currentEmail)
            if (
                filter.searchTerm == null ||
                    (account != null && account.id.value.toString() == filter.searchTerm)
            ) {
                listOfNotNull(account)
            } else {
                emptyList()
            }
        }
    }
}
