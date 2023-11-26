package edu.dongguk.cs25server.repository

import edu.dongguk.cs25server.domain.ItemHQ
import edu.dongguk.cs25server.domain.OrderApplication
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderApplicationRepository: JpaRepository<OrderApplication, Long> {
    fun findAllByItemHQIsIn(itemHQs: List<ItemHQ>): List<OrderApplication>
}