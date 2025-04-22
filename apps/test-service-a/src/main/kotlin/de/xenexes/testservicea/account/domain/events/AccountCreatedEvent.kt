package de.xenexes.testservicea.account.domain.events

import de.xenexes.testservicea.common.domain.DomainEvent
import java.time.Instant
import java.util.UUID

data class AccountCreatedEvent(
    val accountId: UUID,
    val email: String,
    val activationToken: String,
    override val occurredAt: Instant = Instant.now(),
) : DomainEvent
