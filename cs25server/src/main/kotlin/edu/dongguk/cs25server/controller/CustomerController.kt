package edu.dongguk.cs25server.controller

import edu.dongguk.cs25server.annotation.UserId
import edu.dongguk.cs25server.dto.request.CustomerItemSearch
import edu.dongguk.cs25server.dto.response.ItemsResponse
import edu.dongguk.cs25server.dto.response.ListResponseDto
import edu.dongguk.cs25server.dto.response.RestResponse
import edu.dongguk.cs25server.dto.response.StoreResponseDto
import edu.dongguk.cs25server.service.CustomerService
import edu.dongguk.cs25server.service.ItemCSService
import edu.dongguk.cs25server.service.StoreService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/customers")
class CustomerController(
    private val customerService: CustomerService,
    private val storeService: StoreService,
    private val itemCSService: ItemCSService
) {
    
    @GetMapping("/store")
    fun searchStore(@RequestParam("name") name: String) : RestResponse<List<StoreResponseDto>> {
        return RestResponse(storeService.searchByName(name))
    }

    // TODO
    @GetMapping("/store/{storeId}")
    fun searchItems(@PathVariable(name = "storeId") storeId: Long,
                    @ModelAttribute itemSearch: CustomerItemSearch): RestResponse<ListResponseDto<List<ItemsResponse>>> {

        return RestResponse(itemCSService.customerReadItems(storeId, itemSearch))
    }

    @GetMapping("/cart")
    fun checkPointAndBalance(@UserId customerId: Long): RestResponse<Map<String, Int>> {
        return RestResponse(customerService.checkPointAndBalance(customerId))
    }

}