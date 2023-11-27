package edu.dongguk.cs25server.dto.response

class OrderStockResponseDto(
    val item_id: Long?,
    val item_name: String,
    val supply_price: Long,
    val supplier: String,
    val order_quantity: Long,
    val stock_quantity: Long
) {
}