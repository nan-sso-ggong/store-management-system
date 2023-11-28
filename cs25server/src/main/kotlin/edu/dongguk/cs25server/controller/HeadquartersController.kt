package edu.dongguk.cs25server.controller

import edu.dongguk.cs25server.dto.request.*

import org.springframework.web.bind.annotation.DeleteMapping
import edu.dongguk.cs25server.dto.response.*
import edu.dongguk.cs25server.service.*
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/v1/headquarters")
class HeadquartersController(
    private val itemHQService: ItemHQService,
    private val managerService: ManagerService,
    private val storeService: StoreService,
    private val orderApplicationService: OrderApplicationService,
    private val warehousingApplicationService: WarehousingApplicationService
) {

    // 보유 재고 목록 조회
    @GetMapping("/stock-management/stocks")
    fun readStocks(
        @RequestParam(name = "category", required = false) category: String?,
        @RequestParam(name = "item_name", required = false, defaultValue = "") itemName: String
    ):
            RestResponse<ListResponseDto<List<StockResponseDto>>> {
        return RestResponse(data = itemHQService.readStocks(category, itemName))
    }

    // 상품 추가
    @PostMapping("/stock-management/stocks")
    fun createItem(
        @RequestPart data: ItemHQRequestDto,
        @RequestPart imageFile: MultipartFile
    ): RestResponse<Boolean> {
        return RestResponse(itemHQService.createItem(data, imageFile))
    }

    // 보유 재고 상세 조회
    @GetMapping("/stock-management/stocks/{stockId}")
    fun readStockDetail(@PathVariable("stockId") stockId: Long): RestResponse<ItemDetailResponseDto> {
        return RestResponse(itemHQService.readItemDetail(stockId))
    }

    // 상품 수정
    @PatchMapping("/stock-management/stocks/{stockId}")
    fun updateItem(
        @PathVariable("stockId") stockId: Long, @RequestPart data: ItemHQUpdateDto,
        @RequestPart imageFile: MultipartFile
    ): RestResponse<Boolean> {
        return RestResponse(itemHQService.updateItem(stockId, data, imageFile))
    }

    // 상품 삭제
    @DeleteMapping("/stock-management/stocks/{stockId}")
    fun deleteItem(@PathVariable("stockId") stockId: Long): RestResponse<Boolean> {
        return RestResponse(itemHQService.deleteItem(stockId))
    }

    // 입고 관리 발주 목록 조회
    @GetMapping("/warehousing-management/stocks")
    fun readOrderRequests(
        @RequestParam(name = "item_name", required = false, defaultValue = "") itemName: String,
        @RequestParam(name = "category", required = false) category: String?,
        @RequestParam(name = "supplier", required = false) supplier: String?
    ): RestResponse<ListResponseDto<List<OrderResponseDto>>> {
        return RestResponse(itemHQService.readOrderRequests(itemName, category, supplier))
    }

    // 재고 조회
    @GetMapping("/warehousing-management/warehousing-request")
    fun readOrderStocks(
        @RequestParam(name = "lack", required = false, defaultValue = "0") lack: Int,
        @RequestParam(name = "item_name", required = false, defaultValue = "") itemName: String,
        @RequestParam(name = "category", required = false) category: String?,
        @RequestParam(name = "supplier", required = false) supplier: String?
    ): RestResponse<ListResponseDto<List<OrderStockResponseDto>>> {
        return RestResponse(itemHQService.readOrderStocks(lack, itemName, category, supplier))
    }

    // 입고 신청
    @PostMapping("/warehousing-management/warehousing-request")
    fun createWarehousingRequest(
        @RequestBody warehousingRequestDtos: WarehousingRequestDtos
    ): RestResponse<Boolean> {
        return RestResponse(warehousingApplicationService.createWarehousingRequest(warehousingRequestDtos))
    }

    // 출고 관리 발주 목록 조회
    @GetMapping("/release-management/stocks")
    fun readReleaseRequests(
        @RequestParam(name = "store_name", required = false, defaultValue = "") storeName: String,
        @RequestParam(name = "address", required = false, defaultValue = "") address: String,
        ): RestResponse<ListResponseDto<List<ReleaseStockResponseDto>>> {
        return RestResponse(orderApplicationService.readReleaseStocks(storeName,address))
    }

    // 출고 신청 발주 목록 조회
    @GetMapping("/release-management/release-request")
    fun readReleaseStockRequests(
        @RequestParam(name = "store_name", required = false, defaultValue = "") storeName: String,
        @RequestParam(name = "address", required = false, defaultValue = "") address: String,
    ): RestResponse<ListResponseDto<List<ReleaseStockResponseDto>>> {
        return RestResponse(orderApplicationService.readReleaseStocks(storeName,address))
    }

    // 출고 신청 발주 목록 신청
    @PatchMapping("/release-management/release-request")
    fun updateOrderReleaseStatus(
        @RequestBody releaseRequestDto: ReleaseRequestDto
    ): RestResponse<Boolean> {
        return RestResponse(orderApplicationService.updateOrderReleaseStatus(releaseRequestDto))
    }

    @GetMapping("/managers")
    fun getRequestManagerList(
        @RequestParam("index") index: Long,
        @RequestParam("size", defaultValue = "10") size: Long
    ): RestResponse<ListResponseDto<List<RequestManagerListDto>>> {
        return RestResponse(managerService.getRequestManagerList(index, size))
    }

    @GetMapping("/stores")
    fun getRequestStoreList(
        @RequestParam("index") index: Long,
        @RequestParam("size", defaultValue = "10") size: Long
    ): RestResponse<ListResponseDto<List<RequestStoreListDto>>> {
        return RestResponse(storeService.getRequestStoreList(index, size))
    }

    @PatchMapping("/manager/{mangerId}/apply")
    fun applyManagerRequest(
        @PathVariable("mangerId") mangerId: Long,
        @RequestBody requestDto: ApplyRequestDto
    ): RestResponse<Boolean> {
        return RestResponse(managerService.applyManagerRequest(mangerId, requestDto.select))
    }

    @PatchMapping("/store/{storeId}/apply")
    fun applyStoreRequest(
        @PathVariable("storeId") storeId: Long,
        @RequestBody requestDto: ApplyRequestDto
    ): RestResponse<Boolean> {
        return RestResponse(storeService.applyStoreRequest(storeId, requestDto.select))
    }
}