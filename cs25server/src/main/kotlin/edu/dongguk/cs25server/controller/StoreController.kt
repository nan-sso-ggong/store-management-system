package edu.dongguk.cs25server.controller

import edu.dongguk.cs25server.annotation.UserId
import edu.dongguk.cs25server.dto.request.ItemCSRequest
import edu.dongguk.cs25server.dto.request.StoreRequestDto
import edu.dongguk.cs25server.dto.response.RestResponse
import edu.dongguk.cs25server.service.ItemCSService
import edu.dongguk.cs25server.service.StoreService
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/v1/managers/stores")
class StoreController(
    private val storeService: StoreService,
    private val itemCSService: ItemCSService
) {
    //Manager 컨트롤러로 이동 예정
    @PostMapping("")
    fun requestStore(@UserId managerId: Long, @RequestBody requestDto: StoreRequestDto): RestResponse<Boolean> {
        return RestResponse(storeService.requestStore(managerId, requestDto))
    }

    // test 용
    @PostMapping("/{storeId}/additem")
    fun createItem(
        @PathVariable(name = "storeId") storeId: Long,
        @RequestPart data: ItemCSRequest,
        @RequestPart imageFile: MultipartFile
    ): RestResponse<Boolean> {
        return RestResponse(itemCSService.createItem(storeId, data, imageFile))
    }
}