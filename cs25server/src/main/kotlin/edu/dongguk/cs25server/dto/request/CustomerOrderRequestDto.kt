package edu.dongguk.cs25server.dto.request

import java.time.LocalDate

class CustomerOrderRequestDto (
        val order_state: String?,
        val start_date: LocalDate?,
        val end_date: LocalDate?,
        val page: Long = 0
)