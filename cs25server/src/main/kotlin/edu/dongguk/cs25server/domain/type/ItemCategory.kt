package edu.dongguk.cs25server.domain.type

enum class ItemCategory(private val category: String) {
    ICE_CREAM("아이스크림"), SNACK("과자"), NOODLE("라면"), BEVERAGE("음료");

    override fun toString(): String {
        return category
    }
}