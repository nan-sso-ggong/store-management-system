package edu.dongguk.cs25server.domain.type

enum class ReleaseStatus(private val status: String) {
    LACK("재고 부족"), WAITING("출고 대기중"), RELEASING("출고중");

    override fun toString(): String {
        return this.status
    }
}