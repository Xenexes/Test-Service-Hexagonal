package de.xenexes.testservicea.notification.application

import de.xenexes.testservicea.notification.domain.ports.outbound.MailSenderPort
import de.xenexes.testservicea.notification.domain.services.ActivationEmailService
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.web.ServerProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SendActivationEmailConfiguration {
    @Bean
    fun activationEmailConfiguration(
        mailSenderPort: MailSenderPort,
        serverPropertie: ServerProperties,
        @Value("\${service.server.url}") serverUrl: String,
    ): ActivationEmailService = ActivationEmailService(mailSenderPort, serverPropertie, serverUrl)
}
