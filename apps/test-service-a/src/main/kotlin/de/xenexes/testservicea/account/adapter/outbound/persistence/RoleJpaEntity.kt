package de.xenexes.testservicea.account.adapter.outbound.persistence

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "roles")
class RoleJpaEntity(@Id val id: String, @Column(nullable = false, unique = true) val name: String)
