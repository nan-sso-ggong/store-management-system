package edu.dongguk.cs25server.domain

import edu.dongguk.cs25server.domain.type.UserRole
import jakarta.persistence.*
import org.hibernate.annotations.DynamicUpdate
import java.time.LocalDateTime

@Entity
@DynamicUpdate
@Table(name = "headquarters")
class Headquarters(
    @Column(name = "login_id")
    private val loginId: String,

    @Column(name = "password")
    private var password: String,

    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "email", nullable = false)
    val email: String,

    @Column(name = "phone_number", nullable = false)
    val phoneNumber: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    val role: UserRole = UserRole.HQ,

    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "headquarters_id")
    private val id: Long? = null

    @Column(name = "is_login")
    private var isLogin: Boolean ?= null

    @Column(name = "refresh_token")
    private var refreshToken: String? = null

    /*--------------------연관 관계 매핑--------------------*/
    @OneToMany(mappedBy = "headquarters")
    private val itemHQs: MutableList<ItemHQ> = mutableListOf()

    /*--------------------메서드--------------------*/
    fun getId() = this.id

    fun setLogin(refreshToken: String) {
        this.refreshToken = refreshToken
        this.isLogin = true
    }

    fun setLogout() {
        this.refreshToken = refreshToken
        this.isLogin = false
    }
}