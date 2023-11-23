package edu.dongguk.cs25server.service


import edu.dongguk.cs25server.domain.Manager
import edu.dongguk.cs25server.dto.request.ManagerRequestDto
import edu.dongguk.cs25server.exception.ErrorCode
import edu.dongguk.cs25server.exception.GlobalException
import edu.dongguk.cs25server.repository.ManagerRepository
import org.slf4j.Logger
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ManagerService(private val managerRepository: ManagerRepository) {

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
                        userRole = requestDto.userRole,
                        memberShip = requestDto.memberShip)
        )
        return true
    }
}


