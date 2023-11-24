package edu.dongguk.cs25server.domain

import jakarta.persistence.*
import org.hibernate.annotations.DynamicUpdate

@Entity
@DynamicUpdate
@Table(name = "order_item_cs")
class OrderItemCS(
        var orderPrice: Int,
        var count: Int,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "item_cs_id")
        var itemCS: ItemCS,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "order_id")
        var order: Order
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    val id: Long? = null

    fun cancel() {
        this.itemCS.addStock(count)
    }

}


