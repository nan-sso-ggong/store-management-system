package edu.dongguk.cs25server.domain.type

enum class UserRole (private val role: String) {
    CUSTOMER("CUSTOMER"), MANAGER("MANAGER"), HQ("HQ")
}