package de.xenexes.testservicea.config

import de.xenexes.testservicea.common.security.JwtTokenProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
class SecurityConfig(
    private val jwtTokenProvider: JwtTokenProvider,
    private val activationCheckFilter: ActivationCheckFilter,
    private val customUserDetailsService: CustomUserDetailsService,
) {
    companion object {
        const val HEADER_AUTHORIZATION = "Authorization"
        const val HEADER_BEARER_PREFIX = "Bearer "
    }

    private fun corsConfigurationSource(): CorsConfigurationSource? {
        val configuration = CorsConfiguration()
        configuration.allowedMethods = listOf("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH")
        configuration.allowCredentials = true
        configuration.allowedHeaders = listOf("*")
        configuration.addAllowedOriginPattern("*")
        configuration.addExposedHeader("Authorization")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain =
        http
            .cors { it.configurationSource(corsConfigurationSource()) }
            .csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests { authorize ->
                authorize.requestMatchers(HttpMethod.GET, "/accounts/*/activate").permitAll()
                authorize.requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                authorize.anyRequest().authenticated()
            }
            .addFilterBefore(
                JwtTokenFilter(jwtTokenProvider, customUserDetailsService),
                UsernamePasswordAuthenticationFilter::class.java,
            )
            .addFilterAfter(activationCheckFilter, JwtTokenFilter::class.java)
            .userDetailsService(customUserDetailsService)
            .build()
}
