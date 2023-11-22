package edu.dongguk.cs25server.security

interface UserLoginForm {
    fun getId(): Long
    fun getRole(): String
}