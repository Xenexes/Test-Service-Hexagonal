package de.xenexes.testservicea.account.adapter.inbound.rest.model

data class ChangeAccountDetailsRequest(
    val newEmail: String? = null,
    val newPassword: String? = null,
)
