package edu.dongguk.cs25server.controller

import edu.dongguk.cs25server.annotation.UserId
import edu.dongguk.cs25server.dto.request.CustomerItemSearch
import edu.dongguk.cs25server.dto.response.RestResponse
import edu.dongguk.cs25server.dto.response.StoreResponseDto
import edu.dongguk.cs25server.service.CustomerService
import edu.dongguk.cs25server.service.ItemCSService
import edu.dongguk.cs25server.service.StoreService
import edu.dongguk.cs25server.util.Log.Companion.log
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/customers")
class CustomerController(
    private val customerService: CustomerService,
    private val storeService: StoreService,
    private val itemCSService: ItemCSService
) {
    
    @GetMapping("/store")
    fun searchStore(@RequestParam("name") name: String) : RestResponse<List<StoreResponseDto>>
        = RestResponse(storeService.searchByName(name))

    @GetMapping("/store/{storeId}")
    fun searchItems(@PathVariable(name = "storeId") storeId: Long, @ModelAttribute itemSearch: CustomerItemSearch) {
        //작업 중
        log.info("${itemSearch.category}, ${itemSearch.name}, ${itemSearch.page}, ${itemSearch.size}")
    }

    @GetMapping("/cart")
    fun checkPointAndBalance(@UserId customerId: Long): RestResponse<Map<String, Int>>
        = RestResponse(customerService.checkPointAndBalance(customerId))

}