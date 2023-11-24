package edu.dongguk.cs25server.domain

import edu.dongguk.cs25server.domain.type.LoginProvider
import edu.dongguk.cs25server.domain.type.Membership
import edu.dongguk.cs25server.domain.type.UserRole
import jakarta.persistence.*
import org.hibernate.annotations.DynamicUpdate
import java.time.LocalDateTime

@Entity
@DynamicUpdate
@Table(name = "customers")
class Customer (
    @Column(name = "name")
    val name: String,

    @Column(name = "social_id")
    val socialId: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "login_provider")
    val loginProvider: LoginProvider,

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    val role: UserRole,

    @Enumerated(EnumType.STRING)
    @Column(name = "membership")
    val membership: Membership,

    @Column(name = "point")
    var point: Int,

    @Column(name = "is_valid")
    var isValid: Boolean,

    @Column(name = "created_at", updatable = false)
    val createdAt: LocalDateTime
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private val id: Long? = null
    @Column(name = "is_login")
    private var isLogin: Boolean ?= null
    @Column(name = "refresh_token")
    private var refreshToken: String? = null

    fun getId() = this.id

    fun setIsLogin(isLogin: Boolean) {
        this.isLogin = isLogin
    }

    fun setRefreshToken(refreshToken: String) {
        this.refreshToken = refreshToken
    }

    fun setLogin(refreshToken: String) {
        this.refreshToken = refreshToken
        this.isLogin = true
    }

}