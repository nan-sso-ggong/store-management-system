package edu.dongguk.cs25server.controller

import edu.dongguk.cs25server.dto.request.ItemCSUpdateListDto
import edu.dongguk.cs25server.dto.request.ManagerRequestDto
import edu.dongguk.cs25server.dto.response.RestResponse
import edu.dongguk.cs25server.service.ItemCSService
import edu.dongguk.cs25server.service.ManagerService
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/managers")
class ManagerController(private val managerService: ManagerService, private val itemCSService: ItemCSService) {

    @PostMapping("")
    fun createManager(@RequestBody requestDto: ManagerRequestDto): RestResponse<Boolean> {
        return RestResponse(managerService.createManager(requestDto))
    }

    @PatchMapping("/stock/{storeId}")
    fun updateItemStock(@PathVariable("storeId") storeId: Long, @RequestBody requestDto: ItemCSUpdateListDto): RestResponse<Boolean> {
        return RestResponse(itemCSService.updateItemStock(storeId, requestDto))
    }
}
