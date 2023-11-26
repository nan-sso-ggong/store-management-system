package edu.dongguk.cs25server.domain

import edu.dongguk.cs25server.domain.type.Supplier
import jakarta.persistence.*
import org.hibernate.annotations.DynamicUpdate
import java.sql.Timestamp
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@DynamicUpdate
@Table(name = "warehousing_application")
class WarehousingApplication(
    @Enumerated(EnumType.STRING)
    private var supplierName: Supplier,

    @Column(name = "created_at")
    private var createdAt: LocalDate = LocalDate.now(),

    @Column(name = "is_stocked")
    private var isStocked: Boolean
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private val id: Long? = null

    /*--------------------연관 관계 매핑--------------------*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_hq_id")
    private val itemHQ: ItemHQ? = null

    /*--------------------메서드--------------------*/
    fun getCreatedAt():LocalDate {
        return this.createdAt
    }
}