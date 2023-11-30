package edu.dongguk.cs25server.domain

import edu.dongguk.cs25server.domain.type.OrderStatus
import edu.dongguk.cs25server.domain.type.ReleaseStatus
import edu.dongguk.cs25server.dto.response.OrderResponseDto
import edu.dongguk.cs25server.dto.response.ReleaseStockResponseDto
import jakarta.persistence.*
import org.hibernate.annotations.DynamicUpdate
import java.sql.Timestamp
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@DynamicUpdate
@Table(name = "order_application")
class OrderApplication(
    @Column(name = "count")
    private var count: Long,

    @Column(name = "created_at")
    private val createdAt: LocalDateTime,

    @Column(name = "is_stocked")
    private var isStocked: Boolean
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private val id: Long? = null

    @Column(name = "stocked_date")
    private var stockedDate: LocalDate? = null

    @Column(name = "release_status")
    @Enumerated(EnumType.STRING)
    private var releaseStatus: ReleaseStatus = ReleaseStatus.WAITING

    /*--------------------연관 관계 매핑--------------------*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_hq_id")
    private lateinit var itemHQ: ItemHQ

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private lateinit var store: Store

    /*--------------------메서드--------------------*/
    fun setItemHq(itemHQ: ItemHQ) {
        this.itemHQ = itemHQ
    }

    fun setStore(store: Store) {
        this.store = store
    }

    fun getCreatedAt():LocalDateTime {
        return this.createdAt
    }

    fun getCount(): Long {
        return this.count
    }

    fun getReleaseStatus(): ReleaseStatus {
        updateStatus()
        return this.releaseStatus
    }

    fun getItemHQ() = this.itemHQ

    // 출고 상태 로직 설계 필요
    fun updateStatus() {
        if (this.releaseStatus == ReleaseStatus.RELEASING)
            return
        if (isEnoughItemHQStock()) {
            this.releaseStatus = ReleaseStatus.WAITING
        }
        else {
            this.releaseStatus = ReleaseStatus.LACK
        }
    }

    fun releaseOrder() {
        this.releaseStatus = ReleaseStatus.RELEASING
        this.itemHQ.discountStock(this.count)
    }

    fun isEnoughItemHQStock() = this.count <= this.itemHQ.getStock()

    fun toReleaseStockResponse() = ReleaseStockResponseDto(
        order_id = this.id,
        item_name = this.itemHQ.getItemName(),
        supply_price = this.itemHQ.getSupplyPrice(),
        supplier = this.itemHQ.getSupplier().toString(),
        order_quantity = this.count,
        stock_quantity = this.itemHQ.getStock(),
        order_date = this.createdAt,
        stock_status = getReleaseStatus().toString(),
        store_name = this.store.name
    )
}