package edu.dongguk.cs25server.dto.request

import edu.dongguk.cs25server.domain.type.Supplier

class WarehousingRequestDto(
    val item_id: Long,
    val item_name: String,
    val supply_price: Long,
    val supplier: Supplier,
    val order_quantity: Long,
    val stock_quantity: Long,
    val additional_quantity: Long
) {
}