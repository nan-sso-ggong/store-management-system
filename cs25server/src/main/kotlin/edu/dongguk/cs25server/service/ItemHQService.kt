package edu.dongguk.cs25server.service

import edu.dongguk.cs25server.domain.type.ItemCategory
import edu.dongguk.cs25server.dto.response.ListResponseDto
import edu.dongguk.cs25server.dto.response.StockResponseDto
import edu.dongguk.cs25server.repository.ItemHQRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class ItemHQService(
    private val itemHQRepository:ItemHQRepository
) {
//    // 보유 재고 목록 반환
//    fun readStocks(category: String, itemName: String): ListResponseDto<StockResponseDto> {
//        // 구현 필요
//        return null
//    }
}