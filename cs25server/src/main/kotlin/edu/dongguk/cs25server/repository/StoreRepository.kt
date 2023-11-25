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

    @Query("SELECT s FROM Store AS s WHERE s.manager.id = :userId")
    fun findAllByManager(@Param("userId") userId: Long): List<Store>?

    @Override
    override fun findById(@Param("Id") id: Long): Optional<Store>
}

