package edu.dongguk.cs25server.dto.request

class ItemCSRequest(
    val item_name: String,
    val selling_price: Int,
    val category: String, //
    val supplier: String // 동일
)