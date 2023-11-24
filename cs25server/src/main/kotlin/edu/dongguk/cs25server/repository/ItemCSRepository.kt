package edu.dongguk.cs25server.repository

import edu.dongguk.cs25server.domain.ItemCS
import edu.dongguk.cs25server.domain.Store
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ItemCSRepository : JpaRepository<ItemCS, Long> {
    fun findByIdAndStoreOrNull(itemId: Long, store: Store): ItemCS?
}