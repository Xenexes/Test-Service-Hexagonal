package de.xenexes.testservicea.notification.adapter.outbound.mail

import de.xenexes.testservicea.notification.domain.model.EmailMessage
import de.xenexes.testservicea.notification.domain.ports.outbound.MailSenderPort
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component

@Component
class GmailMailSenderAdapter(private val javaMailSender: JavaMailSender) : MailSenderPort {
    override fun send(emailMessage: EmailMessage) {
        val message =
            SimpleMailMessage().apply {
                setTo(emailMessage.recipient)
                subject = emailMessage.subject
                text = emailMessage.body
            }
        javaMailSender.send(message)
    }
}
