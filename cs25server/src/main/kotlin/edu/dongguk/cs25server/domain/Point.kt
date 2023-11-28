package edu.dongguk.cs25server.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "points")
class Point (
    // 시점 포인트
    @Column(name = "balance")
    private var balance: Int = 0,

    // 적립 / 차감 금액
    @Column(name = "amount")
    private var amount: Int = 0,

    @Column(name = "is_save")
    private var isSave: Boolean = true,

    @Column(name = "created_at", updatable = false)
    private val createdAt: LocalDateTime = LocalDateTime.now()
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_id")
    private val id: Long? = null

    @Column(name = "updated_at")
    private var updatedAt: LocalDateTime? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private var customer: Customer? = null

    fun getAmount() = this.amount
    fun setCustomer(customer: Customer) {
        this.customer = customer
        customer.getPoints().add(this)
    }

    fun refund() {
        this.isSave = false
        this.balance -= this.amount
        this.amount = 0
    }

}