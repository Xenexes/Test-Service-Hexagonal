package de.xenexes.testservicea.config

import de.xenexes.testservicea.account.domain.ports.outbound.AccountRepositoryPort
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(private val accountRepository: AccountRepositoryPort) :
    UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val account =
            accountRepository.findByEmail(username)
                ?: throw UsernameNotFoundException("User not found with email: $username")
        if (!account.activated) {
            throw UsernameNotFoundException("User is not activated")
        }
        val authorities = account.roles.map { SimpleGrantedAuthority("ROLE_${it.name}") }
        return CustomUserDetails(account, authorities)
    }
}
