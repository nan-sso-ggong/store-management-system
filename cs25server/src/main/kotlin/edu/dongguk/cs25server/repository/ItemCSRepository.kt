package edu.dongguk.cs25server.repository

import edu.dongguk.cs25server.domain.ItemCS
import edu.dongguk.cs25server.domain.Store
import edu.dongguk.cs25server.domain.type.ItemCategory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ItemCSRepository : JpaRepository<ItemCS, Long> {
    fun findByIdAndStore(itemId: Long, store: Store): ItemCS?
    fun findByStore(store: Store, paging: Pageable): Page<ItemCS>

    fun findByStoreAndCategory(store: Store, category: ItemCategory, paging: Pageable): Page<ItemCS>

    fun findByStoreAndNameContains(store: Store, name: String, paging: Pageable): Page<ItemCS>

    fun findAllByStoreAndNameContainsAndCategory(store: Store, name: String, category: ItemCategory, paging: Pageable): Page<ItemCS>

    @Query("SELECT i FROM ItemCS i WHERE i.store.id = :storeId AND i.id IN (:ids)")
    fun findByIdsAndStore(storeId: Long, ids: List<Long>): List<ItemCS>

    @Query("SELECT ih.id AS IHID, ic.id AS ICID, ic.name AS ICNAME, ih.category AS IHCG, ic.stock AS ICST, ih.price AS ICPR FROM ItemCS AS ic INNER JOIN ItemHQ AS ih ON ic.itemHQ = ih WHERE ic.store = :store AND (ic.name LIKE %:keyword% OR :keyword IS NULL) AND (ih.category = :category OR :category IS NULL)")
    fun findAllByKeywordAndCategory(@Param("store") store: Store, @Param("keyword") keyword: String?, @Param("category") category: ItemCategory?, pageable: Pageable): Page<OrderItemInfo>

    interface OrderItemInfo {
        fun getIHID(): Long
        fun getICID(): Long
        fun getICNAME(): String
        fun getIHCG(): ItemCategory
        fun getICST(): Int
        fun getICPR(): Int
    }
}