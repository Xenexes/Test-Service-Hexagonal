package de.xenexes.testservicea.common.domain.ports

import de.xenexes.testservicea.common.domain.DomainEvent

interface DomainEventPublisher {
    fun publish(event: DomainEvent)
}
