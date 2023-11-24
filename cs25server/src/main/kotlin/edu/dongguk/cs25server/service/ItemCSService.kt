package edu.dongguk.cs25server.service

import edu.dongguk.cs25server.domain.ItemCS
import edu.dongguk.cs25server.domain.Store
import edu.dongguk.cs25server.dto.request.ItemCSUpdateListDto
import edu.dongguk.cs25server.exception.ErrorCode
import edu.dongguk.cs25server.exception.GlobalException
import edu.dongguk.cs25server.repository.ItemCSRepository
import edu.dongguk.cs25server.repository.StoreRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
@Transactional
class ItemCSService(private val itemCSRepository: ItemCSRepository, private val storeRepository: StoreRepository) {

    //C 상품 추가는 본사에서

    //R
    

    //U
    fun updateItemStock(storeId: Long, requestListDto: ItemCSUpdateListDto): Boolean {
        val store: Store = storeRepository.findByIdOrNull(storeId)
                ?: throw GlobalException(ErrorCode.NOT_FOUND_STORE)

        for (requestDto in requestListDto.itemList) {
            val itemCS: ItemCS = itemCSRepository.findByIdAndStoreOrNull(requestDto.itemId, store)
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