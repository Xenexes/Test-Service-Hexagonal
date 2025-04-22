package de.xenexes.testservicea.common.security

import de.xenexes.testservicea.common.security.config.JwtKeyProperties
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.PublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.Base64
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JwtConfig(private val jwtKeyProperties: JwtKeyProperties) {

    @Bean
    fun jwtTokenProvider(): JwtTokenProvider {
        val privateKey = convertPemToPrivateKey(pem = jwtKeyProperties.privateKey)
        val publicKey = convertPemToPublicKey(pem = jwtKeyProperties.publicKey)
        return JwtTokenProvider(privateKey = privateKey, publicKey = publicKey)
    }

    private fun convertPemToPrivateKey(pem: String): PrivateKey {
        val decodedKey = Base64.getDecoder().decode(pem)
        val keySpec = PKCS8EncodedKeySpec(decodedKey)
        val keyFactory = KeyFactory.getInstance("RSA")
        return keyFactory.generatePrivate(keySpec)
    }

    private fun convertPemToPublicKey(pem: String): PublicKey {
        val decodedKey = Base64.getDecoder().decode(pem)
        val keySpec = X509EncodedKeySpec(decodedKey)
        val keyFactory = KeyFactory.getInstance("RSA")
        return keyFactory.generatePublic(keySpec)
    }
}
