package edu.dongguk.cs25server.controller

import edu.dongguk.cs25server.annotation.UserId
import edu.dongguk.cs25server.domain.type.UserRole
import edu.dongguk.cs25server.dto.request.CustomerItemSearch
import edu.dongguk.cs25server.dto.request.CustomerPaymentRequest
import edu.dongguk.cs25server.dto.request.ReadHistoryRequest
import edu.dongguk.cs25server.dto.response.*
import edu.dongguk.cs25server.service.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/customers")
class CustomerController(
    private val customerService: CustomerService,
    private val storeService: StoreService,
    private val itemCSService: ItemCSService,
    private val orderService: OrderService,
    private val authService: AuthService
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
                @RequestBody paymentRequest: CustomerPaymentRequest): RestResponse<PaymentResponse> {
        return RestResponse(orderService.payment(storeId, customerId, paymentRequest))
    }

    @GetMapping("/history")
    fun readHistory(@UserId customerId: Long,
                    @ModelAttribute readHistory: ReadHistoryRequest): RestResponse<ListResponseDto<List<PaymentResponse>>> {
        return RestResponse(orderService.readHistory(customerId, readHistory))
    }

    @GetMapping("/history/{orderId}")
    fun readHistoryDetail(@PathVariable(name = "orderId") orderId: Long,
                          @UserId customerId: Long): RestResponse<PaymentResponse> {
        return RestResponse(orderService.readHistoryDetail(orderId))
    }

    @PostMapping("/refund/{orderId}")
    fun refund(@PathVariable(name = "orderId") orderId: Long,
               @UserId customerId: Long): RestResponse<Any> {
        return RestResponse(success = orderService.refund(orderId, customerId))
    }

    @PostMapping("/logout")
    fun logoutCustomer(@UserId customerId: Long) : RestResponse<Any> {
        return RestResponse(success = authService.logout(customerId, UserRole.CUSTOMER))
    }
}