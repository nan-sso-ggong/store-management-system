package edu.dongguk.cs25server.controller

import edu.dongguk.cs25server.annotation.UserId
import edu.dongguk.cs25server.dto.request.CustomerItemSearch
import edu.dongguk.cs25server.dto.request.CustomerPaymentRequest
import edu.dongguk.cs25server.dto.request.ReadHistoryRequest
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

    @GetMapping("/store/{storeId}")
    fun searchItems(@PathVariable(name = "storeId") storeId: Long,
                    @ModelAttribute itemSearch: CustomerItemSearch): RestResponse<ListResponseDto<List<ItemsResponse>>> {
        return RestResponse(itemCSService.customerReadItems(storeId, itemSearch))
    }

    @GetMapping("/store/cart")
    fun checkPointAndBalance(@UserId customerId: Long): RestResponse<Map<String, Int>> {
        return RestResponse(customerService.checkPointAndBalance(customerId))
    }

    // 구매하는 아이템 아이디, 수량, 사용 포인트, 결제 수단
    @PostMapping("/store/{storeId}/payment")
    fun payment(@PathVariable(name = "storeId") storeId: Long,
                @UserId customerId: Long,
                @RequestBody paymentRequest: CustomerPaymentRequest) {

    }

    // 페이지, 사이즈, OrderState 이넘, 주문 일자 별로 정렬
    @GetMapping("/history")
    fun readHistory(@UserId customerId: Long,
                    @ModelAttribute readHistory: ReadHistoryRequest) {

    }


    // 페이지, 사이즈, OrderState 이넘, 주문 일자 별로 정렬
    @GetMapping("/history/{orderId}")
    fun readHistoryDetail(@PathVariable(name = "orderId") orderId: Long,
                          @UserId customerId: Long) {

    }


    //
    @PostMapping("/refund/{orderId}")
    fun refund(@PathVariable(name = "orderId") orderId: Long,
                          @UserId customerId: Long) {

    }

}