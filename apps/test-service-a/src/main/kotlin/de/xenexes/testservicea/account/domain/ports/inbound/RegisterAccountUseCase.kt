package de.xenexes.testservicea.account.domain.ports.inbound

import de.xenexes.testservicea.account.domain.model.Account

fun interface RegisterAccountUseCase {
    fun registerAccount(email: String, password: String): Account
}
