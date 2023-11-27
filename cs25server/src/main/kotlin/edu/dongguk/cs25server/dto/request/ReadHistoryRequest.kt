package edu.dongguk.cs25server.dto.request

class ReadHistoryRequest (
    val type: String? = null,
    val page: Int = 0,
    val size: Int = 4
)