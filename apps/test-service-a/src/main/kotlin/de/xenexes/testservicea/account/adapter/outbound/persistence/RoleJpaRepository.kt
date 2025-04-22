package de.xenexes.testservicea.account.adapter.outbound.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface RoleJpaRepository : JpaRepository<RoleJpaEntity, String> {
    fun findByName(name: String): RoleJpaEntity?
}
