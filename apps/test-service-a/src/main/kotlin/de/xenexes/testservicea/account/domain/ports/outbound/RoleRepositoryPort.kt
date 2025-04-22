package de.xenexes.testservicea.account.domain.ports.outbound

import de.xenexes.testservicea.account.domain.model.Role

interface RoleRepositoryPort {
    fun findById(id: String): Role?

    fun findByName(name: String): Role?

    fun save(role: Role): Role
}
