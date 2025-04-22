package de.xenexes.testservicea.notification.domain.ports.outbound

import de.xenexes.testservicea.notification.domain.model.EmailMessage

interface MailSenderPort {
    fun send(emailMessage: EmailMessage)
}
