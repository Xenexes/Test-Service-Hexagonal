package de.xenexes.testservicea.common.security

import io.jsonwebtoken.Jwts
import java.security.PrivateKey
import java.security.PublicKey
import java.util.Date

class JwtTokenProvider(
    private val privateKey: PrivateKey,
    private val publicKey: PublicKey,
    private val validityInMs: Long = 3600000, // 1 hour
) {
    companion object {
        private const val JWT_KEY_ROLES = "roles"
    }

    fun generateToken(email: String, roles: List<String>): String {
        val now = Date()
        val expiryDate = Date(now.time + validityInMs)
        return Jwts.builder()
            .subject(email)
            .claim(JWT_KEY_ROLES, roles)
            .issuedAt(now)
            .expiration(expiryDate)
            .signWith(privateKey)
            .compact()
    }

    @Suppress("TooGenericExceptionCaught", "SwallowedException")
    fun validateToken(token: String): Boolean {
        return try {
            Jwts.parser().verifyWith(publicKey).build().parseSignedClaims(token)
            true
        } catch (ex: Exception) {
            // log exception if needed
            false
        }
    }

    fun getEmailFromToken(token: String): String =
        Jwts.parser().verifyWith(publicKey).build().parseSignedClaims(token).payload.subject

    @Suppress("UNCHECKED_CAST")
    fun getRolesFromToken(token: String): List<String> {
        val claims = Jwts.parser().verifyWith(publicKey).build().parseSignedClaims(token)
        return claims.payload[JWT_KEY_ROLES] as? List<String> ?: emptyList()
    }
}
