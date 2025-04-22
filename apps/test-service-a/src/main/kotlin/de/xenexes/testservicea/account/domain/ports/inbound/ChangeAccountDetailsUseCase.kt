package de.xenexes.testservicea.account.domain.ports.inbound

import de.xenexes.testservicea.account.domain.model.Account
import java.util.UUID

fun interface ChangeAccountDetailsUseCase {
    fun changeAccountDetails(accountId: UUID, newEmail: String?, newPassword: String?): Account
}
