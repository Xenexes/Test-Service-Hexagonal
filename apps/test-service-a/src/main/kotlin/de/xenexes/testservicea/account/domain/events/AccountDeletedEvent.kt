package de.xenexes.testservicea.account.domain.events

import de.xenexes.testservicea.common.domain.DomainEvent
import java.time.Instant
import java.util.UUID

data class AccountDeletedEvent(
    val accountId: UUID,
    override val occurredAt: Instant = Instant.now(),
) : DomainEvent
