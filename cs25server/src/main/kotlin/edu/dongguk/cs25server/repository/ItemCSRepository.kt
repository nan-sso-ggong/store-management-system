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

    @Query(value = "SELECT * FROM item_cs i WHERE i.store_id=:storeId AND i.name LIKE %:name% AND i.category = :#{#category.name()}",
        countQuery = "SELECT count(i.item_cs_id) FROM item_cs i WHERE i.store_id=:storeId AND i.name LIKE %:name% AND i.category = :#{#category.name()}",
        nativeQuery = true)
    fun findAllByStoreAndCategoryAndNameContains(@Param("storeId") storeId: Long,
                                                 @Param("name") name: String,
                                                 @Param("category") category: ItemCategory,
                                                 paging: Pageable): Page<ItemCS>

}