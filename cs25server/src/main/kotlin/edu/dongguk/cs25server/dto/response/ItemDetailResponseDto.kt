package edu.dongguk.cs25server.dto.response

import java.time.LocalDate

class ItemDetailResponseDto(
    val item_name: String,
    val category: String,
    val supply_price: Long,
    val stock_date: LocalDate?,
    val stock_quantity: Long,
    val supplier: String,
    val item_image_uuid: String
) {
}