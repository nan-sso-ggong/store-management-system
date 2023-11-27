package edu.dongguk.cs25server.service

import edu.dongguk.cs25server.exception.ErrorCode
import edu.dongguk.cs25server.exception.GlobalException
import edu.dongguk.cs25server.repository.CustomerRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CustomerService (private val customerRepository: CustomerRepository) {

    // TODO 포인트 확인
    fun checkPointAndBalance(customerId: Long): Map<String, Int> {
        val customer = customerRepository.findByIdOrNull(customerId)
            ?: throw GlobalException(ErrorCode.NOT_FOUND_CUSTOMER)

        return mapOf("point" to customer.getPointBalance(), "balance" to customer.getBalance())
    }
}