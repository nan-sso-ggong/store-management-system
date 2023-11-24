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
        @Column(name = "login_id", nullable = false)
        val loginId: String,

        @Column(name = "password", nullable = false)
        val password: String,

        @Column(name = "name", nullable = false)
        val name: String,

        @Column(name = "email", nullable = false)
        val email: String,

        @Column(name = "phone_number", nullable = false)
        val phoneNumber: String,

        @Enumerated(EnumType.STRING)
        @Column(name = "user_role", nullable = false)
        val userRole: UserRole,

        @Enumerated(EnumType.STRING)
        @Column(name = "membership", nullable = false)
        val memberShip: Membership,

        @Column(name = "created_at", nullable = false, updatable = false)
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


