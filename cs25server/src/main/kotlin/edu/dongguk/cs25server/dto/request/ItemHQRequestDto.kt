package edu.dongguk.cs25server.dto.request

class ItemHQRequestDto(
    val itemName: String,
    val category: String, // 후에 jsoncreat를 이용하여 변경 예정
    val supplier: String, // 동일
    val supplyPrice: Long
) {
}