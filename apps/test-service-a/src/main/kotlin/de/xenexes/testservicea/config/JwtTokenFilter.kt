package de.xenexes.testservicea.config

import de.xenexes.testservicea.common.security.JwtTokenProvider
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JwtTokenFilter(
    private val jwtTokenProvider: JwtTokenProvider,
    private val userDetailsService: CustomUserDetailsService,
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val header = request.getHeader(SecurityConfig.Companion.HEADER_AUTHORIZATION)
        if (header != null && header.startsWith(SecurityConfig.Companion.HEADER_BEARER_PREFIX)) {
            val token = header.substring(SecurityConfig.Companion.HEADER_BEARER_PREFIX.length)
            if (jwtTokenProvider.validateToken(token)) {
                val email = jwtTokenProvider.getEmailFromToken(token)
                val roles = jwtTokenProvider.getRolesFromToken(token)
                val authorities = roles.map { SimpleGrantedAuthority("ROLE_$it") }
                val userDetails = userDetailsService.loadUserByUsername(email) as CustomUserDetails
                val auth = UsernamePasswordAuthenticationToken(userDetails, null, authorities)
                SecurityContextHolder.getContext().authentication = auth
            }
        }
        filterChain.doFilter(request, response)
    }
}
