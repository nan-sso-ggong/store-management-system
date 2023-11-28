package edu.dongguk.cs25server.dto.response

class PaymentResponse(
    val id: Long,
    val orderNumber: String,
    val itemName: String,
    val totalPrice: Int,
    val savedPoint: Int,
    val usedPoint: Int,
    val orderDate: String,
    val storeName: String,
    val storeAddress: String,
    val paymentType: String,
    val orderStatus: String
)