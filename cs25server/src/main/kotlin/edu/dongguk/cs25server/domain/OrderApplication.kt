package edu.dongguk.cs25server.domain

import jakarta.persistence.*
import org.hibernate.annotations.DynamicUpdate
import java.sql.Timestamp
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@DynamicUpdate
@Table(name = "order_application")
class OrderApplication(
    @Column(name = "count")
    private var count: Long,

    @Column(name = "created_at")
    private val createdAt: LocalDateTime,

    @Column(name = "is_stocked")
    private var isStocked: Boolean,

    @Column(name = "stocked_date")
    private var stockedDate: LocalDate = LocalDate.now()
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private val id: Long? = null

    /*--------------------연관 관계 매핑--------------------*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_hq_id")
    private val itemHQ: ItemHQ? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private val store: Store? = null

    /*--------------------메서드--------------------*/
}