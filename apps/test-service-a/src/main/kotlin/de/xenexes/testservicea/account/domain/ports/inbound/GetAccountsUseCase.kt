package de.xenexes.testservicea.account.domain.ports.inbound

import de.xenexes.testservicea.account.domain.model.Account
import de.xenexes.testservicea.account.domain.model.AccountQueryFilter

fun interface GetAccountsUseCase {
    fun getAccounts(filter: AccountQueryFilter): List<Account>
}
