package edu.dongguk.cs25server.controller

import edu.dongguk.cs25server.domain.type.ItemCategory
import edu.dongguk.cs25server.annotation.UserId
import edu.dongguk.cs25server.domain.type.UserRole
import edu.dongguk.cs25server.dto.request.*
import edu.dongguk.cs25server.dto.response.*
import edu.dongguk.cs25server.service.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/managers")
class ManagerController(
    private val managerService: ManagerService,
    private val itemCSService: ItemCSService,
    private val storeService: StoreService,
    private val orderService: OrderService,
    private val orderCSService: OrderCSService,
    private val authService: AuthService
) {
    // 점포 목록 조회
    @GetMapping("/store")
    fun readStores(@UserId userId: Long): RestResponse<List<StoreDetailResponseDto>> {
        return RestResponse(storeService.readStores(userId))
    }

    // 점포 정보 편집
    @PatchMapping("/store/{storeId}/edit")
    fun editStore(@PathVariable storeId: Long, @RequestBody storeEditRequestDto: StoreEditRequestDto): RestResponse<Boolean> {
        return RestResponse(storeService.editStore(storeId, storeEditRequestDto))
    }

    // 주문 내역 조회
    @GetMapping("/store/{storeId}/customer_orders")
    fun readCustomerOrder(@PathVariable storeId: Long, @ModelAttribute customerOrderDto: CustomerOrderRequestDto): RestResponse<ListResponseDto<List<CustomerOrderResponseDto>>> {
        return RestResponse(orderService.readCustomerOrder(storeId, customerOrderDto))
    }

    // 픽업 완료 처리
    @PatchMapping("/store/{storeId}/customer_orders")
    fun pickupCustomerOrder(@PathVariable storeId: Long, @RequestBody customerPickupDtos: List<CustomerPickupRequestDto>): RestResponse<Boolean> {
        return RestResponse(orderService.pickupCustomerOrder(storeId, customerPickupDtos))
    }

    // 발주 목록 조회
    @GetMapping("/store/{storeId}/item_orders")
    fun readItemOrders(@PathVariable storeId: Long, @RequestParam keyword: String?, @RequestParam category: String?, @RequestParam(defaultValue = "0") pageIndex: Long): RestResponse<ListResponseDto<List<OrderItemResponseDto>>> {
        return RestResponse(orderCSService.readItemOrders(storeId, keyword, category, pageIndex))
    }

    // 발주 신청
    @PostMapping("/store/{storeId}/item_orders")
    fun addItemOrders(@PathVariable storeId: Long, @RequestBody itemOrderRequestDtos: List<ItemOrderRequestDto>): RestResponse<Boolean> {
        return RestResponse(orderCSService.addItemOrders(storeId, itemOrderRequestDtos))
    }

    //삭제 예정
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

    @PostMapping("/logout")
    fun logoutManager(@UserId managerId: Long): RestResponse<Any> {
        return RestResponse(success = authService.logout(managerId, UserRole.MANAGER))
    }

}
