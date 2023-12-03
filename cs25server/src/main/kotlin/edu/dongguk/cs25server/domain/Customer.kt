package edu.dongguk.cs25server.domain

import edu.dongguk.cs25server.domain.type.LoginProvider
import edu.dongguk.cs25server.domain.type.Membership
import edu.dongguk.cs25server.domain.type.Membership.*
import edu.dongguk.cs25server.domain.type.UserRole
import edu.dongguk.cs25server.exception.ErrorCode
import edu.dongguk.cs25server.exception.GlobalException
import jakarta.persistence.*
import jakarta.persistence.CascadeType.*
import org.hibernate.annotations.DynamicUpdate
import java.time.LocalDateTime


@Entity
@DynamicUpdate
@Table(name = "customers")
class Customer (
    @Column(name = "name")
    val name: String,

    @Column(name = "social_id")
    private val socialId: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "login_provider")
    private val loginProvider: LoginProvider,

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    val role: UserRole = UserRole.CUSTOMER,

    @Enumerated(EnumType.STRING)
    @Column(name = "membership")
    private val membership: Membership = NORMAL,

    @Column(name = "point_balance")
    private var pointBalance: Int = 0,

    @Column(name = "is_valid")
    private var isValid: Boolean = true,

    @Column(name = "created_at", updatable = false)
    private val createdAt: LocalDateTime = LocalDateTime.now(),

    // 사용자 잔액, 임의로 넣어둠
    @Column(name = "balance")
    private var balance: Int = 100000
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private val id: Long? = null

    @Column(name = "is_login")
    private var isLogin: Boolean ?= null

    @Column(name = "refresh_token")
    private var refreshToken: String? = null

    @OneToMany(mappedBy = "customer", cascade = [ALL])
    private var orders: MutableList<Order> = mutableListOf()

    @OneToMany(mappedBy = "customer", cascade = [ALL])
    private var points: MutableList<Point> = mutableListOf()

    fun getId() = this.id

    fun getPointBalance() = this.pointBalance

    fun getBalance() = this.balance

    fun getOrders() = this.orders

    fun getPoints() = this.points

    fun payment(totalPrice: Int, usedPoint: Int): Int {
        val savedPointBalance: Int = when (this.membership) {
            NORMAL -> (totalPrice * 0.01).toInt()
            BRONZE -> (totalPrice * 0.02).toInt()
            SILVER -> (totalPrice * 0.04).toInt()
            GOLD -> (totalPrice * 0.06).toInt()
        }
        this.pointBalance -= (usedPoint - savedPointBalance)
        this.balance -= (totalPrice - usedPoint)

        return savedPointBalance
    }

    fun refund(savedPointBalance: Int, totalPrice: Int, usedPoint: Int) {
        this.pointBalance += (usedPoint - savedPointBalance)
        this.balance += (totalPrice - usedPoint)
    }

    fun checkBalance(totalPrice: Int, usePoint: Int) {
        if (this.balance - totalPrice < 0) {
            throw GlobalException(ErrorCode.NOT_ENOUGH_BALANCE_ERROR)
        }

        if (this.pointBalance - usePoint < 0) {
            throw GlobalException(ErrorCode.NOT_ENOUGH_POINT_BALANCE_ERROR)
        }
    }

    fun setLogin(refreshToken: String) {
        this.refreshToken = refreshToken
        this.isLogin = true
    }

    fun setLogout() {
        this.refreshToken = null
        this.isLogin = false
    }

}