package edu.dongguk.cs25server.dto.response

import edu.dongguk.cs25server.domain.type.ItemCategory

class OrderItemResponseDto(
        val item_cu_id: Long,
        val item_cu_fielditem_name: String,
        val item_hq_category: String,
        val item_cu_stock: Int,
        val item_hq_price: Int,
        val item_state: Boolean // Order_application 테이블에 해당 발주서 생성 여부
        )