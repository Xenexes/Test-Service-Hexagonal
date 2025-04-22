package de.xenexes.testservicea.account.application

import de.xenexes.testservicea.account.application.config.AdminAccountProperties
import de.xenexes.testservicea.account.application.config.ServiceProperties
import de.xenexes.testservicea.account.domain.ports.inbound.RegisterAdminAccountUseCase
import de.xenexes.testservicea.account.domain.ports.outbound.AccountRepositoryPort
import de.xenexes.testservicea.account.domain.ports.outbound.RoleRepositoryPort
import de.xenexes.testservicea.account.domain.services.RegisterAdminAccountService
import de.xenexes.testservicea.common.domain.ports.DomainEventPublisher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
class RegisterAdminAccountConfiguration {

    @Bean
    @Suppress("LongParameterList")
    fun registerAdminAccountUseCase(
        domainEventPublisher: DomainEventPublisher,
        accountRepositoryPort: AccountRepositoryPort,
        roleRepositoryPort: RoleRepositoryPort,
        passwordEncoder: BCryptPasswordEncoder,
        adminAccountProperties: AdminAccountProperties,
        serviceProperties: ServiceProperties,
    ): RegisterAdminAccountUseCase {
        return RegisterAdminAccountService(
            domainEventPublisher = domainEventPublisher,
            accountRepository = accountRepositoryPort,
            roleRepository = roleRepositoryPort,
            passwordEncoder = passwordEncoder::encode,
            serviceEmail = serviceProperties.serviceEmail,
            adminEmail = adminAccountProperties.email,
            adminPassword = adminAccountProperties.password,
        )
    }
}
