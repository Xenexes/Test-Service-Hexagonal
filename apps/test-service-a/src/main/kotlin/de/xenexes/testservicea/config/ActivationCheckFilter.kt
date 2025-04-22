package de.xenexes.testservicea.config

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.util.AntPathMatcher
import org.springframework.web.filter.OncePerRequestFilter

@Component
class ActivationCheckFilter(private val pathMatcher: AntPathMatcher = AntPathMatcher()) :
    OncePerRequestFilter() {

    private val unrestrictedPathPatterns = setOf("/auth/login", "/accounts/*/activate")

    override fun shouldNotFilter(request: HttpServletRequest): Boolean =
        unrestrictedPathPatterns.any { pattern -> pathMatcher.match(pattern, request.servletPath) }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        SecurityContextHolder.getContext()
            .authentication
            ?.principal
            .takeIf { it is CustomUserDetails }
            ?.let { principal ->
                if (!(principal as CustomUserDetails).isEnabled) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Account not activated")
                    return
                }
            }
        filterChain.doFilter(request, response)
    }
}
