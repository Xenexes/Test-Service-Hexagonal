package de.xenexes.testservicea.account.adapter.inbound.rest

import de.xenexes.testservicea.account.adapter.inbound.rest.model.LoginRequest
import de.xenexes.testservicea.account.adapter.inbound.rest.model.LoginResponse
import de.xenexes.testservicea.account.domain.ports.outbound.AccountRepositoryPort
import de.xenexes.testservicea.common.security.JwtTokenProvider
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthenticationController(
    private val accountRepository: AccountRepositoryPort,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider,
) {
    @PostMapping("/auth/login")
    fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<LoginResponse> =
        accountRepository
            .findByEmail(loginRequest.email)
            ?.takeIf { passwordEncoder.matches(loginRequest.password, it.passwordHash) }
            ?.let { account ->
                val roles = account.roles.map { it.id.value }
                val token = jwtTokenProvider.generateToken(account.email, roles)
                ResponseEntity.ok(LoginResponse(token))
            } ?: ResponseEntity.badRequest().build()
}
