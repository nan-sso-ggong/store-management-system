package edu.dongguk.cs25server.domain

import edu.dongguk.cs25server.dto.response.StoreReponseDto
import jakarta.persistence.*
import org.hibernate.annotations.DynamicUpdate
import java.util.ArrayList
import java.util.List

@Entity
@DynamicUpdate
@Table(name = "stores")
class Store(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "store_id")
        val id: Long? = null,
        @JoinColumn(name = "name", nullable = false)
        val name: String,
        @JoinColumn(name = "address", nullable = false)
        val address: String,
        @JoinColumn(name = "call_number", nullable = false)
        val callNumber: String,
        @JoinColumn(name = "thumbnail", nullable = false)
        val thumbnail: String,
) {
    @OneToMany(mappedBy = "store")
    private lateinit var itemCS: List<ItemCS>

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private var manager: Manager? = null

    @OneToMany(mappedBy = "store")
    private lateinit var orderApplications: List<OrderApplication>

    constructor(
            name: String,
            address: String,
            callNumber: String,
            thumbnail: String,
            manager: Manager
    ) : this(null, name, address, callNumber, thumbnail) {
        this.manager = manager
    }

    fun toResponse(): StoreReponseDto = StoreReponseDto(
        id = this.id,
        name = this.name,
        address = this.address)
}


