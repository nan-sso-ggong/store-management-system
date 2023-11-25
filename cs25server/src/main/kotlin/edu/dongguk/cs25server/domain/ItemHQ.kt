package edu.dongguk.cs25server.domain

import edu.dongguk.cs25server.domain.type.ItemCategory
import edu.dongguk.cs25server.domain.type.Supplier
import edu.dongguk.cs25server.dto.response.ItemDetailResponseDto
import edu.dongguk.cs25server.dto.response.StockResponseDto
import edu.dongguk.cs25server.dto.response.StoreResponseDto
import jakarta.persistence.*
import org.hibernate.annotations.DynamicUpdate
import java.sql.Timestamp
import java.time.LocalDate
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
    @Column(name = "category")
    var category: ItemCategory,

    @Enumerated(EnumType.STRING)
    @Column(name = "supplier")
    var supplier: Supplier,

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
    fun getWarehousingDate(): LocalDate? {
        val size = warehousingApplications.size
        if (size == 0) {
            return null
        } else {
            return warehousingApplications.get(size-1).getCreatedAt()
        }
    }

    fun toStockResponse(): StockResponseDto = StockResponseDto(
        itemId = this.id,
        itemName = this.itemName,
        supplyPrice = this.price,
        stockQuantity = this.stock,
        warehousingDate = this.getWarehousingDate()
    )

    fun toItemDetailResponse(): ItemDetailResponseDto = ItemDetailResponseDto(
        itemName = this.itemName,
        category = this.category.toString(),
        supplyPrice = this.price,
        stockDate = this.getWarehousingDate(),
        stockQuantity = this.stock,
        supplier = this.supplier.toString(),
        itemImageUuid = this.image.getUuidName()
    )
}