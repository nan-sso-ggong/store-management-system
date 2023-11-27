package edu.dongguk.cs25server.repository

import edu.dongguk.cs25server.domain.Order
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepostiory: JpaRepository<Order, Long> {
}