package edu.dongguk.cs25server.domain

import edu.dongguk.cs25server.domain.type.ItemCategory
import edu.dongguk.cs25server.dto.response.StockResponseDto
import edu.dongguk.cs25server.dto.response.StoreResponseDto
import jakarta.persistence.*
import org.hibernate.annotations.DynamicUpdate
import java.sql.Timestamp
import java.time.LocalDateTime

@Entity
@DynamicUpdate
@Table(name = "item_HQ")
class ItemHQ(
    @Column(name = "item_name")
    var itemName: String,

    @Column(name = "price")
    var price: Long,

    @Column(name = "stock")
    var stock: Long = 0,

    @Enumerated(EnumType.STRING)
    var category: ItemCategory,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    var image: Image
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_hq_id")
    val id: Long? = null

    /*--------------------연관 관계 매핑--------------------*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "headquarters_id")
    val headquarters: Headquarters? = null

    @OneToMany(mappedBy = "itemHQ")
    lateinit var orderApplications: List<OrderApplication>

    @OneToMany(mappedBy = "itemHQ")
    lateinit var itemCSs: List<ItemCS>

    @OneToMany(mappedBy = "itemHQ")
    lateinit var warehousingApplications: List<WarehousingApplication>

    /*--------------------메서드--------------------*/
    fun toResponse(): StockResponseDto = StockResponseDto(
        item_id = this.id,
        item_name = this.itemName,
        supply_price = this.price,
        stock_quantity = this.stock,
        warehousing_date = if (warehousingApplications.size == 0) {
            null
        } else {
            warehousingApplications.get(warehousingApplications.size-1).getCreatedAt()
        }
    )
}