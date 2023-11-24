package edu.dongguk.cs25server.controller

import edu.dongguk.cs25server.domain.type.ItemCategory
import edu.dongguk.cs25server.dto.request.ItemCSUpdateListDto
import edu.dongguk.cs25server.dto.request.ManagerRequestDto
import edu.dongguk.cs25server.dto.response.ListResponseDto
import edu.dongguk.cs25server.dto.response.RestResponse
import edu.dongguk.cs25server.dto.response.StockForStoreDto
import edu.dongguk.cs25server.service.ItemCSService
import edu.dongguk.cs25server.service.ManagerService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/managers")
class ManagerController(private val managerService: ManagerService, private val itemCSService: ItemCSService) {

    @PostMapping("")
    fun createManager(@RequestBody requestDto: ManagerRequestDto): RestResponse<Boolean> {
        return RestResponse(managerService.createManager(requestDto))
    }

    @PatchMapping("/store/{storeId}/stock")
    fun updateItemStock(@PathVariable("storeId") storeId: Long, @RequestBody requestDto: ItemCSUpdateListDto): RestResponse<Boolean> {
        return RestResponse(itemCSService.updateItemStock(storeId, requestDto))
    }

    @GetMapping("/store/{storeId}/stock")
    fun getItemCS(@PathVariable("storeId") storeId: Long,
                  @RequestParam("category", required = false) category: ItemCategory?,
                  @RequestParam("order") order: String,
                  @RequestParam("sort") sort: String,
                  @RequestParam("index") index: Long,
                  @RequestParam("size", defaultValue = "10") size: Long): RestResponse<ListResponseDto<List<StockForStoreDto>>> {
        return RestResponse(itemCSService.getItemCS(storeId, category, sort, order, index, size))
    }
}
