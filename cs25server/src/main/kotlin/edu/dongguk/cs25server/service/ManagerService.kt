package edu.dongguk.cs25server.service


import edu.dongguk.cs25server.domain.Manager
import edu.dongguk.cs25server.domain.Store
import edu.dongguk.cs25server.domain.type.AllowStatus
import edu.dongguk.cs25server.dto.request.ManagerRequestDto
import edu.dongguk.cs25server.dto.response.ListResponseDto
import edu.dongguk.cs25server.dto.response.RequestManagerListDto
import edu.dongguk.cs25server.dto.request.StoreEditRequestDto
import edu.dongguk.cs25server.dto.response.PageInfo
import edu.dongguk.cs25server.dto.response.StoreDetailResponseDto
import edu.dongguk.cs25server.exception.ErrorCode
import edu.dongguk.cs25server.exception.GlobalException
import edu.dongguk.cs25server.repository.ManagerRepository
import edu.dongguk.cs25server.repository.StoreRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ManagerService(
    private val managerRepository: ManagerRepository,
    private val storeRepository: StoreRepository
) {
    fun readStores(userId: Long): List<StoreDetailResponseDto> {
        var stores: List<Store> =
            storeRepository.findAllByManager(userId) ?: throw GlobalException(ErrorCode.NOT_FOUND_STORE)
        var storeDetailResponseDtos: MutableList<StoreDetailResponseDto> = arrayListOf()
        for (store in stores) {
            storeDetailResponseDtos.add(
                StoreDetailResponseDto(
                    store_id = store.id,
                    store_name = store.name,
                    address = store.address,
                    store_tel = store.callNumber
                )
            )
        }
        return storeDetailResponseDtos;
    }

    fun editStore(storeId: Long, storeEditRequestDto: StoreEditRequestDto): Boolean {
        var store: Store = storeRepository.findByIdOrNull(storeId) ?: throw GlobalException(ErrorCode.NOT_FOUND_STORE)
        store.editStore(storeEditRequestDto)
        return true
    }

    fun createManager(requestDto: ManagerRequestDto): Boolean {
        managerRepository.findTop1ByLoginIdOrNameOrEmailOrPhoneNumber(
            requestDto.loginId,
            requestDto.name,
            requestDto.email,
            requestDto.phoneNumber
        )?.let {
            throw GlobalException(ErrorCode.DUPLICATION_MANAGER)
        }
        managerRepository.save(
            Manager(
                loginId = requestDto.loginId,
                password = requestDto.password,
                name = requestDto.name,
                email = requestDto.email,
                phoneNumber = requestDto.phoneNumber,
                role = requestDto.userRole,
                memberShip = requestDto.memberShip
            )
        )
        return true
    }

    fun getRequestManagerList(pageIndex: Long, pageSize: Long): ListResponseDto<List<RequestManagerListDto>> {
        val paging: Pageable = PageRequest.of(
            pageIndex.toInt(),
            pageSize.toInt(),
        )

        val managerList: Page<ManagerRepository.ManagerInfo> =
            managerRepository.findNotAllowManagerByStatusOrderByCreatedAtDesc(AllowStatus.BEFORE.toString(),paging);

        val pageInfo: PageInfo = PageInfo(
            page = pageIndex.toInt(),
            size = pageSize.toInt(),
            totalElements = managerList.totalElements.toInt(),
            totalPages = managerList.totalPages
        )

        val managerDtoList: List<RequestManagerListDto> = managerList
            .map { m ->
                RequestManagerListDto(
                    m.getNAME(),
                    m.getPNUMBER(),
                    m.getADDRESS(),
                    m.getCREATEDAT().toString()
                )
            }
            .toList()

        return ListResponseDto(managerDtoList, pageInfo)
    }
}


