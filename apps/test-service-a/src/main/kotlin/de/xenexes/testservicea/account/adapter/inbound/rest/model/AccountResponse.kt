package de.xenexes.testservicea.account.adapter.inbound.rest.model

import java.util.UUID

data class AccountResponse(val id: UUID, val email: String, val roles: List<String>)
