package de.xenexes.testservicea.notification.domain.model

data class EmailMessage(val recipient: String, val subject: String, val body: String)
