package edu.dongguk.cs25server.repository

import edu.dongguk.cs25server.domain.Customer
import edu.dongguk.cs25server.domain.Headquarters
import edu.dongguk.cs25server.domain.type.LoginProvider
import edu.dongguk.cs25server.security.UserLoginForm
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface HeadquartersRepository : JpaRepository<Headquarters, Long> {

    @Query("SELECT h.id AS id, h.role AS role FROM Headquarters h WHERE h.id = :headquartersId AND h.isLogin = true AND h.refreshToken is not null")
    fun findByIdAndRefreshToken(@Param("headquartersId") headquartersId: Long): UserLoginForm?

}