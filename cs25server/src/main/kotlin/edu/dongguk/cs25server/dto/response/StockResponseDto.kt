package edu.dongguk.cs25server.dto.response

import java.sql.Timestamp

class StockResponseDto(
    val item_id: Long?,
    val item_name: String,
    val supply_price: Long,
    val stock_quantity: Long,
    val warehousing_date: Timestamp?
)