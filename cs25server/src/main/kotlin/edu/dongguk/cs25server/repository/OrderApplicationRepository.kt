package edu.dongguk.cs25server.repository

import edu.dongguk.cs25server.domain.ItemHQ
import edu.dongguk.cs25server.domain.OrderApplication
import edu.dongguk.cs25server.domain.Store
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface OrderApplicationRepository: JpaRepository<OrderApplication, Long> {
    fun findAllByItemHQIsIn(itemHQs: List<ItemHQ>): List<OrderApplication>
    fun findAllByStoreIn(stores: List<Store>): List<OrderApplication>
    fun existsByStoreAndItemHQ(store: Store, itemHQ: ItemHQ): Boolean
}