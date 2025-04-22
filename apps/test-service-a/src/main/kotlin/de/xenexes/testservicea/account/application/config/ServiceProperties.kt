package de.xenexes.testservicea.account.application.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "service")
data class ServiceProperties(
    var server: ServerProperties = ServerProperties(),
    var serviceEmail: String = "",
)

data class ServerProperties(var url: String = "")
