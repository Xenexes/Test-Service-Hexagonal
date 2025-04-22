package de.xenexes.testservicea.account.adapter.inbound.rest.mapper

import de.xenexes.testservicea.account.adapter.inbound.rest.model.AccountResponse
import de.xenexes.testservicea.account.adapter.inbound.rest.model.GetAccountsQueryFilter
import de.xenexes.testservicea.account.domain.model.Account
import de.xenexes.testservicea.account.domain.model.AccountQueryFilter

fun Account.toResponse(): AccountResponse =
    AccountResponse(id = this.id.value, email = this.email, roles = this.roles.map { it.name })

fun GetAccountsQueryFilter.toDomain(): AccountQueryFilter =
    AccountQueryFilter(searchTerm = searchTerm)
