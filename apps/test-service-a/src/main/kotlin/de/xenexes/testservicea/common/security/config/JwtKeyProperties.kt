package de.xenexes.testservicea.common.security.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "service.login.jwt.key")
data class JwtKeyProperties(
    /** JWT RS512 private key */
    var privateKey: String,
    /** JWT RS512 private key */
    var publicKey: String,
)
