package edu.dongguk.cs25server.repository

import edu.dongguk.cs25server.domain.Customer
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
            "o.totalPrice AS PRICE, o.createdAt AS CREATE, o.orderStatus AS STATUS " +
            "FROM Order AS o INNER JOIN OrderItemCS AS oi ON oi.order = o " +
            "WHERE o.store = :store AND (:state IS NULL OR o.orderStatus = :state) AND (:startDate IS NULL OR o.createdAt >= :startDate) AND (:endDate IS NULL OR o.createdAt <= :endDate) ORDER BY o.createdAt DESC",
    countQuery = "SELECT count(o) FROM Order AS o WHERE o.store = :store")
    fun findCustomerOrderByStore(@Param("store") store: Store, @Param("state") state: OrderStatus?, @Param("startDate") startDate: LocalDateTime?, @Param("endDate") endDate: LocalDateTime?, pageable: Pageable): Page<CustomerOrderInfo>

    fun findByCustomer(customer: Customer, paging: Pageable): Page<Order>

    fun findByCustomerAndOrderStatus(customer: Customer, orderStatus: OrderStatus, paging: Pageable): Page<Order>

    interface CustomerOrderInfo {
        fun getCNAME(): String
        fun getOID(): Long
        fun getINAME(): MutableList<String>
        fun getPRICE(): Int
        fun getCREATE(): LocalDateTime
        fun getSTATUS(): OrderStatus
    }
}