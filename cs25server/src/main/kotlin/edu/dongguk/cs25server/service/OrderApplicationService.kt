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
    // 입고 관리 발주 목록 조회
    fun readOrderRequest(
        itemName: String,
        category: String?,
        supplier: String?
    ): ListResponseDto<List<OrderResponseDto>> {
        val itemHQs: List<ItemHQ> = itemHQRepository.findAllByItemNameAndCategoryAndSupplier(
            itemName,
            ItemCategory.getCategory(category), Supplier.getSupplier(supplier)
        )

        val orderResponseDtos: List<OrderResponseDto> =
            itemHQs.map(ItemHQ::toOrderResponse).toList()
        return ListResponseDto(
            datalist = orderResponseDtos,
            page_info = PageInfo(
                page = 0,
                size = 0,
                total_elements = orderResponseDtos.size,
                total_pages = ceil(orderResponseDtos.size / 10.0).toInt()
            )
        )
    }

    // 재고 조회
    fun readOrderStocks(
        lack: Int,
        itemName: String,
        category: String?,
        supplier: String?
    ): ListResponseDto<List<OrderStockResponseDto>> {
        val itemHQs: List<ItemHQ> = if (lack == 1) {
            itemHQRepository.findAllByStockIsLessThanOrderSum()
        } else {
            itemHQRepository.findAllByItemNameAndCategoryAndSupplier(
                itemName, ItemCategory.getCategory(category), Supplier.getSupplier(supplier)
            )
        }

        val orderStockResponseDtos: List<OrderStockResponseDto> = itemHQs.map(ItemHQ::toOrderStockResponse).toList()
        return ListResponseDto(
            datalist = orderStockResponseDtos,
            page_info = PageInfo(
                page = 0,
                size = 0,
                total_elements = orderStockResponseDtos.size,
                total_pages = ceil(orderStockResponseDtos.size / 10.0).toInt()
            )
        )
    }
}