package edu.dongguk.cs25server.repository

import edu.dongguk.cs25server.domain.ItemHQ
import edu.dongguk.cs25server.domain.type.ItemCategory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface ItemHQRepository: JpaRepository<ItemHQ, Long> {
    fun findAllByItemNameContainsAndCategory(itemName: String, category: ItemCategory?): List<ItemHQ>
    fun findAllByItemNameContains(itemName: String): List<ItemHQ>
}