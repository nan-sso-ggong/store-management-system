package edu.dongguk.cs25server.repository

import edu.dongguk.cs25server.domain.Store
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface StoreRepository : JpaRepository<Store, Long> {
    fun findTop1ByNameOrCallNumber(name: String, callNumber: String): Store?

    fun findByNameContains(@Param("name") name: String): List<Store>
}

