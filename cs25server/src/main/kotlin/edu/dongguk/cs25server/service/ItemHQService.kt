package edu.dongguk.cs25server.service

import edu.dongguk.cs25server.domain.ItemCS
import edu.dongguk.cs25server.domain.ItemHQ
import edu.dongguk.cs25server.domain.Store
import edu.dongguk.cs25server.domain.type.ItemCategory
import edu.dongguk.cs25server.domain.type.Supplier
import edu.dongguk.cs25server.dto.request.ItemHQRequestDto
import edu.dongguk.cs25server.dto.request.ItemHQUpdateDto
import edu.dongguk.cs25server.dto.response.*
import edu.dongguk.cs25server.exception.ErrorCode
import edu.dongguk.cs25server.exception.GlobalException
import edu.dongguk.cs25server.repository.ImageRepository
import edu.dongguk.cs25server.repository.ItemCSRepository
import edu.dongguk.cs25server.repository.ItemHQRepository
import edu.dongguk.cs25server.repository.StoreRepository
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
    private val itemCSRepository: ItemCSRepository,
    private val storeRepository: StoreRepository,
    private val fileUtil: FileUtil
) {
    // 보유 재고 목록 반환
    fun readStocks(category: String?, itemName: String): ListResponseDto<List<StockResponseDto>> {
        val itemHQs: List<ItemHQ> =
            itemHQRepository.findAllByItemNameAndCategory(itemName, ItemCategory.getCategory(category))

        val stockResponseDtos = itemHQs.map(ItemHQ::toStockResponse).toList()
        return ListResponseDto(
            datalist = stockResponseDtos,
            pageInfo = PageInfo(
                page = 0,
                size = 0,
                totalElements = stockResponseDtos.size,
                totalPages = ceil(stockResponseDtos.size / 10.0).toInt()
            )
        )
    }

    // 상품 추가
    fun createItem(data: ItemHQRequestDto, imageFile: MultipartFile): Boolean {
        val image = fileUtil.toEntity(imageFile)
        if (itemHQRepository.existsByItemName(data.item_name)) {
            throw GlobalException(ErrorCode.DUPLICATION_ITEM_ERROR)
        }

        val itemHQ = itemHQRepository.save(
            ItemHQ(
                itemName = data.item_name,
                price = data.supply_price,
                category = data.category,
                supplier = data.supplier,
                image = image
            )
        )

        val stores = storeRepository.findAll()
        val itemCSs = stores.map { store ->
            ItemCS(
                name = data.item_name,
                price = data.supply_price.toInt(),
                category = data.category,
                itemHQ = itemHQ,
                store = store,
                image = image
            )
        }.toList()

        itemCSRepository.saveAll(itemCSs)
        return true
    }

    // 보유 재고 상세 조회
    fun readItemDetail(stockId: Long): ItemDetailResponseDto {
        val itemHQ = itemHQRepository.findByIdOrNull(stockId) ?: throw GlobalException(ErrorCode.NOT_FOUND_ITEMHS)
        return itemHQ.toItemDetailResponse()
    }

    // 상품 수정
    fun updateItem(stockId: Long, data: ItemHQUpdateDto?, imageFile: MultipartFile?): Boolean {
        val itemHQ = itemHQRepository.findByIdOrNull(stockId) ?: throw GlobalException(ErrorCode.NOT_FOUND_ITEMHS)

        if (imageFile != null && !imageFile.isEmpty) {
            itemHQ.updateImage(fileUtil.toEntityS3(imageFile))
        }
        if (data != null) {
            if (itemHQRepository.existsByItemName(data.item_name)) {
                throw GlobalException(ErrorCode.DUPLICATION_ITEM_ERROR)
            }
            itemHQ.updateImteHQInfo(data)
        }

        return true
    }

    // 상품 삭제
    fun deleteItem(stockId: Long): Boolean {
        val itemHQ = itemHQRepository.findByIdOrNull(stockId) ?: throw GlobalException(ErrorCode.NOT_FOUND_ITEMHS)
        val image = itemHQ.getImage()

        imageRepository.delete(image)
        itemHQRepository.delete(itemHQ)

        return true
    }

    // 입고 관리 발주 목록 조회
    fun readOrderRequests(
        itemName: String,
        category: String?,
        supplier: String?
    ): ListResponseDto<List<OrderResponseDto>> {
        val itemHQs = itemHQRepository.findAllByItemNameAndCategoryAndSupplier(
            itemName, ItemCategory.getCategory(category), Supplier.getSupplier(supplier)
        )

        val orderResponseDtos = itemHQs.map(ItemHQ::toOrderResponse).toList()
        return ListResponseDto(
            datalist = orderResponseDtos,
            pageInfo = PageInfo(
                page = 0,
                size = 0,
                totalElements = orderResponseDtos.size,
                totalPages = ceil(orderResponseDtos.size / 10.0).toInt()
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

        val orderStockResponseDtos = itemHQs.map(ItemHQ::toOrderStockResponse).toList()
        return ListResponseDto(
            datalist = orderStockResponseDtos,
            pageInfo = PageInfo(
                page = 0,
                size = 0,
                totalElements = orderStockResponseDtos.size,
                totalPages = ceil(orderStockResponseDtos.size / 10.0).toInt()
            )
        )
    }
}