package de.xenexes.testservicea.account.application

import de.xenexes.testservicea.account.domain.ports.inbound.ChangeAccountDetailsUseCase
import de.xenexes.testservicea.account.domain.ports.outbound.AccountRepositoryPort
import de.xenexes.testservicea.account.domain.services.ChangeAccountDetailsService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
class ChangeAccountDetailsConfiguration {
    @Bean
    fun changeAccountDetailsUseCase(
        accountRepository: AccountRepositoryPort,
        passwordEncoder: BCryptPasswordEncoder,
    ): ChangeAccountDetailsUseCase =
        ChangeAccountDetailsService(accountRepository, passwordEncoder::encode)
}
