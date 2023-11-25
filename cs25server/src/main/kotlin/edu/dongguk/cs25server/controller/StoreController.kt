package edu.dongguk.cs25server.controller

import edu.dongguk.cs25server.dto.request.StoreRequestDto
import edu.dongguk.cs25server.dto.response.RestResponse
import edu.dongguk.cs25server.service.ItemCSService
import edu.dongguk.cs25server.service.StoreService
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/managers/stores")
class StoreController(private val storeService: StoreService) {
    //Manager 컨트롤러로 이동 예정
    @PostMapping("")
    fun createStore(@RequestBody requestDto: StoreRequestDto): RestResponse<Boolean> {
        return RestResponse(storeService.createStore(requestDto))
    }
}