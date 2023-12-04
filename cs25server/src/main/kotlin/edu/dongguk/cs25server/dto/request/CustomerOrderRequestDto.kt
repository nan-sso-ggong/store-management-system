package edu.dongguk.cs25server.dto.request

import java.time.LocalDateTime

class CustomerOrderRequestDto (
        val order_state: String? = null,
        val start_date: LocalDateTime? = null,
        val end_date: LocalDateTime? = null,
        val page: Long = 0,
        val size: Int = 7,
)