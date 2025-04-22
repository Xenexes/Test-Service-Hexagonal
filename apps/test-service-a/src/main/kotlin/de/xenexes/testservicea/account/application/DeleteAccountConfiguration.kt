package de.xenexes.testservicea.account.application

import de.xenexes.testservicea.account.domain.ports.inbound.DeleteAccountUseCase
import de.xenexes.testservicea.account.domain.ports.outbound.AccountRepositoryPort
import de.xenexes.testservicea.account.domain.services.DeleteAccountUseCaseService
import de.xenexes.testservicea.common.domain.ports.DomainEventPublisher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DeleteAccountConfiguration {
    @Bean
    fun deleteAccountUseCase(
        accountRepository: AccountRepositoryPort,
        domainEventPublisher: DomainEventPublisher,
    ): DeleteAccountUseCase =
        DeleteAccountUseCaseService(
            accountRepository = accountRepository,
            domainEventPublisher = domainEventPublisher,
        )
}
