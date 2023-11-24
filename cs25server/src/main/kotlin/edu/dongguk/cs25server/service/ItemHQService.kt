package edu.dongguk.cs25server.service

import edu.dongguk.cs25server.domain.ItemHQ
import edu.dongguk.cs25server.domain.type.ItemCategory
import edu.dongguk.cs25server.dto.response.ListResponseDto
import edu.dongguk.cs25server.dto.response.PageInfo
import edu.dongguk.cs25server.dto.response.StockResponseDto
import edu.dongguk.cs25server.repository.ItemHQRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.time.LocalDate
import java.time.LocalDateTime

@Service
@Transactional
class ItemHQService(
    private val itemHQRepository:ItemHQRepository
) {
    // 보유 재고 목록 반환
    fun readStocks(category: String?, itemName: String): ListResponseDto<MutableList<StockResponseDto>> {
        val itemHQs: List<ItemHQ> = if (itemName.isNullOrBlank()) {
            itemHQRepository.findAllByItemNameContains(itemName)
        } else {
            val itemCategory: ItemCategory? = ItemCategory.getCategory(category)
            itemHQRepository.findAllByItemNameContainsAndCategory(itemName, itemCategory)
        }

        val stockResponseDtos: MutableList<StockResponseDto> = mutableListOf()
        for (itemHQ in itemHQs) {
            stockResponseDtos.add(StockResponseDto(
                item_id = itemHQ.id,
                item_name = itemHQ.itemName,
                supply_price = itemHQ.price,
                stock_quantity = itemHQ.stock,
                // 입고일 로직 수정 필요
                warehousing_date = Timestamp.valueOf(LocalDateTime.now()))
            )
        }

        return ListResponseDto(
            datalist = stockResponseDtos,
            pageInfo = PageInfo(
                page = 0,
                size = 0,
                totalElements = stockResponseDtos.size,
                totalPages = Math.round(stockResponseDtos.size / 10.0).toInt()
            )
        )
    }
}