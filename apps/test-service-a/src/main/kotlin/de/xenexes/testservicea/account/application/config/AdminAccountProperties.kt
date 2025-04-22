package de.xenexes.testservicea.account.application.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "service.account.creation.admin")
data class AdminAccountProperties(
    /** Admin account creation on start up is enabled */
    var enabled: Boolean = false,
    /** Mail address for the new admin account* */
    var email: String = "",
    /** Password for the new admin account* */
    var password: String = "",
)
