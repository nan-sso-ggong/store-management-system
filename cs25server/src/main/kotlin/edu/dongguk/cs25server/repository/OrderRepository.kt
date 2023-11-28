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

interface OrderRepository: JpaRepository<Order, Long> {
    @Query(value = "SELECT DISTINCT o.customer.name AS CNAME, o.id AS OID, " +
            "(SELECT GROUP_CONCAT(oi.itemCS.name) FROM OrderItemCS oi WHERE oi.order = o) AS INAME," +
            "o.finalPrice AS PRICE, o.createdAt AS CREATE, o.orderStatus AS STATUS " +
            "FROM Order AS o INNER JOIN OrderItemCS AS oi ON oi.order = o " +
            "WHERE o.store = :store AND (:state IS NULL OR o.orderStatus = :state) " +
            "AND (:startDate IS NULL OR o.createdAt >= :startDate) " +
            "AND (:endDate IS NULL OR o.createdAt <= :endDate)",
    countQuery = "SELECT count(o) FROM Order AS o WHERE o.store = :store")
    fun findCustomerOrderByStore(@Param("store") store: Store, @Param("state") state: OrderStatus?, @Param("startDate") starteDate: LocalDateTime?, @Param("endDate") endDateTime: LocalDateTime?, pageable: Pageable): Page<CustomerOrderInfo>

    interface CustomerOrderInfo {
        fun getCNAME(): String
        fun getOID(): Long
        fun getINAME(): MutableList<String>
        fun getPRICE(): Int
        fun getCREATE(): LocalDateTime
        fun getSTATUS(): OrderStatus
    }
}