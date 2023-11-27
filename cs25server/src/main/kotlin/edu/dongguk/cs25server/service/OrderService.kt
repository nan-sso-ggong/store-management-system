package edu.dongguk.cs25server.service

import edu.dongguk.cs25server.domain.Order
import edu.dongguk.cs25server.domain.Store
import edu.dongguk.cs25server.domain.type.OrderStatus
import edu.dongguk.cs25server.dto.request.CustomerOrderRequestDto
import edu.dongguk.cs25server.dto.request.CustomerPickupRequestDto
import edu.dongguk.cs25server.dto.response.CustomerOrderResponseDto
import edu.dongguk.cs25server.dto.response.ListResponseDto
import edu.dongguk.cs25server.dto.response.PageInfo
import edu.dongguk.cs25server.exception.ErrorCode
import edu.dongguk.cs25server.exception.GlobalException
import edu.dongguk.cs25server.repository.OrderRepostiory
import edu.dongguk.cs25server.repository.StoreRepository
import edu.dongguk.cs25server.util.Log.Companion.log
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class OrderService(private val storeRepository: StoreRepository, private val orderRepostiory: OrderRepostiory) {
    // 점주 - 주문 내역 조회
    fun readCustomerOrder(storeId: Long, customerOrderRequestDto: CustomerOrderRequestDto): ListResponseDto<List<CustomerOrderResponseDto>> {
        val paging: Pageable = PageRequest.of(
                customerOrderRequestDto.page.toInt(),
                10,
        )
        val store: Store = storeRepository.findByIdOrNull(storeId) ?: throw GlobalException(ErrorCode.NOT_FOUND_STORE)
        val customerOrderList: Page<OrderRepostiory.CustomerOrderInfo> = orderRepostiory.findCustomerOrderByStore(store, paging)

        val pageInfo: PageInfo = PageInfo(
                page = customerOrderRequestDto.page.toInt(),
                size = 10,
                totalElements = customerOrderList.totalElements.toInt(),
                totalPages = customerOrderList.totalPages
        )

        val customerOrderDtolist: List<CustomerOrderResponseDto> = customerOrderList
                .map { o ->
                    CustomerOrderResponseDto(
                           o.getCNAME(),
                            o.getOID(),
                            o.getINAME(),
                            o.getPRICE(),
                            o.getCREATE(),
                            o.getSTATUS().toString()
                    )
                }
                .toList()

        return ListResponseDto(customerOrderDtolist, pageInfo)
    }

    // 점주 - 픽업 완료 처리
    fun pickupCustomerOrder(storeId: Long, customerPickupDtos: List<CustomerPickupRequestDto>): Boolean {
        customerPickupDtos.map { dto ->
            orderRepostiory.findByIdOrNull(dto.customer_order_id)
                    ?.updateOrderStatus(OrderStatus.PICKUP)
                    ?: throw GlobalException(ErrorCode.NOT_FOUND_ORDER)
        }
        return true
    }
}