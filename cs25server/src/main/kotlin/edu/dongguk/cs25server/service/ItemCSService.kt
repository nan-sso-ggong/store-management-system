package edu.dongguk.cs25server.service

import edu.dongguk.cs25server.domain.ItemCS
import edu.dongguk.cs25server.domain.Store
import edu.dongguk.cs25server.domain.type.ItemCategory
import edu.dongguk.cs25server.dto.request.ItemCSUpdateListDto
import edu.dongguk.cs25server.dto.response.ListResponseDto
import edu.dongguk.cs25server.dto.response.PageInfo
import edu.dongguk.cs25server.dto.response.StockForStoreDto
import edu.dongguk.cs25server.exception.ErrorCode
import edu.dongguk.cs25server.exception.GlobalException
import edu.dongguk.cs25server.repository.ItemCSRepository
import edu.dongguk.cs25server.repository.StoreRepository
import jakarta.transaction.Transactional
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
@Transactional
class ItemCSService(private val itemCSRepository: ItemCSRepository, private val storeRepository: StoreRepository) {

    //C 상품 추가는 본사에서

    //R
    fun getItemCS(storeId: Long, category: ItemCategory?, sorting: String, ordering: String, pageIndex: Long, pageSize: Long): ListResponseDto<List<StockForStoreDto>> {
        val order: Sort.Direction
        val store: Store = storeRepository.findByIdOrNull(storeId)
                ?: throw GlobalException(ErrorCode.NOT_FOUND_STORE)

        order = if (ordering.equals("desc")) {
            Sort.Direction.DESC
        } else {
            Sort.Direction.ASC
        }

        val paging: Pageable = when (sorting) {
            "amount" -> PageRequest.of(
                    pageIndex.toInt(),
                    pageSize.toInt(),
                    Sort.by(order, "stock")
            )

            "name" -> PageRequest.of(
                    pageIndex.toInt(),
                    pageSize.toInt(),
                    Sort.by(order, "name")
            )

            else -> throw GlobalException(ErrorCode.INVALID_ARGUMENT)
        }
        val stockList: Page<ItemCS>
        if (category == null) {
            stockList = itemCSRepository.findByStore(store, paging)
        } else {
            stockList = itemCSRepository.findByStoreAndItemCategory(store, category, paging)
        }


        val pageInfo: PageInfo = PageInfo(page = pageIndex.toInt(),
                size = pageSize.toInt(),
                totalElements = stockList.totalElements.toInt(),
                totalPages = stockList.totalPages)

        val stockDtoList: List<StockForStoreDto> = stockList
                .map(ItemCS::toResponse).toList()

        return ListResponseDto(stockDtoList, pageInfo)
    }

    //U
    fun updateItemStock(storeId: Long, requestListDto: ItemCSUpdateListDto): Boolean {
        val store: Store = storeRepository.findByIdOrNull(storeId)
                ?: throw GlobalException(ErrorCode.NOT_FOUND_STORE)

        for (requestDto in requestListDto.itemList) {
            val itemCS: ItemCS = itemCSRepository.findByIdAndStore(requestDto.itemId, store)
                    ?: throw GlobalException(ErrorCode.NOT_FOUND_ITEMCS)

            if (requestDto.is_plus) {
                itemCS.addStock(requestDto.amount)
            } else {
                itemCS.removeStock(requestDto.amount)
            }
        }
        return true
    }

    //D 본사에서


}