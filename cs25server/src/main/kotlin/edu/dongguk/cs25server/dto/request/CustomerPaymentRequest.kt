package edu.dongguk.cs25server.dto.request

class CustomerPaymentRequest (
    val type: String? = null,
    val point: Int = 0,
    val items: List<ItemCSPaymentDto>
)