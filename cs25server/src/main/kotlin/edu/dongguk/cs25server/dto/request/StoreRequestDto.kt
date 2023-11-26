package edu.dongguk.cs25server.dto.request

data class StoreRequestDto(
        val name: String,
        val address: String,
        val callNumber: String,
        val thumbnail: String,
)