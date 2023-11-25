package edu.dongguk.cs25server.domain

import edu.dongguk.cs25server.domain.type.ItemCategory
import edu.dongguk.cs25server.domain.type.Supplier
import edu.dongguk.cs25server.dto.request.ItemHQUpdateDto
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
    private var itemName: String,

    @Column(name = "price")
    private var price: Long,

    @Column(name = "stock")
    private var stock: Long = 0,

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private var category: ItemCategory,

    @Enumerated(EnumType.STRING)
    @Column(name = "supplier")
    private var supplier: Supplier,

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "image_id")
    private var image: Image
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_hq_id")
    private val id: Long? = null

    /*--------------------연관 관계 매핑--------------------*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "headquarters_id")
    private val headquarters: Headquarters? = null

    @OneToMany(mappedBy = "itemHQ")
    private lateinit var orderApplications: List<OrderApplication>

    @OneToMany(mappedBy = "itemHQ")
    private lateinit var itemCSs: List<ItemCS>

    @OneToMany(mappedBy = "itemHQ")
    private lateinit var warehousingApplications: List<WarehousingApplication>

    /*--------------------메서드--------------------*/
    fun getWarehousingDate(): LocalDate? {
        val size = warehousingApplications.size
        if (size == 0) {
            return null
        } else {
            return warehousingApplications.get(size-1).getCreatedAt()
        }
    }

    fun getImage(): Image {
        return this.image
    }

    fun updateItemHQ(itemHQUpdateDto: ItemHQUpdateDto, image: Image) {
        this.itemName = itemHQUpdateDto.item_name
        this.price = itemHQUpdateDto.supply_price
        this.category = itemHQUpdateDto.category
        this.supplier = itemHQUpdateDto.supplier
        this.image = image
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