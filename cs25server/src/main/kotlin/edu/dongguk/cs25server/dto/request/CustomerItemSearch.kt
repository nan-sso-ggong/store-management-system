package edu.dongguk.cs25server.dto.request

class CustomerItemSearch (
    val category: String? = null,
    val name: String = "",
    val page: Int = 0,
    val size: Int = 8
)