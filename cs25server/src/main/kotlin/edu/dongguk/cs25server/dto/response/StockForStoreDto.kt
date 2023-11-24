package edu.dongguk.cs25server.dto.response

import java.time.LocalDate

data class StockForStoreDto(
        val id: Long,
        val name: String,
        val amount: Int,
        val price: LocalDate,
        val supplyDate: LocalDate,
        val category: String
)