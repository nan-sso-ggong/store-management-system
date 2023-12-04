package edu.dongguk.cs25server.service

import edu.dongguk.cs25server.domain.Manager
import edu.dongguk.cs25server.domain.Store
import edu.dongguk.cs25server.domain.type.AllowStatus
import edu.dongguk.cs25server.dto.request.StoreEditRequestDto
import edu.dongguk.cs25server.dto.request.StoreRequestDto
import edu.dongguk.cs25server.dto.response.*
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
import org.springframework.web.multipart.MultipartFile

@Service
@Transactional
class StoreService(
    private val storeRepository: StoreRepository,
    private val managerRepository: ManagerRepository,
    private val fileUtil: FileUtil
) {
    // 점포 목록 조회
    fun readStores(userId: Long): List<StoreDetailResponseDto> =
        storeRepository.findAllByManagerIdAndStatus(userId, AllowStatus.APPROVAL)
            .map(Store::toDetailResponse)
            .ifEmpty { throw GlobalException(ErrorCode.NOT_FOUND_STORE) }

    // 점포 정보 편집
    fun editStore(storeId: Long, storeEditRequestDto: StoreEditRequestDto): Boolean {
        var store: Store = storeRepository.findByIdOrNull(storeId) ?: throw GlobalException(ErrorCode.NOT_FOUND_STORE)
        store.editStore(storeEditRequestDto)
        return true
    }

    //C
    fun requestStore(managerId: Long, requestDto: StoreRequestDto, imageFile: MultipartFile): Boolean {
        storeRepository.findTop1ByNameOrCallNumber(requestDto.name, requestDto.callNumber)
            ?.let { throw GlobalException(ErrorCode.DUPLICATION_STORE) }

        val manager: Manager = managerRepository.findByIdAndStatus(managerId, AllowStatus.APPROVAL)
            ?: throw GlobalException(ErrorCode.NOT_FOUND_MANAGER)
        val image = fileUtil.toEntityS3(imageFile)
        storeRepository.save(
            Store(
                name = requestDto.name,
                address = requestDto.address,
                callNumber = requestDto.callNumber,
                image = image,
                manager = manager
            )
        )
        return true
    }

    //R
    //보류
    fun searchByName(name: String): List<StoreResponseDto> = storeRepository.findByNameContains(name)
        .map(Store::toResponse)
        .ifEmpty { throw GlobalException(ErrorCode.NOT_FOUND_STORE) }

    fun getRequestStoreList(pageIndex: Long, pageSize: Long): ListResponseDto<List<RequestStoreListDto>> {
        val paging: Pageable = PageRequest.of(
            pageIndex.toInt(),
            pageSize.toInt(),
        )

        val storeList: Page<StoreRepository.StoreInfo> =
            storeRepository.findNotAllowStoreByStatusOrderByCreatedAtDesc(AllowStatus.BEFORE.toString(), paging)

        val pageInfo: PageInfo = PageInfo(
            page = pageIndex.toInt(),
            size = pageSize.toInt(),
            totalElements = storeList.totalElements.toInt(),
            totalPages = storeList.totalPages
        )

        val storeDtoList: List<RequestStoreListDto> = storeList
            .map { s ->
                RequestStoreListDto(
                    s.getID(),
                    s.getNAME(),
                    s.getMNAME(),
                    s.getADDRESS(),
                    s.getCREATEDAT().toString()
                )
            }
            .toList()

        return ListResponseDto(storeDtoList, pageInfo)
    }

    //U
    fun applyStoreRequest(managerId: Long, select: Boolean): Boolean {
        val store = storeRepository.findByIdAndStatus(managerId, AllowStatus.BEFORE)
            ?: throw GlobalException(ErrorCode.NOT_FOUND_STORE)

        if (store.manager.status != AllowStatus.APPROVAL)
            throw GlobalException(ErrorCode.MANAGER_NOT_ALLOW)

        if (select) {
            store.allowStore(AllowStatus.APPROVAL)
        } else {
            store.allowStore(AllowStatus.REFUSE)
        }

        return true;
    }

    //D
    //디비에서 지우죠!

}