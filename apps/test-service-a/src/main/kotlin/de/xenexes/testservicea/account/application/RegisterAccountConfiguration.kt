package de.xenexes.testservicea.account.application

import de.xenexes.testservicea.account.domain.ports.inbound.RegisterAccountUseCase
import de.xenexes.testservicea.account.domain.ports.outbound.AccountRepositoryPort
import de.xenexes.testservicea.account.domain.ports.outbound.RoleRepositoryPort
import de.xenexes.testservicea.account.domain.services.RegisterAccountService
import de.xenexes.testservicea.common.domain.ports.DomainEventPublisher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
class RegisterAccountConfiguration {
    @Bean
    fun registerAccountUseCase(
        domainEventPublisher: DomainEventPublisher,
        accountRepositoryPort: AccountRepositoryPort,
        roleRepositoryPort: RoleRepositoryPort,
        passwordEncoder: BCryptPasswordEncoder,
    ): RegisterAccountUseCase {
        return RegisterAccountService(
            domainEventPublisher = domainEventPublisher,
            accountRepository = accountRepositoryPort,
            roleRepository = roleRepositoryPort,
            passwordEncoder = passwordEncoder::encode,
        )
    }
}
