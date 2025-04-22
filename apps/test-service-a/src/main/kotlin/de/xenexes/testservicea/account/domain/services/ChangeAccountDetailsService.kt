package de.xenexes.testservicea.account.domain.services

import de.xenexes.testservicea.account.domain.exceptions.AccountNotFoundException
import de.xenexes.testservicea.account.domain.exceptions.EmailAlreadyExistsException
import de.xenexes.testservicea.account.domain.model.Account
import de.xenexes.testservicea.account.domain.model.AccountId
import de.xenexes.testservicea.account.domain.ports.inbound.ChangeAccountDetailsUseCase
import de.xenexes.testservicea.account.domain.ports.outbound.AccountRepositoryPort
import de.xenexes.testservicea.account.domain.validation.AccountValidator
import java.util.UUID

class ChangeAccountDetailsService(
    private val accountRepository: AccountRepositoryPort,
    private val passwordEncoder: (String) -> String,
) : ChangeAccountDetailsUseCase {

    override fun changeAccountDetails(
        accountId: UUID,
        newEmail: String?,
        newPassword: String?,
    ): Account {
        val id = AccountId(accountId)
        val account =
            accountRepository.findById(id)
                ?: throw AccountNotFoundException("Account with id $accountId not found")

        var updatedAccount = account

        newEmail?.let { email ->
            AccountValidator.validateEmail(email)
            validateExistingEmail(email, account)
            updatedAccount = updatedAccount.copy(email = email)
        }

        newPassword?.let { password ->
            AccountValidator.validatePassword(password)
            val encodedPassword = passwordEncoder(password)
            updatedAccount = updatedAccount.copy(passwordHash = encodedPassword)
        }

        return accountRepository.save(updatedAccount)
    }

    private fun validateExistingEmail(email: String, account: Account) {
        val existingAccount = accountRepository.findByEmail(email)
        if (existingAccount != null && existingAccount.id != account.id) {
            throw EmailAlreadyExistsException("Email already exists: $email")
        }
    }
}
