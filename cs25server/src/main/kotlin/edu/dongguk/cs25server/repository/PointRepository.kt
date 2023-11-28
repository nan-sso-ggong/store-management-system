package edu.dongguk.cs25server.repository

import edu.dongguk.cs25server.domain.Point
import org.springframework.data.jpa.repository.JpaRepository

interface PointRepository: JpaRepository<Point, Long> {
}