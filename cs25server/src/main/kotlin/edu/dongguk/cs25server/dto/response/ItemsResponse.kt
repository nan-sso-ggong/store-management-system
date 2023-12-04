package edu.dongguk.cs25server.dto.response

class ItemsResponse(
    val id: Long?,
    val name: String,
    val price: Int,
    val thumbnail: String,
    val stock: Int
)