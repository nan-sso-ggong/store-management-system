package edu.dongguk.cs25server.repository

import edu.dongguk.cs25server.domain.Store
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface StoreRepository : JpaRepository<Store, Long> {
    fun findTop1ByNameOrCallNumber(name: String, callNumber: String): Store?
}

