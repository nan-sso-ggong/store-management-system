package edu.dongguk.cs25server.domain.type

import com.fasterxml.jackson.annotation.JsonCreator
import edu.dongguk.cs25server.exception.ErrorCode
import edu.dongguk.cs25server.exception.GlobalException

enum class PaymentType(private val paymentType: String) {
    CARD("신용/체크카드"), POINT("포인트");

    override fun toString(): String {
        return paymentType
    }

    companion object {
        @JvmStatic
        @JsonCreator
        fun getCategory(paymentType: String?): PaymentType? {
            if (paymentType.isNullOrEmpty())
                return null

            for(value in PaymentType.values()) {
                if (paymentType.equals(value.paymentType)) {
                    return value
                }
            }
            throw GlobalException(ErrorCode.WRONG_CATEGORY_ERROR)
        }
    }
}