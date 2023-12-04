package edu.dongguk.cs25server.repository

import edu.dongguk.cs25server.domain.Point
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface PointRepository: JpaRepository<Point, Long> {

    @Query("SELECT COALESCE(sum(p.amount), 0) FROM Point p WHERE p.customer.id = :customerId AND p.isSave = true")
    fun findTotalPointByCustomer(@Param("customerId") customerId: Long): Int
}