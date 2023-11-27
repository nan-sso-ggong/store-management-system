package edu.dongguk.cs25server.security

import edu.dongguk.cs25server.domain.type.UserRole

interface UserLoginForm {
    fun getId(): Long
    fun getRole(): UserRole
}