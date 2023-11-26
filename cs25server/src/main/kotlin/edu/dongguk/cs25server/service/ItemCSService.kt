package edu.dongguk.cs25server.service

import edu.dongguk.cs25server.domain.Image
import edu.dongguk.cs25server.domain.ItemCS
import edu.dongguk.cs25server.domain.Store
import edu.dongguk.cs25server.domain.type.Extension
import edu.dongguk.cs25server.domain.type.ImageCategory
import edu.dongguk.cs25server.domain.type.ItemCategory
import edu.dongguk.cs25server.dto.request.ItemCSRequest
import edu.dongguk.cs25server.dto.request.ItemCSUpdateListDto
import edu.dongguk.cs25server.dto.response.ItemsResponse
import edu.dongguk.cs25server.dto.response.ListResponseDto
import edu.dongguk.cs25server.dto.response.PageInfo
import edu.dongguk.cs25server.dto.response.StockForStoreDto
import edu.dongguk.cs25server.exception.ErrorCode
import edu.dongguk.cs25server.exception.GlobalException
import edu.dongguk.cs25server.repository.ImageRepository
import edu.dongguk.cs25server.repository.ItemCSRepository
import edu.dongguk.cs25server.repository.StoreRepository
import jakarta.transaction.Transactional
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
@Transactional
class ItemCSService(
    private val itemCSRepository: ItemCSRepository,
    private val storeRepository: StoreRepository,
    private val imageRepository: ImageRepository,
    private val fileUtil: FileUtil
) {

    //C 상품 추가는 본사에서
    //test 용 메소드
    fun createItem(storeId: Long, data: ItemCSRequest, imageFile: MultipartFile): Boolean {
        if (imageFile.isEmpty) {
            throw GlobalException(ErrorCode.EMPTY_IMAGE_ERROR)
        }
        val originName = imageFile.originalFilename
        val extension = fileUtil.getFileExtension(originName)
        val uuidName = fileUtil.storeFile(imageFile)?:throw GlobalException(ErrorCode.IMAGE_SAVING_ERROR)
        val image = imageRepository.save(
            Image(
                originName = originName,
                uuidName = uuidName,
                extension = Extension.valueOf(extension.uppercase()),
                imageCategory = ImageCategory.ITEM_HQ
            )
        )

        val store: Store = storeRepository.findByIdOrNull(storeId)
            ?: throw GlobalException(ErrorCode.NOT_FOUND_STORE)

        val category = ItemCategory.getCategory(data.category)
        itemCSRepository.save(
            ItemCS(
                name = data.item_name,
                price = data.selling_price,
                category = category,
                image = image,
                store = store
            )
        )
        return true
    }

    //R
    fun getItemCS(storeId: Long, category: ItemCategory?, sorting: String, ordering: String, pageIndex: Long, pageSize: Long): ListResponseDto<List<StockForStoreDto>> {
        val order: Sort.Direction
        val store: Store = storeRepository.findByIdOrNull(storeId)
                ?: throw GlobalException(ErrorCode.NOT_FOUND_STORE)

        order = if (ordering.equals("desc")) {
            Sort.Direction.DESC
        } else {
            Sort.Direction.ASC
        }

        val paging: Pageable = when (sorting) {
            "amount" -> PageRequest.of(
                    pageIndex.toInt(),
                    pageSize.toInt(),
                    Sort.by(order, "stock")
            )

            "name" -> PageRequest.of(
                    pageIndex.toInt(),
                    pageSize.toInt(),
                    Sort.by(order, "name")
            )

            else -> throw GlobalException(ErrorCode.INVALID_ARGUMENT)
        }
        val stockList: Page<ItemCS>
        if (category == null) {
            stockList = itemCSRepository.findByStore(store, paging)
        } else {
            stockList = itemCSRepository.findByStoreAndCategory(store, category, paging)
        }

        val pageInfo = PageInfo(page = pageIndex.toInt(),
                size = pageSize.toInt(),
                totalElements = stockList.totalElements.toInt(),
                totalPages = stockList.totalPages)

        val stockDtoList: List<StockForStoreDto> = stockList
                .map(ItemCS::toResponse).toList()

        return ListResponseDto(stockDtoList, pageInfo)
    }

    //
    fun customerReadItems(storeId: Long, name: String, category: ItemCategory?, page: Int, size: Int): ListResponseDto<List<ItemsResponse>> {
        val store: Store = storeRepository.findByIdOrNull(storeId)
            ?: throw GlobalException(ErrorCode.NOT_FOUND_STORE)

        val paging: Pageable = PageRequest.of(
            page,
            size,
            Sort.by(Sort.Direction.DESC, "name"))

        val items: Page<ItemCS> = if (category == null) {
            itemCSRepository.findByStoreAndNameContains(store, name, paging)
        } else {
            itemCSRepository.findByStoreAndCategoryAndNameContains(store, category, name, paging)
        } ?: throw GlobalException(ErrorCode.NOT_FOUND_ITEMCS)

        val pageInfo = PageInfo(page = page,
            size = size,
            totalElements = items.totalElements.toInt(),
            totalPages = items.totalPages)

        val itemsResponse: List<ItemsResponse> = items.map(ItemCS::toItemsResponse).toList()
        return ListResponseDto(itemsResponse, pageInfo)
    }

    //U
    fun updateItemStock(storeId: Long, requestListDto: ItemCSUpdateListDto): Boolean {
        val store: Store = storeRepository.findByIdOrNull(storeId)
                ?: throw GlobalException(ErrorCode.NOT_FOUND_STORE)

        for (requestDto in requestListDto.itemList) {
            val itemCS: ItemCS = itemCSRepository.findByIdAndStore(requestDto.itemId, store)
                    ?: throw GlobalException(ErrorCode.NOT_FOUND_ITEMCS)

            if (requestDto.is_plus) {
                itemCS.addStock(requestDto.amount)
            } else {
                itemCS.removeStock(requestDto.amount)
            }
        }
        return true
    }

    //D 본사에서


}