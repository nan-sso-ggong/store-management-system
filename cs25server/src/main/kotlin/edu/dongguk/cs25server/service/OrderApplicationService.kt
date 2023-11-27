package edu.dongguk.cs25server.service

import edu.dongguk.cs25server.domain.ItemHQ
import edu.dongguk.cs25server.domain.type.ItemCategory
import edu.dongguk.cs25server.domain.type.Supplier
import edu.dongguk.cs25server.dto.response.ListResponseDto
import edu.dongguk.cs25server.dto.response.OrderResponseDto
import edu.dongguk.cs25server.dto.response.OrderStockResponseDto
import edu.dongguk.cs25server.dto.response.PageInfo
import edu.dongguk.cs25server.repository.ItemHQRepository
import edu.dongguk.cs25server.repository.OrderApplicationRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import kotlin.math.ceil

@Service
@Transactional
class OrderApplicationService(
    private val orderApplicationRepository: OrderApplicationRepository,
    private val itemHQRepository: ItemHQRepository
) {

}