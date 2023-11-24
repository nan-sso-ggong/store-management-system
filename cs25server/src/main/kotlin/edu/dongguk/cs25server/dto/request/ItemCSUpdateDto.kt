package edu.dongguk.cs25server.dto.request

data class ItemCSUpdateDto(
        val itemId: Long,
        val amount: Int,
        val is_plus: Boolean
)