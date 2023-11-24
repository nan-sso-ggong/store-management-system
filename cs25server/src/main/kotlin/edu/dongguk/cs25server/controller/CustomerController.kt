package edu.dongguk.cs25server.controller

import edu.dongguk.cs25server.dto.response.RestResponse
import edu.dongguk.cs25server.dto.response.StoreResponseDto
import edu.dongguk.cs25server.service.CustomerService
import edu.dongguk.cs25server.service.StoreService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/customers")
class CustomerController(
    private val customerService: CustomerService,
    private val storeService: StoreService,
) {

    // TODO 점포 검색
    @GetMapping("/store")
    fun search(@RequestParam("name") name: String) : RestResponse<List<StoreResponseDto>> {
        return RestResponse(storeService.searchByName(name))
    }

}