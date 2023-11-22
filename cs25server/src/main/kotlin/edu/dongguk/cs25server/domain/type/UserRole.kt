package edu.dongguk.cs25server.domain.type

enum class UserRole (private val role: String) {
    ROLE_CUSTOMER("ROLE_CUSTOMER"), ROLE_MANAGER("ROLE_MANAGER"), ROLE_HQ("ROLE_HQ")
}