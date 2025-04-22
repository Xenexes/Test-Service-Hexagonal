package de.xenexes.testservicea.notification.domain.services

import de.xenexes.testservicea.account.domain.events.AccountCreatedEvent
import de.xenexes.testservicea.notification.domain.model.EmailMessage
import de.xenexes.testservicea.notification.domain.ports.outbound.MailSenderPort
import org.springframework.boot.autoconfigure.web.ServerProperties

class ActivationEmailService(
    private val mailSenderPort: MailSenderPort,
    private val serverProperties: ServerProperties,
    private val serverUrl: String,
) {
    fun sendActivationEmail(event: AccountCreatedEvent) {
        val activationLink =
            "$serverUrl:${serverProperties.port}/accounts/${event.accountId}/activate?token=${event.activationToken}"
        val subject = "Activate Your Account"
        val body =
            """
            Hello,
            
            Thank you for signing up. Please activate your account by clicking the link below:
            $activationLink
            
            Regards,
            The Test-Service-A Team
        """
                .trimIndent()
        val emailMessage = EmailMessage(recipient = event.email, subject = subject, body = body)
        mailSenderPort.send(emailMessage)
    }
}
