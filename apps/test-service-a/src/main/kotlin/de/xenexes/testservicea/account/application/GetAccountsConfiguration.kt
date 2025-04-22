package de.xenexes.testservicea.account.application

import de.xenexes.testservicea.account.domain.ports.inbound.GetAccountsUseCase
import de.xenexes.testservicea.account.domain.ports.outbound.AccountRepositoryPort
import de.xenexes.testservicea.account.domain.services.GetAccountsUseCaseService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GetAccountsConfiguration {

    @Bean
    fun getAccountsUseCase(accountRepository: AccountRepositoryPort): GetAccountsUseCase =
        GetAccountsUseCaseService(accountRepository)
}
