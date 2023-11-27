package edu.dongguk.cs25server.dto.response

class OrderStockResponseDto(
    val itemId: Long?,
    val itemName: String,
    val supplyPrice: Long,
    val supplier: String,
    val orderQuantity: Long,
    val stockQuantity: Long
) {
}