package edu.dongguk.cs25server.controller

import edu.dongguk.cs25server.dto.response.RestResponse
import edu.dongguk.cs25server.dto.response.StockResponseDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/headquarters")
class HeadquartersController {

    // 보유 재고 목록 조회
    @GetMapping("/stock-management/stocks")
    fun readStocks(@RequestParam("category") category: String,
                   @RequestParam("item_name") itemName: String): RestResponse<String> {
        // return RestResponse<StockResponseDto>()
    }
}