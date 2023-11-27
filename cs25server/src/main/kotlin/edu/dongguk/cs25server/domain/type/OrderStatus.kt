package edu.dongguk.cs25server.domain.type

import com.fasterxml.jackson.annotation.JsonCreator
import edu.dongguk.cs25server.exception.ErrorCode
import edu.dongguk.cs25server.exception.GlobalException

enum class OrderStatus(private val orderStatus: String) {
    READY("픽업대기"), PICKUP("픽업완료"), CANCEL("환불처리");

    override fun toString(): String {
        return orderStatus
    }

    companion object {
        @JvmStatic
        @JsonCreator
        fun getOrderStatus(orderStatus: String?): OrderStatus? {
            if (orderStatus.isNullOrEmpty())
                return null

            for(value in OrderStatus.values()) {
                if (orderStatus.equals(value.orderStatus)) {
                    return value
                }
            }
            throw GlobalException(ErrorCode.WRONG_ORDER_STATUS_ERROR)
        }
    }
}