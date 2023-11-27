package edu.dongguk.cs25server.repository

import edu.dongguk.cs25server.domain.ItemHQ
import edu.dongguk.cs25server.domain.type.ItemCategory
import edu.dongguk.cs25server.domain.type.Supplier
import edu.dongguk.cs25server.dto.response.OrderResponseDto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ItemHQRepository: JpaRepository<ItemHQ, Long> {
    @Query("SELECT ih FROM ItemHQ AS ih WHERE ih.itemName LIKE %:itemName% AND (ih.category = :category OR :category IS NULL) " +
                "AND (ih.supplier = :supplier OR :supplier IS NULL)")
    fun findAllByItemNameAndCategoryAndSupplier(itemName: String, category: ItemCategory?, supplier: Supplier?): List<ItemHQ>
    @Query("SELECT ih FROM ItemHQ AS ih WHERE ih.itemName LIKE %:itemName% AND (ih.category = :category OR :category IS NULL)")
    fun findAllByItemNameAndCategory(@Param("itemName") itemName: String, @Param("category") category: ItemCategory?): List<ItemHQ>
    fun findAllByItemNameContains(itemName: String): List<ItemHQ>
    @Query("SELECT ih FROM ItemHQ AS ih WHERE ih.stock < (SELECT SUM(oa.count) FROM OrderApplication AS oa " +
            "WHERE oa.itemHQ = ih GROUP BY oa.itemHQ\n)")
    fun findAllByStockIsLessThanOrderSum(): List<ItemHQ>
}