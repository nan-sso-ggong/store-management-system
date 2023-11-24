package edu.dongguk.cs25server.repository

import edu.dongguk.cs25server.domain.ItemCS
import edu.dongguk.cs25server.domain.Store
import edu.dongguk.cs25server.domain.type.ItemCategory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ItemCSRepository : JpaRepository<ItemCS, Long> {
    fun findByIdAndStore(itemId: Long, store: Store): ItemCS?
    fun findByStore(store: Store, paging: Pageable): Page<ItemCS>

    fun findByStoreAndItemCategory(store: Store, category: ItemCategory, paging: Pageable): Page<ItemCS>
}