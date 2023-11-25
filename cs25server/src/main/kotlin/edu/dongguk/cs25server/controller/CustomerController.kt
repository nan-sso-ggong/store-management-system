package edu.dongguk.cs25server.controller

import edu.dongguk.cs25server.annotation.UserId
import edu.dongguk.cs25server.domain.type.ItemCategory
import edu.dongguk.cs25server.dto.request.CustomerItemSearch
import edu.dongguk.cs25server.dto.request.ItemCSRequest
import edu.dongguk.cs25server.dto.request.ItemHQRequestDto
import edu.dongguk.cs25server.dto.response.ItemsResponse
import edu.dongguk.cs25server.dto.response.ListResponseDto
import edu.dongguk.cs25server.dto.response.RestResponse
import edu.dongguk.cs25server.dto.response.StoreResponseDto
import edu.dongguk.cs25server.service.CustomerService
import edu.dongguk.cs25server.service.ItemCSService
import edu.dongguk.cs25server.service.StoreService
import edu.dongguk.cs25server.util.Log.Companion.log
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

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

//    TODO
//    @GetMapping("/store/{storeId}")
//    fun searchItems(@PathVariable(name = "storeId") storeId: Long,
//                    @ModelAttribute itemSearch: CustomerItemSearch) : RestResponse<ListResponseDto<List<ItemsResponse>>> {
//        //작업 중
//        log.info("${itemSearch.category}, ${itemSearch.name}, ${itemSearch.page}, ${itemSearch.size}")
//        return RestResponse(itemCSService.customerReadItems(
//            storeId, itemSearch.name, ItemCategory.getCategory(itemSearch.category), itemSearch.page, itemSearch.size))
//    }

    @GetMapping("/cart")
    fun checkPointAndBalance(@UserId customerId: Long): RestResponse<Map<String, Int>>
        = RestResponse(customerService.checkPointAndBalance(customerId))


}