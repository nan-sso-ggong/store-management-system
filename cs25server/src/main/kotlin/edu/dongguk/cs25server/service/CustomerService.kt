package edu.dongguk.cs25server.service

import edu.dongguk.cs25server.exception.ErrorCode
import edu.dongguk.cs25server.exception.GlobalException
import edu.dongguk.cs25server.repository.CustomerRepository
import org.springframework.stereotype.Service

@Service
class CustomerService (private val customerRepository: CustomerRepository) {

    // TODO 포인트 확인
    fun checkPoint(customerId: Long) {
//        val customer = customerRepository.findById(customerId).orElseThrow(
//            throw GlobalException(ErrorCode.NOT_FOUND_CUSTOMER)
//        )
//        return customer.point
    }
}