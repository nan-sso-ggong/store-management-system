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
    val name: String,
    val socialId: String,
    @Enumerated(EnumType.STRING)
    val loginProvider: LoginProvider,
    @Enumerated(EnumType.STRING)
    val role: UserRole,
    @Enumerated(EnumType.STRING)
    val membership: Membership,
    var point: Int,
    var isValid: Boolean,
    @Column(updatable = false)
    val createdAt: LocalDateTime
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private val id: Long? = null
    private var updatedAt: LocalDateTime? = null
    private var isLogin: Boolean ?= null
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