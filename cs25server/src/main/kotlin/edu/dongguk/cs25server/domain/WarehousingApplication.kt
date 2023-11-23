package edu.dongguk.cs25server.domain

import jakarta.persistence.*
import java.sql.Timestamp

class WarehousingApplication(
    @Enumerated(EnumType.STRING)
    private var supplierName: String,

    @Column(name = "created_at")
    private var createdAt: Timestamp,

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
}