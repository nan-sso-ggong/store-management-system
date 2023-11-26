package edu.dongguk.cs25server.domain

import edu.dongguk.cs25server.domain.type.AllowStatus
import edu.dongguk.cs25server.domain.type.Membership
import edu.dongguk.cs25server.domain.type.UserRole
import jakarta.persistence.*
import org.hibernate.annotations.DynamicUpdate
import java.time.LocalDate
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
    @Column(name = "role", nullable = false)
    val role: UserRole = UserRole.MANAGER,

    @Enumerated(EnumType.STRING)
    @Column(name = "membership", nullable = false)
    val memberShip: Membership = Membership.NORMAL,

    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDate = LocalDate.now(),

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    var status: AllowStatus = AllowStatus.BEFORE
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manager_id")
    private val id: Long? = null

    @Column(name = "is_login")
    private var isLogin: Boolean? = null

    @Column(name = "refresh_token")
    private var refreshToken: String? = null

    @JoinColumn(name = "updated_at", nullable = false)
    private val updatedAt: LocalDateTime? = null

    @OneToMany(mappedBy = "manager")
    private val stores: List<Store> = ArrayList()

    fun getId() = this.id

    fun setLogin(refreshToken: String) {
        this.refreshToken = refreshToken
        this.isLogin = true
    }

    fun setLogout() {
        this.refreshToken = refreshToken
        this.isLogin = false
    }

    fun allowManager(status: AllowStatus) {
        this.status = status;
    }

}


