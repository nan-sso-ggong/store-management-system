package edu.dongguk.cs25server.dto.response

import java.time.LocalDate

data class RequestManagerListDto(
    val id: Long,
    val name: String,
    val phoneNumber: String,
    val address: String,
    val created_at: String
)
