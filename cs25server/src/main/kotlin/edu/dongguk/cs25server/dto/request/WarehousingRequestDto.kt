package edu.dongguk.cs25server.dto.request

import edu.dongguk.cs25server.domain.type.Supplier

class WarehousingRequestDto(
    val item_id: Long,
    val additional_quantity: Long
) {
}