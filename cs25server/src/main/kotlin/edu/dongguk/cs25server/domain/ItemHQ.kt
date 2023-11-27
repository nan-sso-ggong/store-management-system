package edu.dongguk.cs25server.domain

import edu.dongguk.cs25server.domain.type.ItemCategory
import edu.dongguk.cs25server.domain.type.ReleaseStatus
import edu.dongguk.cs25server.domain.type.Supplier
import edu.dongguk.cs25server.dto.request.ItemHQUpdateDto
import edu.dongguk.cs25server.dto.response.*
import jakarta.persistence.*
import org.hibernate.annotations.DynamicUpdate
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

    @Column(name = "order_stock")
    private var orderStock: Long = 0,

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
            return warehousingApplications.get(size - 1).getCreatedAt()
        }
    }

    fun getOrderDate(): LocalDateTime? {
        val size = orderApplications.size
        if (size == 0) {
            return null
        } else {
            return orderApplications.get(size - 1).getCreatedAt()
        }
    }

    fun getImage(): Image {
        return this.image
    }

    fun getItemName(): String {
        return this.itemName
    }

    fun getSupplyPrice(): Long {
        return this.price
    }

    fun getSupplier(): Supplier {
        return this.supplier
    }

    fun getStock(): Long {
        return this.stock
    }

    fun getOrderApplications(): List<OrderApplication> {
        return this.orderApplications
    }

    fun updateItemHQ(itemHQUpdateDto: ItemHQUpdateDto, image: Image) {
        this.itemName = itemHQUpdateDto.item_name
        this.price = itemHQUpdateDto.supply_price
        this.category = itemHQUpdateDto.category
        this.supplier = itemHQUpdateDto.supplier
        this.image = image
    }

    fun updateOrderStock() {
        this.orderStock = this.orderApplications.map(OrderApplication::getCount).sum()
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

    fun toOrderResponse(): OrderResponseDto {
        updateOrderStock()
        return OrderResponseDto(
            itemId = this.id,
            itemName = this.itemName,
            supplyPrice = this.price,
            supplier = this.supplier.toString(),
            orderQuantity = orderStock,
            stockQuantity = stock,
            orderDate = getOrderDate(),
            stockStatus = if (stock < orderStock) {
                ReleaseStatus.LACK.toString()
            } else {
                ReleaseStatus.WAITING.toString()
            }
        )
    }

    fun toOrderStockResponse(): OrderStockResponseDto {
        updateOrderStock()
        return OrderStockResponseDto(
            itemId = this.id,
            itemName = this.itemName,
            supplyPrice = this.price,
            supplier = this.supplier.toString(),
            orderQuantity = orderStock,
            stockQuantity = this.stock
        )
    }
}