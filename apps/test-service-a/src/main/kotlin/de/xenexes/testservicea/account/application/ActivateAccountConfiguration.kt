package de.xenexes.testservicea.account.application

import de.xenexes.testservicea.account.domain.ports.inbound.ActivateAccountUseCase
import de.xenexes.testservicea.account.domain.ports.outbound.AccountRepositoryPort
import de.xenexes.testservicea.account.domain.services.ActivateAccountUseCaseService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ActivateAccountConfiguration {
    @Bean
    fun activateAccountUseCase(accountRepository: AccountRepositoryPort): ActivateAccountUseCase =
        ActivateAccountUseCaseService(accountRepository = accountRepository)
}
