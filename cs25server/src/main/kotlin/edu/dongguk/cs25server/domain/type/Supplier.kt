package edu.dongguk.cs25server.domain.type

import com.fasterxml.jackson.annotation.JsonCreator
import edu.dongguk.cs25server.exception.ErrorCode
import edu.dongguk.cs25server.exception.GlobalException

enum class Supplier {
    문호리팥죽, 쿠캣, 라벨리, 스키니피그, 롯데푸드, 빙그레, 해태,세이면, 키다리,
    하림, 청정, 삼양, 오뚜기, 사천왕, 남양, 롯데, 코카, 파워에이드, LG생활건강,
    BR, 천지개벽, HK, OKF, 에이트투인터, 대산, 크라운, 아띠, 로아카, 오레오,
    동서, 농심, 뵈르, 오리온;

    companion object {
        @JvmStatic
        @JsonCreator
        fun getSupplier(supplier: String?): Supplier? {
            if (supplier.isNullOrEmpty())
                return null

            for(value in Supplier.values()) {
                if (supplier.equals(value.toString())) {
                    return value
                }
            }
            throw GlobalException(ErrorCode.WRONG_SUPPLIER_ERROR)
        }
    }
}