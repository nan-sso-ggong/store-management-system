package edu.dongguk.cs25server.dto.response

import edu.dongguk.cs25server.domain.type.OrderStatus
import java.time.LocalDate
import java.time.LocalDateTime

class CustomerOrderResponseDto (
        val customer_name: String?, // Customer
        val order_id: Long?,        // Order
        val item_cu_fielditem_name: MutableList<String>?, // item_CS
        val payment_total_price: Int?,   // Order
        val order_created_at: LocalDateTime?,    // Order
        val order_state: String?     // Order
)