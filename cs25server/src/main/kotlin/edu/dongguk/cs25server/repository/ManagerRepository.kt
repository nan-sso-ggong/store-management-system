package edu.dongguk.cs25server.repository

import edu.dongguk.cs25server.domain.Customer
import edu.dongguk.cs25server.domain.Manager
import edu.dongguk.cs25server.security.UserLoginForm
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ManagerRepository : JpaRepository<Manager, Long> {

    @Override
    override fun findById(id: Long): Optional<Manager>
    fun findTop1ByLoginIdOrNameOrEmailOrPhoneNumber(loginId: String, name: String, email: String, phoneNumber: String): Manager?

    fun findByLoginIdAndPassword(loginId: String, password: String): Manager?

    @Query("SELECT m.id AS id, m.role AS role FROM Manager m WHERE m.id = :managerId AND m.isLogin = true AND m.refreshToken is not null")
    fun findByIdAndRefreshToken(@Param("managerId") managerId: Long): UserLoginForm?

    fun findByIdAndRefreshTokenIsNotNullAndIsLoginIsTrue(managerId: Long): Manager?
}