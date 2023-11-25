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
import java.util.*

@Repository
interface StoreRepository : JpaRepository<Store, Long> {
    fun findTop1ByNameOrCallNumber(name: String, callNumber: String): Store?

    fun findByNameContains(@Param("name") name: String): List<Store>

    fun findNotAllowStoreByStatusOrderByCreatedAtDesc(status: AllowStatus, paging: Pageable): Page<Store>

    fun findTop1ByManager(manager: Manager): Store?
}

