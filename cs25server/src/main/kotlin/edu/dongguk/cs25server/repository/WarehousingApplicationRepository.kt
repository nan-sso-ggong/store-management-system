package edu.dongguk.cs25server.repository

import edu.dongguk.cs25server.domain.WarehousingApplication
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface WarehousingApplicationRepository: JpaRepository<WarehousingApplication, Long> {
}