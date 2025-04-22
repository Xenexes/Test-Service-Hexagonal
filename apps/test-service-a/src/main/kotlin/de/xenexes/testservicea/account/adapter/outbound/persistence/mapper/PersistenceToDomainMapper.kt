package de.xenexes.testservicea.account.adapter.outbound.persistence.mapper

import de.xenexes.testservicea.account.adapter.outbound.persistence.AccountJpaEntity
import de.xenexes.testservicea.account.domain.model.Account
import de.xenexes.testservicea.account.domain.model.AccountId
import de.xenexes.testservicea.account.domain.model.Role
import de.xenexes.testservicea.account.domain.model.RoleId

fun AccountJpaEntity.toDomain(): Account =
    Account(
        id = AccountId(this.id),
        email = this.email,
        passwordHash = this.passwordHash,
        activated = this.activated,
        activationToken = this.activationToken,
        roles = this.roles.map { Role(RoleId(it.id), it.name) }.toSet(),
    )
