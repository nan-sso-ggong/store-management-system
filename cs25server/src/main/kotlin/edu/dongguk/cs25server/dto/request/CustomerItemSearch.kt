package edu.dongguk.cs25server.dto.request

class CustomerItemSearch (
    val category: String = "",
    val name: String = "",
    val page: Int = 1,
    val size: Int = 8
)