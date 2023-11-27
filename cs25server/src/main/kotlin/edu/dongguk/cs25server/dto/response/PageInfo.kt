package edu.dongguk.cs25server.dto.response

class PageInfo(
    val page: Int,
    val size: Int,
    val total_elements: Int,
    val total_pages: Int
) {
}