package de.xenexes.testservicea.common.adapter.outbound

import de.xenexes.testservicea.common.domain.DomainEvent
import de.xenexes.testservicea.common.domain.ports.DomainEventPublisher
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class SpringDomainEventPublisher(private val applicationEventPublisher: ApplicationEventPublisher) :
    DomainEventPublisher {
    override fun publish(event: DomainEvent) {
        applicationEventPublisher.publishEvent(event)
    }
}
