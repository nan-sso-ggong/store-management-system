package edu.dongguk.cs25server.dto.response

data class RequestStoreListDto(
    val id: Long,
    val name: String,
    val manager_name: String,
    val address: String,
    val created_at: String
)
