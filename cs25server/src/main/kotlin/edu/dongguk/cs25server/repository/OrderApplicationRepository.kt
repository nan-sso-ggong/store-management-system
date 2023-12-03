package edu.dongguk.cs25server.repository

import edu.dongguk.cs25server.domain.ItemHQ
import edu.dongguk.cs25server.domain.OrderApplication
import edu.dongguk.cs25server.domain.Store
import edu.dongguk.cs25server.domain.type.ItemCategory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface OrderApplicationRepository : JpaRepository<OrderApplication, Long> {
    fun findAllByItemHQIsIn(itemHQs: List<ItemHQ>): List<OrderApplication>
    fun findAllByStoreIn(stores: List<Store>): List<OrderApplication>
    fun existsByStoreAndItemHQ(store: Store, itemHQ: ItemHQ): Boolean

    @Query(
        "select i.item_name as NAME, i.category as CATEGORY, ic.stock as STOCK ,i.price as PRICE, o.count as AMOUNT " +
                "from order_application o " +
                "inner join item_hq i on o.item_hq_id = i.item_hq_id " +
                "inner join item_cs ic on i.item_hq_id = ic.item_hq_id " +
                "where o.store_id = :storeId",
        countQuery = "select count(*) " +
                "from order_application o " +
                "inner join item_hq i on o.item_hq_id = i.item_hq_id " +
                "inner join item_cs ic on i.item_hq_id = ic.item_hq_id " +
                "where o.store_id = :storeId", nativeQuery = true
    )
    fun findByStore(@Param("storeId") storeId: Long, paging: Pageable): Page<OrderInfo>

    interface OrderInfo {
        fun getName(): String
        fun getCategory(): ItemCategory
        fun getStock(): Int
        fun getPrice(): Int
        fun getAmount(): Int

    }
}