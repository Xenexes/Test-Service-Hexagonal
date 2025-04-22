package de.xenexes.testservicea.account.adapter.outbound.persistence

import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository

interface AccountJpaRepository : JpaRepository<AccountJpaEntity, UUID> {
    fun findByEmail(email: String): AccountJpaEntity?
}
