package edu.dongguk.cs25server.domain.type

enum class LoginProvider (private val loginProvider: String) {
    KAKAO("KAKAO"), NAVER("NAVER"), GOOGLE("GOOGLE")
}