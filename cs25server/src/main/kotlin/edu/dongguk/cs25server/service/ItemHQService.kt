package edu.dongguk.cs25server.service

import edu.dongguk.cs25server.domain.ItemHQ
import edu.dongguk.cs25server.domain.type.ItemCategory
import edu.dongguk.cs25server.domain.type.Supplier
import edu.dongguk.cs25server.dto.request.ItemHQRequestDto
import edu.dongguk.cs25server.dto.request.ItemHQUpdateDto
import edu.dongguk.cs25server.dto.response.*
import edu.dongguk.cs25server.exception.ErrorCode
import edu.dongguk.cs25server.exception.GlobalException
import edu.dongguk.cs25server.repository.ImageRepository
import edu.dongguk.cs25server.repository.ItemHQRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import kotlin.math.ceil

@Service
@Transactional
class ItemHQService(
    private val itemHQRepository: ItemHQRepository,
    private val imageRepository: ImageRepository,
    private val fileUtil: FileUtil
) {
    // 보유 재고 목록 반환
    fun readStocks(category: String?, itemName: String): ListResponseDto<List<StockResponseDto>> {
        val itemHQs: List<ItemHQ> =
            itemHQRepository.findAllByItemNameAndCategory(itemName, ItemCategory.getCategory(category))

        val stockResponseDtos: List<StockResponseDto> = itemHQs.map(ItemHQ::toStockResponse).toList()
        return ListResponseDto(
            datalist = stockResponseDtos,
            page_info = PageInfo(
                page = 0,
                size = 0,
                total_elements = stockResponseDtos.size,
                total_pages = Math.ceil(stockResponseDtos.size / 10.0).toInt()
            )
        )
    }

    // 상품 추가
    fun createItem(data: ItemHQRequestDto, imageFile: MultipartFile): Boolean {
        val image = fileUtil.toEntity(imageFile)
        itemHQRepository.save(
            ItemHQ(
                itemName = data.item_name,
                price = data.supply_price,
                category = data.category,
                supplier = data.supplier,
                image = image
            )
        )
        return true
    }

    // 보유 재고 상세 조회
    fun readItemDetail(stockId: Long): ItemDetailResponseDto {
        val itemHQ = itemHQRepository.findByIdOrNull(stockId) ?: throw GlobalException(ErrorCode.NOT_FOUND_ITEMHS)
        return itemHQ.toItemDetailResponse()
    }

    // 상품 수정
    fun updateItem(stockId: Long, data: ItemHQUpdateDto, imageFile: MultipartFile): Boolean {
        val itemHQ = itemHQRepository.findByIdOrNull(stockId) ?: throw GlobalException(ErrorCode.NOT_FOUND_ITEMHS)
        val image = fileUtil.toEntity(imageFile)
        val previous_image = itemHQ.getImage()
        if (!fileUtil.deleteFile(previous_image.getUuidName()))
            throw GlobalException(ErrorCode.IMAGE_DELETE_ERROR)
        imageRepository.delete(previous_image)

        itemHQ.updateItemHQ(data, image)
        return true
    }

    // 상품 삭제
    fun deleteItem(stockId: Long): Boolean {
        val itemHQ = itemHQRepository.findByIdOrNull(stockId) ?: throw GlobalException(ErrorCode.NOT_FOUND_ITEMHS)
        var image = itemHQ.getImage()
        if (!fileUtil.deleteFile(image.getUuidName()))
            throw GlobalException(ErrorCode.IMAGE_DELETE_ERROR)

        itemHQRepository.delete(itemHQ)
        return true
    }

    // 입고 관리 발주 목록 조회
    fun readOrderRequest(
        itemName: String,
        category: String?,
        supplier: String?
    ): ListResponseDto<List<OrderResponseDto>> {
        val itemHQs: List<ItemHQ> = itemHQRepository.findAllByItemNameAndCategoryAndSupplier(
            itemName, ItemCategory.getCategory(category), Supplier.getSupplier(supplier)
        )

        val orderResponseDtos: List<OrderResponseDto> =
            itemHQs.map(ItemHQ::toOrderResponse).toList()
        return ListResponseDto(
            datalist = orderResponseDtos,
            page_info = PageInfo(
                page = 0,
                size = 0,
                total_elements = orderResponseDtos.size,
                total_pages = ceil(orderResponseDtos.size / 10.0).toInt()
            )
        )
    }

    // 재고 조회
    fun readOrderStocks(
        lack: Int,
        itemName: String,
        category: String?,
        supplier: String?
    ): ListResponseDto<List<OrderStockResponseDto>> {
        val itemHQs: List<ItemHQ> = if (lack == 1) {
            itemHQRepository.findAllByStockIsLessThanOrderSum()
        } else {
            itemHQRepository.findAllByItemNameAndCategoryAndSupplier(
                itemName, ItemCategory.getCategory(category), Supplier.getSupplier(supplier)
            )
        }

        val orderStockResponseDtos: List<OrderStockResponseDto> = itemHQs.map(ItemHQ::toOrderStockResponse).toList()
        return ListResponseDto(
            datalist = orderStockResponseDtos,
            page_info = PageInfo(
                page = 0,
                size = 0,
                total_elements = orderStockResponseDtos.size,
                total_pages = ceil(orderStockResponseDtos.size / 10.0).toInt()
            )
        )
    }
}