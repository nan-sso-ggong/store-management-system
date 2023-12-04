package edu.dongguk.cs25server.repository

import edu.dongguk.cs25server.domain.Manager
import edu.dongguk.cs25server.domain.Store
import edu.dongguk.cs25server.domain.type.AllowStatus
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.util.*

@Repository
interface StoreRepository : JpaRepository<Store, Long> {
    fun findTop1ByNameOrCallNumber(name: String, callNumber: String): Store?

    fun findByNameContains(@Param("name") name: String): List<Store>

    @Query(
        "SELECT s.store_id as ID, s.name as NAME, m.name as MNAME, s.address as ADDRESS, s.created_at as CREATEDAT " +
                "FROM stores s inner join managers m " +
                "on m.manager_id = s.manager_id " +
                "WHERE s.status = :status order by s.created_at desc",
        countQuery = "SELECT COUNT(*) " +
                "FROM stores s inner join managers m " +
                "on m.manager_id = s.manager_id " +
                "WHERE s.status = :status order by s.created_at desc", nativeQuery = true
    )
    fun findNotAllowStoreByStatusOrderByCreatedAtDesc(
        @Param("status") status: String,
        paging: Pageable
    ): Page<StoreInfo>

    interface StoreInfo {
        fun getID(): Long
        fun getNAME(): String
        fun getMNAME(): String
        fun getADDRESS(): String
        fun getCREATEDAT(): LocalDate
    }

    @Query("select s from Store s join fetch s.manager where s.id = :id and s.status=:status")
    fun findByIdAndStatus(@Param("id") id: Long, @Param("status") status: AllowStatus): Store?

    fun findTop1ByManager(manager: Manager): Store?

    fun findAllByManagerIdAndStatus(mangerId: Long, status: AllowStatus): List<Store>

    fun findAllByNameContainsAndAddressContains(name: String, address: String): List<Store>
}

