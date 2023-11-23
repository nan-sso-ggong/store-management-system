package edu.dongguk.cs25server.dto.response

class ListResponseDto<T> (
    val datalist: T,
    val pageInfo: PageInfo
) {
}