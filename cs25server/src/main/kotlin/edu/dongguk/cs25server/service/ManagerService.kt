package edu.dongguk.cs25server.service


import edu.dongguk.cs25server.domain.Manager
import edu.dongguk.cs25server.domain.Store
import edu.dongguk.cs25server.dto.request.ManagerRequestDto
import edu.dongguk.cs25server.dto.request.StoreEditRequestDto
import edu.dongguk.cs25server.dto.request.StoreRequestDto
import edu.dongguk.cs25server.dto.response.StoreDetailResponseDto
import edu.dongguk.cs25server.dto.response.StoreResponseDto
import edu.dongguk.cs25server.exception.ErrorCode
import edu.dongguk.cs25server.exception.GlobalException
import edu.dongguk.cs25server.repository.ManagerRepository
import edu.dongguk.cs25server.repository.StoreRepository
import edu.dongguk.cs25server.util.Log.Companion.log
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ManagerService(private val managerRepository: ManagerRepository,
                     private val storeRepository: StoreRepository) {
    fun readStores(userId : Long): List<StoreDetailResponseDto> {
        var stores: List<Store> = storeRepository.findAllByManager(userId)?: throw GlobalException(ErrorCode.NOT_FOUND_STORE)
        var storeDetailResponseDtos: MutableList<StoreDetailResponseDto> = arrayListOf()
        for (store in stores) {
            storeDetailResponseDtos.add(StoreDetailResponseDto(
                    store_id = store.id,
                    store_name = store.name,
                    address = store.address,
                    store_tel = store.callNumber
            ))
        }
        return storeDetailResponseDtos;
    }

    fun editStore(storeId: Long, storeEditRequestDto: StoreEditRequestDto): Boolean {
        var store: Store = storeRepository.findById(storeId).orElseThrow{ GlobalException(ErrorCode.NOT_FOUND_STORE) }
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
                Manager(loginId = requestDto.loginId,
                        password = requestDto.password,
                        name = requestDto.name,
                        email = requestDto.email,
                        phoneNumber = requestDto.phoneNumber,
                        role = requestDto.userRole,
                        memberShip = requestDto.memberShip)
        )
        return true
    }
}


