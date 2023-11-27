package edu.dongguk.cs25server.dto.request

class CustomerPaymentRequest (
    val type: String? = null,
    val point: Int = 0,
    val totalPrice: Int = 0,
    val itemName: String,
    val itemCount: Int,
    val items: List<ItemCSPaymentDto>
)