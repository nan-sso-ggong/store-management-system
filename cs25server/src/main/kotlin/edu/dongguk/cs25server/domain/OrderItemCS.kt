package edu.dongguk.cs25server.domain

import jakarta.persistence.*
import org.hibernate.annotations.DynamicUpdate

@Entity
@DynamicUpdate
@Table(name = "order_item_cs")
class OrderItemCS(

    @Column(name = "order_price")
    var orderPrice: Int,

    @Column(name = "quantity")
    var quantity: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_cs_id")
    var itemCS: ItemCS,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    val id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private var order: Order? = null

    fun cancel() {
        this.itemCS.addStock(quantity)
    }

    fun setOrder(order: Order) {
        this.order = order
    }

    companion object {
        fun createOrderItem(item: ItemCS, itemPrice: Int, quantity: Int) : OrderItemCS {
            return OrderItemCS(
                orderPrice = itemPrice,
                quantity = quantity,
                itemCS = item,
            )
        }
    }

}


