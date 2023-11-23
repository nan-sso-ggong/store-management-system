package edu.dongguk.cs25server.domain

import edu.dongguk.cs25server.domain.type.ItemCategory
import jakarta.persistence.*
import org.hibernate.annotations.DynamicUpdate

@Entity
@DynamicUpdate
@Table(name = "item_HQ")
class ItemHQ(
    @Column(name = "item_name")
    private var itemName: String,

    @Column(name = "price")
    private var price: Int,

    @Column(name = "stock")
    private var stock: Int,

    @Enumerated(EnumType.STRING)
    private var category: ItemCategory
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_hq_id")
    private val id: Long? = null

    /*--------------------연관 관계 매핑--------------------*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "headquarters_id")
    private val headquarters: Headquarters? = null

//    @OneToMany(mappedBy = "itemHQ")
//    private lateinit var orderApplications: List<OrderApplication>
//
//    @OneToMany(mappedBy = "itemHQ")
//    private lateinit var itemCS: List<ItemCS>

    /*--------------------메서드--------------------*/
}