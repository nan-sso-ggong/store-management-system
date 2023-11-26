package edu.dongguk.cs25server.controller

import edu.dongguk.cs25server.domain.type.ItemCategory
import edu.dongguk.cs25server.dto.request.ItemCSUpdateListDto
import edu.dongguk.cs25server.annotation.UserId
import edu.dongguk.cs25server.dto.request.CustomerOrderDto
import edu.dongguk.cs25server.dto.request.ManagerRequestDto
import edu.dongguk.cs25server.dto.request.StoreEditRequestDto
import edu.dongguk.cs25server.dto.response.*
import edu.dongguk.cs25server.service.ItemCSService
import edu.dongguk.cs25server.service.ManagerService
import edu.dongguk.cs25server.service.OrderService
import edu.dongguk.cs25server.service.StoreService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/managers")
class ManagerController(private val managerService: ManagerService, private val itemCSService: ItemCSService, private val storeService: StoreService, private val orderService: OrderService) {
    // 점포 목록 조회
    @GetMapping("/store")
    fun readStores(@UserId userId: Long): RestResponse<List<StoreDetailResponseDto>> {
        return RestResponse(storeService.readStores(userId));
    }

    // 점포 정보 편집
    @PatchMapping("/store/{storeId}/edit")
    fun editStore(@PathVariable storeId: Long, @RequestBody storeEditRequestDto: StoreEditRequestDto): RestResponse<Boolean> {
        return RestResponse(storeService.editStore(storeId, storeEditRequestDto))
    }

//    // 주문 내역 조회
//    @GetMapping("/store/{storeId}/customer_orders")
//    fun readCustomerOrder(@PathVariable storeId: Long, @ModelAttribute customerOrderDto: CustomerOrderDto): RestResponse<ListResponseDto<List<OrdersResponseDto>>> {
//        return RestResponse(orderService.readCustomerOrder(storeId, customerOrderDto))
//    }

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
}
