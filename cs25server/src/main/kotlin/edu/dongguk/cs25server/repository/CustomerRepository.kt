package edu.dongguk.cs25server.repository

import edu.dongguk.cs25server.domain.Customer
import edu.dongguk.cs25server.domain.type.LoginProvider
import edu.dongguk.cs25server.security.UserLoginForm
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CustomerRepository : JpaRepository<Customer, Long> {
    fun findBySocialIdAndLoginProvider(socialId: String, loginProvider: LoginProvider): Customer?

    @Query("SELECT c.id AS id, c.role AS role FROM Customer c WHERE c.id = :customerId AND c.isLogin = true AND c.refreshToken is not null")
    fun findByIdAndRefreshToken(@Param("customerId") customerId: Long): UserLoginForm?

    fun findByIdAndRefreshTokenIsNotNullAndIsLoginIsTrue(customerId: Long): Customer?

}