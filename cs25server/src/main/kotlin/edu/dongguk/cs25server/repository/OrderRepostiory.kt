package edu.dongguk.cs25server.repository

import edu.dongguk.cs25server.domain.Order
import edu.dongguk.cs25server.domain.Store
import edu.dongguk.cs25server.domain.type.OrderStatus
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDateTime

interface OrderRepostiory: JpaRepository<Order, Long> {
    fun findAllByStore(store: Store, pageable: Pageable): Page<Order>?

    @Query(value = "SELECT o.customer.name AS CNAME, o.id AS OID, oi.itemCS.name AS INAME, o.finalPrice AS PRICE, o.createdAt AS CREATE, o.orderStatus AS STATUS " +
            "FROM Order AS o INNER JOIN OrderItemCS AS oi ON oi.order = o WHERE o.store = :store",
    countQuery = "SELECT count(*) FROM Order")
    fun findCustomerOrderByStore(@Param("store") store: Store, pageable: Pageable): Page<CustomerOrderInfo>

    interface CustomerOrderInfo {
        fun getCNAME(): String
        fun getOID(): Long
        fun getINAME(): MutableList<String>
        fun getPRICE(): Int
        fun getCREATE(): LocalDateTime
        fun getSTATUS(): OrderStatus
    }
}