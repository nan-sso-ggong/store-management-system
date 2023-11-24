package edu.dongguk.cs25server.repository

import edu.dongguk.cs25server.domain.Manager
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ManagerRepository : JpaRepository<Manager, Long> {

    @Override
    override fun findById(id: Long): Optional<Manager>
    fun findTop1ByLoginIdOrNameOrEmailOrPhoneNumber(loginId: String, name: String, email: String, phoneNumber: String): Manager?
}