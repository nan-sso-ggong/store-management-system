package edu.dongguk.cs25server.service

import edu.dongguk.cs25server.domain.ItemHQ
import edu.dongguk.cs25server.domain.OrderApplication
import edu.dongguk.cs25server.domain.type.ItemCategory
import edu.dongguk.cs25server.domain.type.Supplier
import edu.dongguk.cs25server.dto.response.ListResponseDto
import edu.dongguk.cs25server.dto.response.OrderResponseDto
import edu.dongguk.cs25server.dto.response.PageInfo
import edu.dongguk.cs25server.repository.ItemHQRepository
import edu.dongguk.cs25server.repository.OrderApplicationRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class OrderApplicationService(
    private val orderApplicationRepository: OrderApplicationRepository,
    private val itemHQRepository: ItemHQRepository
) {
    // 입고 관리 발주 목록 조회
    fun readOrderRequest(itemName: String, category: String?, supplier: String?): ListResponseDto<List<OrderResponseDto>> {
        val itemHQs: List<ItemHQ> = itemHQRepository.findAllByItemNameAndCategoryAndSupplier(itemName,
            ItemCategory.getCategory(category), Supplier.getSupplier(supplier))
        val orderApplications = orderApplicationRepository.findAllByItemHQIsIn(itemHQs)

        val orderResponseDtos: List<OrderResponseDto> = orderApplications.map(OrderApplication::toOrderResponse).toList()
        return ListResponseDto(
            datalist = orderResponseDtos,
            pageInfo = PageInfo(
                page = 0,
                size = 0,
                totalElements = orderResponseDtos.size,
                totalPages = Math.ceil(orderResponseDtos.size / 10.0).toInt()
            )
        )
    }
}