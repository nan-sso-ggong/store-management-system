package edu.dongguk.cs25server.domain.type

import com.fasterxml.jackson.annotation.JsonCreator
import edu.dongguk.cs25server.exception.ErrorCode
import edu.dongguk.cs25server.exception.GlobalException

enum class ItemCategory(private val category: String) {
    ICE_CREAM("아이스크림"), SNACK("과자"), NOODLE("라면"), BEVERAGE("음료");

    override fun toString(): String {
        return category
    }

    companion object {
        @JvmStatic
        @JsonCreator
        fun getCategory(category: String?): ItemCategory? {
            if (category.isNullOrEmpty())
                return null

            for(value in ItemCategory.values()) {
                if (category.equals(value.category)) {
                    return value
                }
            }
            throw GlobalException(ErrorCode.WRONG_CATEGORY_ERROR)
        }
    }
}