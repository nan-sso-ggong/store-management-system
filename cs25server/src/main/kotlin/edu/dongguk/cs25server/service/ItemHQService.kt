package edu.dongguk.cs25server.service

import edu.dongguk.cs25server.domain.Image
import edu.dongguk.cs25server.domain.ItemHQ
import edu.dongguk.cs25server.domain.type.Extension
import edu.dongguk.cs25server.domain.type.ImageCategory
import edu.dongguk.cs25server.domain.type.ItemCategory
import edu.dongguk.cs25server.domain.type.Supplier
import edu.dongguk.cs25server.dto.request.ItemHQRequestDto
import edu.dongguk.cs25server.dto.response.ItemDetailResponseDto
import edu.dongguk.cs25server.dto.response.ListResponseDto
import edu.dongguk.cs25server.dto.response.PageInfo
import edu.dongguk.cs25server.dto.response.StockResponseDto
import edu.dongguk.cs25server.exception.ErrorCode
import edu.dongguk.cs25server.exception.GlobalException
import edu.dongguk.cs25server.repository.ImageRepository
import edu.dongguk.cs25server.repository.ItemHQRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
@Transactional
class ItemHQService(
    private val itemHQRepository:ItemHQRepository,
    private val imageRepository: ImageRepository,
    private val fileUtil: FileUtil
) {
    // 보유 재고 목록 반환
    fun readStocks(category: String?, itemName: String): ListResponseDto<List<StockResponseDto>> {
        val itemHQs: List<ItemHQ> = if (itemName.isNullOrBlank()) {
            itemHQRepository.findAllByItemNameContains(itemName)
        } else {
            val itemCategory: ItemCategory? = ItemCategory.getCategory(category)
            itemHQRepository.findAllByItemNameContainsAndCategory(itemName, itemCategory)
        }

        val stockResponseDtos: List<StockResponseDto> = itemHQs.map(ItemHQ::toStockResponse).toList()
        return ListResponseDto(
            datalist = stockResponseDtos,
            pageInfo = PageInfo(
                page = 0,
                size = 0,
                totalElements = stockResponseDtos.size,
                totalPages = Math.round(stockResponseDtos.size / 10.0).toInt()
            )
        )
    }

    // 상품 추가
    fun createItem(data: ItemHQRequestDto, imageFile: MultipartFile): Boolean {
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

        val category = ItemCategory.getCategory(data.category)
        category?: throw GlobalException(ErrorCode.WRONG_CATEGORY_ERROR)
        itemHQRepository.save(
            ItemHQ(
                itemName = data.itemName,
                price = data.supplyPrice,
                category = category,
                supplier = Supplier.valueOf(data.supplier),
                image = image
            )
        )
        return true
    }

    // 보유 재고 상세 조회
    fun readItemDetail(stockId: Long): ItemDetailResponseDto {
        val itemHQ = itemHQRepository.findByIdOrNull(stockId)?: throw GlobalException(ErrorCode.NOT_FOUND_ITEMHS)
        return itemHQ.toItemDetailResponse()
    }
}