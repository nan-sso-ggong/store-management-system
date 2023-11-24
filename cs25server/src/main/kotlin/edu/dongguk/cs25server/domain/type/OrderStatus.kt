package edu.dongguk.cs25server.domain.type

enum class OrderStatus(private val orderStatus: String) {
    READY("READY"), PICKUP("PICKUP"), CANCEL("CANCEL")
}