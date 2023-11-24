package edu.dongguk.cs25server.dto.response

import java.time.LocalDate
import java.time.LocalDateTime

data class StockForStoreDto(
        val id: Long?,
        val name: String,
        val amount: Int,
        val price: Int,
        val category: String
)