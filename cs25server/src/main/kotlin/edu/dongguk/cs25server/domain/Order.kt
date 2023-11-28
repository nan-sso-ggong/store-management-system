package edu.dongguk.cs25server.domain

import edu.dongguk.cs25server.domain.type.OrderStatus
import edu.dongguk.cs25server.domain.type.PaymentType
import edu.dongguk.cs25server.dto.response.PaymentResponse
import edu.dongguk.cs25server.exception.ErrorCode
import edu.dongguk.cs25server.exception.GlobalException
import jakarta.persistence.*
import jakarta.persistence.CascadeType.ALL
import jakarta.persistence.FetchType.LAZY
import jakarta.persistence.GenerationType.IDENTITY
import org.hibernate.annotations.DynamicUpdate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Entity
@Table(name = "orders")
@DynamicUpdate
class Order (

    @Column(name = "order_number")
    var orderNumber: String,

    @Column(name = "order_name")
    var orderName: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type")
    private var paymentType: PaymentType,

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private var orderStatus: OrderStatus = OrderStatus.READY,

    @Column(name = "created_at")
    private var createdAt: LocalDateTime = LocalDateTime.now()
) {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "order_id")
    private var id: Long? = null

    @Column(name = "total_price")
    private var totalPrice: Int = 0

    @Column(name = "used_point")
    private var usedPoint: Int = 0

    @Column(name = "final_price")
    private var finalPrice: Int = 0

    @Column(name = "updated_at")
    private var updatedAt: LocalDateTime? = null

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "customer_id")
    private lateinit var customer: Customer

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "store_id")
    private lateinit var store: Store

    @OneToMany(mappedBy = "order", cascade = [ALL])
    private var orderItems: MutableList<OrderItemCS> = mutableListOf()

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "point_id")
    private lateinit var point: Point

    fun getId() = this.id

    fun setPoint(point: Point) {
        this.point = point
    }

    fun updateOrderStatus(orderStatus: OrderStatus) {
        this.orderStatus = orderStatus
        this.updatedAt = LocalDateTime.now()
    }

    fun setCustomer(customer: Customer) {
        this.customer = customer
        customer.getOrders().add(this)
    }

    fun setStore(store: Store) {
        this.store = store
        store.getOrders().add(this)
    }

    fun setPrice(totalPrice: Int, usePoint: Int) {
        this.usedPoint = usePoint
        this.totalPrice = totalPrice
        this.finalPrice = totalPrice - usePoint
    }

    fun addOrderItem(orderItem: OrderItemCS) {
        this.orderItems.add(orderItem)
        orderItem.setOrder(this)
    }

    fun refund() {
        if (this.orderStatus == OrderStatus.PICKUP) {
            throw GlobalException(ErrorCode.ALREADY_PICKUP_ERROR)
        }
        this.updateOrderStatus(OrderStatus.CANCEL)

        for (orderItem in this.orderItems) {
            orderItem.cancel()
        }

        val savedPointBalance = this.point.getAmount()
        point.refund()

        this.customer.refund(savedPointBalance, totalPrice, usedPoint)

        this.usedPoint = 0
        this.totalPrice = 0
        this.finalPrice = 0
    }

    fun toHistoryResponse() : PaymentResponse {
        return PaymentResponse(
            id = this.id!!,
            orderNumber = this.orderNumber,
            itemName = this.orderName,
            totalPrice = this.totalPrice,
            savedPoint = this.point.getAmount(), // 적립 포인트
            usedPoint = this.usedPoint, //사용 포인트
            orderDate = DateTimeFormatter.ofPattern("yy.MM.dd HH:mm")
                .format(this.updatedAt ?: this.createdAt),
            storeName = this.store.name,
            storeAddress = this.store.address,
            paymentType = this.paymentType.toString(),
            orderStatus = this.orderStatus.toString()
        )
    }

    companion object {
        fun createOrder(
            customer: Customer, store: Store,
            usePoint: Int, totalPrice: Int,
            itemName: String, itemCount: Int,
            paymentType: PaymentType, orderItems: List<OrderItemCS>): Order {
            val order = Order(
                orderName = createOrderName(itemName, itemCount),
                orderNumber = createOrderNumber(customer.getId()!!),
                paymentType = paymentType,
                orderStatus = OrderStatus.READY
            )

            for (orderItem in orderItems) {
                order.addOrderItem(orderItem)
            }

            order.setCustomer(customer)
            order.setStore(store)
            order.setPrice(totalPrice, usePoint)

            return order
        }

        private fun createOrderNumber(customerId: Long): String {
            // CS + YYMMDD + RANDSTR(3) + CUSTOMERID(3)
            return ("CS" + DateTimeFormatter.ofPattern("yyMMdd").format(LocalDateTime.now())
                    + (1..3).map{ ('A'..'Z').random()}.joinToString("")
                    + String.format("%03d", customerId))
        }

        private fun createOrderName(itemName: String, itemCount: Int): String {
            if (itemCount - 1 <= 0) {
                return itemName
            }
            return itemName + "외 " + (itemCount - 1).toString() + "개"
        }
    }

}


