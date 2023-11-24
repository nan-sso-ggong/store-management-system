package edu.dongguk.cs25server.dto.request

import edu.dongguk.cs25server.domain.type.Membership
import edu.dongguk.cs25server.domain.type.UserRole

data class ManagerRequestDto (
    val loginId: String,
    val password: String,
    val name: String,
    val email: String,
    val phoneNumber: String,
    val userRole: UserRole,
    val memberShip: Membership
)