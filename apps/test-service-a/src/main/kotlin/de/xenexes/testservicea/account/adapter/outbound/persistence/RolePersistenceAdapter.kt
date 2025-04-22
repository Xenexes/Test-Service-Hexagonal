package de.xenexes.testservicea.account.adapter.outbound.persistence

import de.xenexes.testservicea.account.domain.model.Role
import de.xenexes.testservicea.account.domain.model.RoleId
import de.xenexes.testservicea.account.domain.ports.outbound.RoleRepositoryPort
import kotlin.jvm.optionals.getOrNull
import org.springframework.stereotype.Component

@Component
class RolePersistenceAdapter(private val roleJpaRepository: RoleJpaRepository) :
    RoleRepositoryPort {
    override fun findById(id: String): Role? =
        roleJpaRepository.findById(id).getOrNull()?.let { Role(RoleId(it.id), it.name) }

    override fun findByName(name: String): Role? =
        roleJpaRepository.findByName(name)?.let { Role(RoleId(it.id), it.name) }

    override fun save(role: Role): Role {
        val entity = RoleJpaEntity(id = role.id.value, name = role.name)
        val saved = roleJpaRepository.save(entity)
        return Role(RoleId(saved.id), saved.name)
    }
}
