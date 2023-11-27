package edu.dongguk.cs25server.service

import edu.dongguk.cs25server.domain.WarehousingApplication
import edu.dongguk.cs25server.dto.request.WarehousingRequestDto
import edu.dongguk.cs25server.exception.ErrorCode
import edu.dongguk.cs25server.exception.GlobalException
import edu.dongguk.cs25server.repository.ItemHQRepository
import edu.dongguk.cs25server.repository.WarehousingAppliactionRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
@Transactional
class WarehousingApplicationService(
    val warehousingAppliactionRepository: WarehousingAppliactionRepository,
    val itemHQRepository: ItemHQRepository
) {
    // 입고 신청
    fun createWarehousingRequest(warehousingRequestDtos: List<WarehousingRequestDto>): Boolean {
        for (warehousingRequestDto in warehousingRequestDtos) {
            if (warehousingRequestDto.additional_quantity <= 0)
                continue

            val itemHQ = itemHQRepository.findByIdOrNull(warehousingRequestDto.item_id) ?:
            throw GlobalException(ErrorCode.NOT_FOUND_ITEMHS)
            warehousingAppliactionRepository.save(
                WarehousingApplication(
                    supplierName = warehousingRequestDto.supplier,
                    count = warehousingRequestDto.additional_quantity,
                    itemHQ = itemHQ
                )
            )
        }
        return true
    }

}