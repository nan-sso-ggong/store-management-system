package edu.dongguk.cs25server.domain

import edu.dongguk.cs25server.domain.type.OrderStatus
import edu.dongguk.cs25server.domain.type.PaymentType
import jakarta.persistence.*
import org.hibernate.annotations.DynamicUpdate
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import java.util.*
import jakarta.persistence.CascadeType.ALL
import jakarta.persistence.FetchType.LAZY
import jakarta.persistence.GenerationType.IDENTITY

@Entity
@Table(name = "orders")
@DynamicUpdate
class Order {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "order_id")
    private var id: Long? = null

    @Column(name = "total_price")
    private var totalPrice: Int? = null

    @Column(name = "discount_price")
    private var discountPrice: Int? = null

    @Column(name = "final_price")
    private var finalPrice: Int? = null

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type")
    private var paymentType: PaymentType? = null

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private var orderStatus: OrderStatus? = null

    @Column(name = "created_at")
    private var createdAt: LocalDateTime? = null

    @Column(name = "updated_at")
    private var stateModifyAt: LocalDateTime? = null

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "customer_id")
    private var customer: Customer? = null

    @OneToMany(mappedBy = "order", cascade = [ALL])
    private var orderItems: List<OrderItemCS>? = null

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "point_id")
    private var point: Point?= null

    fun updateOrderStatus(orderStatus: OrderStatus) {
        this.orderStatus = orderStatus
        this.stateModifyAt = LocalDateTime.now()
    }

    fun createOrder(customer: Customer, vararg orderItems: OrderItemCS): Order {
        val order = Order()
        order.updateOrderStatus(OrderStatus.READY)
        return order
    }

    fun cancel() {
        if (this.orderStatus == OrderStatus.PICKUP) {
            throw IllegalStateException("이미 픽업된 상품은 취소가 불가능합니다.")
        }
        this.updateOrderStatus(OrderStatus.CANCEL)
        for (orderItem in orderItems!!) {
            orderItem.cancel()
        }
    }

}


