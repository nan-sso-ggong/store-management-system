package edu.dongguk.cs25server.controller

import edu.dongguk.cs25server.annotation.UserId
import edu.dongguk.cs25server.dto.request.ItemHQRequestDto
import edu.dongguk.cs25server.dto.response.ListResponseDto
import edu.dongguk.cs25server.dto.response.RestResponse
import edu.dongguk.cs25server.dto.response.StockResponseDto
import edu.dongguk.cs25server.service.ItemHQService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/v1/headquarters")
class HeadquartersController(
    private val itemHQService: ItemHQService
) {

    // 보유 재고 목록 조회
    @GetMapping("/stock-management/stocks")
    fun readStocks(@RequestParam(name = "category", required = false) category: String?,
                   @RequestParam(name = "item_name", required = false, defaultValue = "") itemName: String):
            RestResponse<ListResponseDto<List<StockResponseDto>>> {
        return RestResponse(data = itemHQService.readStocks(category, itemName))
    }

    // 상품 추가
    @PostMapping("/stock-management/stocks")
    fun createItem(@RequestPart data: ItemHQRequestDto,
                   @RequestPart imageFile: MultipartFile): RestResponse<Boolean> {
        return RestResponse(itemHQService.createItem(data, imageFile))
    }
}