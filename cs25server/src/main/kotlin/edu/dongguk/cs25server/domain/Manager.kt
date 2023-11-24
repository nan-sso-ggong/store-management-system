package edu.dongguk.cs25server.domain

import edu.dongguk.cs25server.domain.type.Membership
import edu.dongguk.cs25server.domain.type.UserRole
import jakarta.persistence.*
import org.hibernate.annotations.DynamicUpdate
import java.time.LocalDateTime

@Entity
@DynamicUpdate
@Table(name = "managers")
class Manager(
        @JoinColumn(name = "login_id", nullable = false)
        val loginId: String,

        @JoinColumn(name = "password", nullable = false)
        val password: String,

        @JoinColumn(name = "name", nullable = false)
        val name: String,

        @JoinColumn(name = "email", nullable = false)
        val email: String,

        @JoinColumn(name = "phone_number", nullable = false)
        val phoneNumber: String,

        @Enumerated(EnumType.STRING)
        @JoinColumn(name = "user_role", nullable = false)
        val userRole: UserRole,

        @Enumerated(EnumType.STRING)
        @JoinColumn(name = "membership", nullable = false)
        val memberShip: Membership,

        @Column(updatable = false)
        @JoinColumn(name = "created_at", nullable = false)
        val createdAt: LocalDateTime = LocalDateTime.now(),
        ) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manager_id")
    private val id: Long? = null

    @JoinColumn(name = "updated_at", nullable = false)
    private val updatedAt: LocalDateTime? = null

    @OneToMany(mappedBy = "manager")
    private val stores: List<Store> = ArrayList()

}


