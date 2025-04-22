package de.xenexes.testservicea.account.domain.ports.inbound

import de.xenexes.testservicea.account.domain.model.Account

fun interface ActivateAccountUseCase {
    fun activateAccount(accountId: String, token: String): Account
}
