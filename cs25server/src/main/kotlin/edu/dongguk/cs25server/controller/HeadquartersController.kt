package edu.dongguk.cs25server.controller

import edu.dongguk.cs25server.dto.response.ListResponseDto
import edu.dongguk.cs25server.dto.response.RestResponse
import edu.dongguk.cs25server.dto.response.StockResponseDto
import edu.dongguk.cs25server.service.ItemHQService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/headquarters")
class HeadquartersController(
    private val itemHQService: ItemHQService
) {

//    // 보유 재고 목록 조회
//    @GetMapping("/stock-management/stocks")
//    fun readStocks(@RequestParam(name = "category", required = false) category: String,
//                   @RequestParam(name = "item_name", required = false, defaultValue = "") itemName: String): RestResponse<ListResponseDto<StockResponseDto>> {
//        return RestResponse<ListResponseDto<StockResponseDto>>(data = itemHQService.readStocks(category, itemName))
//    }
}