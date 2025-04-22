package de.xenexes.testservicea.config

import de.xenexes.testservicea.account.domain.model.Account
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class CustomUserDetails(
    private val account: Account,
    private val authorities: Collection<GrantedAuthority>,
) : UserDetails {
    override fun getUsername() = account.email

    override fun getPassword() = account.passwordHash

    override fun getAuthorities() = authorities

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = account.activated
}
