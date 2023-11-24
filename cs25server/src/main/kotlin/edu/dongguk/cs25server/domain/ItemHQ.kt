package edu.dongguk.cs25server.domain

import edu.dongguk.cs25server.domain.type.ItemCategory
import jakarta.persistence.*
import org.hibernate.annotations.DynamicUpdate

@Entity
@DynamicUpdate
@Table(name = "item_HQ")
class ItemHQ(
    @Column(name = "item_name")
    var itemName: String,

    @Column(name = "price")
    var price: Long,

    @Column(name = "stock")
    var stock: Long,

    @Enumerated(EnumType.STRING)
    var category: ItemCategory
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_hq_id")
    val id: Long? = null

    /*--------------------연관 관계 매핑--------------------*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "headquarters_id")
    val headquarters: Headquarters? = null

    @OneToMany(mappedBy = "itemHQ")
    lateinit var orderApplications: List<OrderApplication>

    @OneToMany(mappedBy = "itemHQ")
    lateinit var itemCSs: List<ItemCS>

    @OneToMany(mappedBy = "itemHQ")
    lateinit var warehousingApplications: List<WarehousingApplication>

    /*--------------------메서드--------------------*/
}