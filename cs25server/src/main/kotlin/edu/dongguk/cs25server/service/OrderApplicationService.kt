package edu.dongguk.cs25server.service

import edu.dongguk.cs25server.domain.ItemHQ
import edu.dongguk.cs25server.domain.OrderApplication
import edu.dongguk.cs25server.domain.type.ItemCategory
import edu.dongguk.cs25server.domain.type.ReleaseStatus
import edu.dongguk.cs25server.domain.type.Supplier
import edu.dongguk.cs25server.dto.request.ReleaseRequestDto
import edu.dongguk.cs25server.dto.response.*
import edu.dongguk.cs25server.exception.ErrorCode
import edu.dongguk.cs25server.exception.GlobalException
import edu.dongguk.cs25server.repository.ItemHQRepository
import edu.dongguk.cs25server.repository.OrderApplicationRepository
import edu.dongguk.cs25server.repository.StoreRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import kotlin.math.ceil

@Service
@Transactional
class OrderApplicationService(
    private val storeRepository: StoreRepository,
    private val orderApplicationRepository: OrderApplicationRepository
) {
    // 출고 관리 발주 목록 조회
    fun readReleaseStocks(storeName: String, address: String): ListResponseDto<List<ReleaseStockResponseDto>> {
        val stores = storeRepository.findAllByNameContainsAndAddressContains(storeName, address)
        val orderApplications = orderApplicationRepository.findAllByStoreIn(stores)

        val releaseStockResponseDtos = orderApplications.map(OrderApplication::toReleaseStockResponse).toList()
        return ListResponseDto(
            datalist = releaseStockResponseDtos,
            pageInfo = PageInfo(
                page = 0,
                size = 0,
                totalElements = releaseStockResponseDtos.size,
                totalPages = ceil(releaseStockResponseDtos.size/10.0).toInt()
            )
        )
    }

    // 출고 신청 발주 목록 신청
    fun updateOrderReleaseStatus(releaseRequestDto: ReleaseRequestDto): Boolean {
        for (orderId in releaseRequestDto.order_ids) {
            val orderApplication = orderApplicationRepository.findByIdOrNull(orderId)?:
            throw GlobalException(ErrorCode.NOT_FOUND_ORDER_APPLICATION)

            if (orderApplication.getReleaseStatus() == ReleaseStatus.RELEASING)
                continue

            if (orderApplication.getReleaseStatus() == ReleaseStatus.LACK &&
                !orderApplication.isEnoughItemHQStock()) {
                throw GlobalException(ErrorCode.NOT_ENOUGH_RELEASE_ERROR)
            }
            orderApplication.releaseOrder()
        }

        return true
    }
}