package edu.dongguk.cs25server.dto.response

class PageInfoint(
    val page: Int,
    val size: Int,
    val totalElements: Int,
    val totalPages: Int
) {
}