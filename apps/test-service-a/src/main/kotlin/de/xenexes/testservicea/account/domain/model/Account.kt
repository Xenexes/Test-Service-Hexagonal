package de.xenexes.testservicea.account.domain.model

import de.xenexes.testservicea.account.domain.exceptions.InvalidActivationTokenException

data class Account(
    val id: AccountId = AccountId(),
    val email: String,
    val passwordHash: String,
    val activated: Boolean = false,
    val activationToken: String? = null,
    val roles: Set<Role> = emptySet(),
) {
    fun activate(providedToken: String): Account {
        if (activationToken == providedToken) {
            return copy(activated = true)
        } else {
            throw InvalidActivationTokenException("Invalid activation token for account $id")
        }
    }
}
