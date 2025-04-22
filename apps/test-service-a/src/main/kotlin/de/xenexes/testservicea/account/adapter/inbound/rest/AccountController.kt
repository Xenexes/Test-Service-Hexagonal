package de.xenexes.testservicea.account.adapter.inbound.rest

import de.xenexes.testservicea.account.adapter.inbound.rest.mapper.toDomain
import de.xenexes.testservicea.account.adapter.inbound.rest.mapper.toResponse
import de.xenexes.testservicea.account.adapter.inbound.rest.model.AccountResponse
import de.xenexes.testservicea.account.adapter.inbound.rest.model.ChangeAccountDetailsRequest
import de.xenexes.testservicea.account.adapter.inbound.rest.model.GetAccountsQueryFilter
import de.xenexes.testservicea.account.adapter.inbound.rest.model.RegisterAccountRequest
import de.xenexes.testservicea.account.domain.ports.inbound.ChangeAccountDetailsUseCase
import de.xenexes.testservicea.account.domain.ports.inbound.DeleteAccountUseCase
import de.xenexes.testservicea.account.domain.ports.inbound.GetAccountsUseCase
import de.xenexes.testservicea.account.domain.ports.inbound.RegisterAccountUseCase
import java.util.UUID
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/accounts")
class AccountController(
    private val registerAccount: RegisterAccountUseCase,
    private val getAccounts: GetAccountsUseCase,
    private val changeDetails: ChangeAccountDetailsUseCase,
    private val deleteAccount: DeleteAccountUseCase,
) {

    @PostMapping
    fun register(@RequestBody dto: RegisterAccountRequest): ResponseEntity<AccountResponse> {
        val account = registerAccount.registerAccount(dto.email, dto.password)
        return ResponseEntity.ok(account.toResponse())
    }

    @GetMapping
    fun list(
        @RequestParam(required = false) searchTerm: String?
    ): ResponseEntity<List<AccountResponse>> {
        val accounts = getAccounts.getAccounts(GetAccountsQueryFilter(searchTerm).toDomain())
        return ResponseEntity.ok(accounts.map { it.toResponse() }.toList())
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: UUID,
        @RequestBody dto: ChangeAccountDetailsRequest,
    ): ResponseEntity<AccountResponse> {
        val updatedAccount = changeDetails.changeAccountDetails(id, dto.newEmail, dto.newPassword)
        return ResponseEntity.ok(updatedAccount.toResponse())
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String): ResponseEntity<Void> {
        deleteAccount.deleteAccount(id)
        return ResponseEntity.noContent().build()
    }
}
