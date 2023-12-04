package edu.dongguk.cs25server.domain

import edu.dongguk.cs25server.domain.type.ItemCategory
import edu.dongguk.cs25server.dto.response.ItemsResponse
import edu.dongguk.cs25server.dto.response.StockForStoreDto
import edu.dongguk.cs25server.exception.GlobalException
import edu.dongguk.cs25server.exception.ErrorCode
import jakarta.persistence.*
import org.hibernate.annotations.DynamicUpdate

@Entity
@DynamicUpdate
@Table(name = "item_CS")
class ItemCS(
        var name: String,

        var price: Int = 0,

        var stock: Int = 0,

        var isNewItem: Boolean = true,

        @Enumerated(EnumType.STRING)
        var category: ItemCategory,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "item_hq_id")
        var itemHQ: ItemHQ? = null,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "store_id")
        var store: Store? = null,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "image_id")
        var image: Image
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_cs_id")
    private val id: Long? = null

    @OneToMany(mappedBy = "itemCS", cascade = [CascadeType.ALL])
    private lateinit var orderItems: MutableList<OrderItemCS>

    fun getId() = this.id

    fun setStores(store: Store) {
        this.store = store
        store.getItemCS().add(this)
    }

    fun addStock(stock: Int) {
        this.stock += stock
    }

    fun checkStock(stock: Int) {
        if (this.stock - stock < 0) {
            throw GlobalException(ErrorCode.NOT_ENOUGH_STOCK_ERROR)
        }
    }

    fun removeStock(stock: Int) {
        val restStock = this.stock - stock
        if (restStock < 0) {
            throw GlobalException(ErrorCode.NOT_ENOUGH_STOCK_ERROR)
        }
        this.stock = restStock
    }

    fun toResponse(): StockForStoreDto = StockForStoreDto(
        id = this.id,
        name = this.name,
        amount = this.stock,
        price = this.price,
        category = this.category.toString()
    )

    fun toItemsResponse(): ItemsResponse {
        return ItemsResponse(
            id = this.id,
            name = this.name,
            price = this.price,
            thumbnail = this.image.getAccessUrl(),
            stock = this.stock
        )
    }

}


