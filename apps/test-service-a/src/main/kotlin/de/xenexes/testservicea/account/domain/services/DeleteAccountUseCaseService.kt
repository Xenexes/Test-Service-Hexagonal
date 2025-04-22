package de.xenexes.testservicea.account.domain.services

import de.xenexes.testservicea.account.domain.events.AccountDeletedEvent
import de.xenexes.testservicea.account.domain.model.AccountId
import de.xenexes.testservicea.account.domain.ports.inbound.DeleteAccountUseCase
import de.xenexes.testservicea.account.domain.ports.outbound.AccountRepositoryPort
import de.xenexes.testservicea.common.domain.ports.DomainEventPublisher
import java.util.UUID

class DeleteAccountUseCaseService(
    private val accountRepository: AccountRepositoryPort,
    private val domainEventPublisher: DomainEventPublisher,
) : DeleteAccountUseCase {
    override fun deleteAccount(accountId: String) {
        val id = AccountId(UUID.fromString(accountId))
        accountRepository.delete(id)
        domainEventPublisher.publish(AccountDeletedEvent(accountId = id.value))
    }
}
