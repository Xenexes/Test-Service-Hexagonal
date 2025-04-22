package de.xenexes.testservicea.account.adapter.inbound.rest

import de.xenexes.testservicea.account.domain.ports.inbound.ActivateAccountUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ActivationController(private val activateAccountUseCase: ActivateAccountUseCase) {

    @GetMapping("/accounts/{accountId}/activate")
    fun activateAccount(
        @PathVariable accountId: String,
        @RequestParam token: String,
    ): ResponseEntity<String> {
        val activatedAccount = activateAccountUseCase.activateAccount(accountId, token)
        return ResponseEntity.ok("Account for ${activatedAccount.email} activated successfully!")
    }
}
