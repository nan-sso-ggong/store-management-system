package edu.dongguk.cs25server.dto.response

import java.time.LocalDate

class ItemDetailResponseDto(
    val itemName: String,
    val category: String,
    val supplyPrice: Long,
    val stockDate: LocalDate?,
    val stockQuantity: Long,
    val supplier: String,
    val itemImageUuid: String
) {
}