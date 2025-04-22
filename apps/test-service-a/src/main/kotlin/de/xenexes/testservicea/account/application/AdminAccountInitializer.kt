package de.xenexes.testservicea.account.application

import de.xenexes.testservicea.account.domain.ports.inbound.RegisterAdminAccountUseCase
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
@ConditionalOnProperty(
    prefix = "service.account.creation.admin",
    name = ["enabled"],
    havingValue = "true",
    matchIfMissing = false,
)
class AdminAccountInitializer(
    private val registerAdminAccountUseCase: RegisterAdminAccountUseCase
) {

    @EventListener(ApplicationReadyEvent::class)
    fun onApplicationReady() {
        registerAdminAccountUseCase.registerAdminAccount()
    }
}
