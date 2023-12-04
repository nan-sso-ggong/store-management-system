package edu.dongguk.cs25server.repository

import edu.dongguk.cs25server.domain.Manager
import edu.dongguk.cs25server.domain.Store
import edu.dongguk.cs25server.domain.type.AllowStatus
import edu.dongguk.cs25server.security.UserLoginForm
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.util.*

@Repository
interface ManagerRepository : JpaRepository<Manager, Long> {
    fun findTop1ByLoginIdOrNameOrEmailOrPhoneNumber(
        loginId: String,
        name: String,
        email: String,
        phoneNumber: String
    ): Manager?

    fun findByLoginIdAndPassword(loginId: String, password: String): Manager?

    fun findTop1ByLoginIdOrPasswordOrPhoneNumber(loginId: String, password: String, phoneNumber: String): Manager?

    @Query("SELECT m.id AS id, m.role AS role FROM Manager m WHERE m.id = :managerId AND m.isLogin = true AND m.refreshToken is not null")
    fun findByIdAndRefreshToken(@Param("managerId") managerId: Long): UserLoginForm?

    @Query("SELECT m.id AS id, m.role AS role FROM Manager m WHERE m.id = :managerId AND m.isLogin = true AND m.refreshToken = :refreshToken")
    fun findByIdAndRefreshToken(@Param("managerId") managerId: Long, @Param("refreshToken") refreshToken: String): UserLoginForm?

    fun findByIdAndRefreshTokenIsNotNullAndIsLoginIsTrue(managerId: Long): Manager?

    @Query(
        value = "SELECT m.manager_id as ID, m.name as NAME, m.phone_number as PNUMBER, s.address as ADDRESS, m.created_at AS CREATEDAT " +
                "FROM managers m inner join stores s on m.manager_id = s.manager_id " +
                "WHERE m.status = :status order by m.created_at desc",
        countQuery = "SELECT count(*) " +
                "FROM managers m inner join stores s on m.manager_id = s.manager_id " +
                "WHERE m.status = :status order by m.created_at desc", nativeQuery = true
    )
    fun findNotAllowManagerByStatusOrderByCreatedAtDesc(
        @Param("status") status: String,
        paging: Pageable
    ): Page<ManagerInfo>

    fun findByIdAndStatus(id: Long, status: AllowStatus): Manager?

    interface ManagerInfo {
        fun getID(): Long
        fun getNAME(): String
        fun getPNUMBER(): String
        fun getADDRESS(): String
        fun getCREATEDAT(): LocalDate

    }

    fun findTop1ByStores(store: Store): Manager?
}