package edu.dongguk.cs25server.dto.response

import java.time.LocalDateTime

class OrderResponseDto(
    val itemId: Long?,
    val itemName: String,
    val supplyPrice: Long,
    val supplier: String,
    val orderQuantity: Long,
    val stockQuantity: Long,
    val orderDate: LocalDateTime?,
    val stockStatus: String
) {
}