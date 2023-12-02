package edu.dongguk.cs25server.dto.response

class StoreDetailResponseDto(
        val store_id: Long?,
        val store_image: String,
        val store_name: String,
        val address: String,
        val store_tel: String
)