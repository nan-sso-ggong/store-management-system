package edu.dongguk.cs25server.dto.response

import java.time.LocalDateTime

class ReleaseStockResponseDto(
    val order_id: Long?,
    val item_name: String,
    val supply_price: Long,
    val supplier: String,
    val order_quantity: Long,
    val stock_quantity: Long,
    val order_date: LocalDateTime,
    val stock_status: String,
    val store_name: String
) {
}