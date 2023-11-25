package edu.dongguk.cs25server.dto.response


data class StockForStoreDto(
        val id: Long?,
        val name: String,
        val amount: Int,
        val price: Int,
        val category: String
)