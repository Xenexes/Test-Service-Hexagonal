package de.xenexes.testservicea.account.domain.ports.inbound

fun interface DeleteAccountUseCase {
    fun deleteAccount(accountId: String)
}
