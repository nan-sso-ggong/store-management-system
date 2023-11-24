package edu.dongguk.cs25server.domain.type

import edu.dongguk.cs25server.exception.ErrorCode
import edu.dongguk.cs25server.exception.GlobalException

enum class ItemCategory(private val category: String) {
    ICE_CREAM("아이스크림"), SNACK("과자"), NOODLE("라면"), BEVERAGE("음료");

    override fun toString(): String {
        return category
    }

    companion object {
        fun getCategory(category: String?): ItemCategory? {
            if (category.isNullOrBlank())
                throw GlobalException(ErrorCode.WRONG_CATEGORY_ERROR)

            for(value in ItemCategory.values()) {
                if (category.equals(value.category)) {
                    return value
                }
            }
            return null
        }
    }
}