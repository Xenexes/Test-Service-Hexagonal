package de.xenexes.testservicea.account.adapter.outbound.persistence

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "accounts")
class AccountJpaEntity(
    @Id val id: UUID,
    @Column(nullable = false, unique = true) val email: String,
    @Column(name = "password_hash", nullable = false) val passwordHash: String,
    @Column(nullable = false) val activated: Boolean,
    @Column(name = "activation_token") val activationToken: String? = null,
    @Column(name = "created_at", nullable = false) val createdAt: Instant = Instant.now(),
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "account_roles",
        joinColumns = [JoinColumn(name = "account_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")],
    )
    val roles: Set<RoleJpaEntity> = emptySet(),
)
