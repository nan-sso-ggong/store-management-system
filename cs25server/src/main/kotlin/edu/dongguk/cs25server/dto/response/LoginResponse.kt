package edu.dongguk.cs25server.dto.response

class LoginResponse(
    val name: String,
    val access_token: String,
    val refresh_token: String
)