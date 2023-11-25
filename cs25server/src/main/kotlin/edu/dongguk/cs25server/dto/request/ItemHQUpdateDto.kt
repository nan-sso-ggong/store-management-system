package edu.dongguk.cs25server.dto.request

import edu.dongguk.cs25server.domain.type.ItemCategory
import edu.dongguk.cs25server.domain.type.Supplier

class ItemHQUpdateDto(
    val item_name: String,
    val category: ItemCategory,
    val supplier: Supplier,
    val supply_price: Long
) {
}