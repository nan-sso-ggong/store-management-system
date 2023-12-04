package edu.dongguk.cs25server.service

import edu.dongguk.cs25server.domain.WarehousingApplication
import edu.dongguk.cs25server.dto.request.WarehousingRequestDtos
import edu.dongguk.cs25server.exception.ErrorCode
import edu.dongguk.cs25server.exception.GlobalException
import edu.dongguk.cs25server.repository.ItemHQRepository
import edu.dongguk.cs25server.repository.WarehousingApplicationRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
@Transactional
class WarehousingApplicationService(
    val warehousingApplicationRepository: WarehousingApplicationRepository,
    val itemHQRepository: ItemHQRepository
) {
    // 입고 신청
    fun createWarehousingRequest(warehousingRequestDtos: WarehousingRequestDtos): Boolean {
        for (warehousingRequestDto in warehousingRequestDtos.data_list) {
            if (warehousingRequestDto.additional_quantity <= 0)
                continue

            val itemHQ = itemHQRepository.findByIdOrNull(warehousingRequestDto.item_id) ?:
            throw GlobalException(ErrorCode.NOT_FOUND_ITEMHS)

            warehousingApplicationRepository.save(
                WarehousingApplication(
                    supplierName = itemHQ.getSupplier(),
                    count = warehousingRequestDto.additional_quantity,
                    itemHQ = itemHQ
                )
            )
        }
        return true
    }

}