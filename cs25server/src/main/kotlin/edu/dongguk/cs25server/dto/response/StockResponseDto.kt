package edu.dongguk.cs25server.dto.response

class StockResponseDto(
    val item_id: Long,
    val item_name: String,
    val selling_price: Long,
    val stock_quantity: Long,
    val warehousing_date: String
)