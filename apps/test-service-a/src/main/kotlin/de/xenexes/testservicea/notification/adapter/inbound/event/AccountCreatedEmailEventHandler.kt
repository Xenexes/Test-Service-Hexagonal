package de.xenexes.testservicea.notification.adapter.inbound.event

import de.xenexes.testservicea.account.domain.events.AccountCreatedEvent
import de.xenexes.testservicea.notification.domain.services.ActivationEmailService
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class AccountCreatedEmailEventHandler(private val activationEmailService: ActivationEmailService) {
    @EventListener
    fun handleAccountCreatedEvent(event: AccountCreatedEvent) {
        activationEmailService.sendActivationEmail(event)
    }
}
