package edu.dongguk.cs25server.service

import edu.dongguk.cs25server.domain.OrderApplication
import edu.dongguk.cs25server.domain.Store
import edu.dongguk.cs25server.domain.type.ItemCategory
import edu.dongguk.cs25server.dto.response.ListResponseDto
import edu.dongguk.cs25server.dto.response.OrderItemResponseDto
import edu.dongguk.cs25server.dto.response.PageInfo
import edu.dongguk.cs25server.dto.response.PickupOrderRequestDto
import edu.dongguk.cs25server.exception.ErrorCode
import edu.dongguk.cs25server.exception.GlobalException
import edu.dongguk.cs25server.repository.ItemCSRepository
import edu.dongguk.cs25server.repository.ItemHQRepository
import edu.dongguk.cs25server.repository.OrderApplicationRepository
import edu.dongguk.cs25server.repository.StoreRepository
import edu.dongguk.cs25server.util.Log.Companion.log
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
class OrderCSService(private val storeRepository: StoreRepository, private val itemHQRepository: ItemHQRepository, private val orderApplicationRepository: OrderApplicationRepository, private val itemCSRepository: ItemCSRepository) {
    // 발주 목록 조회
    fun readItemOrders(storeId: Long, keyword: String?, category: String?, pageIndex: Long): ListResponseDto<List<OrderItemResponseDto>> {
        val store: Store = storeRepository.findByIdOrNull(storeId) ?: throw GlobalException(ErrorCode.NOT_FOUND_STORE)
        val paging = PageRequest.of(
            pageIndex.toInt(),
            10
        )
        val itemCSList: Page<ItemCSRepository.OrderItemInfo> = itemCSRepository.findAllByKeywordAndCategory(store, keyword, ItemCategory.getCategory(category), paging)
        val pageInfo: PageInfo = PageInfo(
            page = pageIndex.toInt(),
            size = 10,
            totalElements = itemCSList.totalElements.toInt(),
            totalPages = itemCSList.totalPages
        )

        val orderItemResponseDto: List<OrderItemResponseDto> = itemCSList.map {
            item -> OrderItemResponseDto(
                item.getICID(),
                item.getICNAME(),
                item.getIHCG(),
                item.getICST(),
                item.getICPR(),
                orderApplicationRepository.existsByStoreAndItemHQ(store, itemHQRepository.findById(item.getIHID()).get())
            )
        }.toList()
        return ListResponseDto(orderItemResponseDto, pageInfo)
    }

    fun pickupItemOrders(storeId: Long, pickupOrderRequestDtos: List<PickupOrderRequestDto>): Boolean {
        pickupOrderRequestDtos.map { pickup ->
            val orderApplication = OrderApplication(
                pickup.count,
                LocalDateTime.now(),
                false
            )
            orderApplication.setItemHq(itemHQRepository.findByIdOrNull(pickup.item_hq_id)?: throw GlobalException(ErrorCode.NOT_FOUND_ITEMHS))
            orderApplication.setStore(storeRepository.findByIdOrNull(storeId)?: throw GlobalException(ErrorCode.NOT_FOUND_STORE))
            orderApplicationRepository.save(orderApplication)
        }
        return true;
    }
}