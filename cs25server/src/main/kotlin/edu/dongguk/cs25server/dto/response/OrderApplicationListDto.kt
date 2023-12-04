package edu.dongguk.cs25server.dto.response

import edu.dongguk.cs25server.domain.type.ItemCategory

data class OrderApplicationListDto(
    val id: Long,
    val name: String,
    val category: String,
    val stock: Int,
    val price: Int,
    val count: Int
)