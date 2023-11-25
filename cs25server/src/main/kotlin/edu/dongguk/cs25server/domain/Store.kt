package edu.dongguk.cs25server.domain

import edu.dongguk.cs25server.domain.type.AllowStatus
import edu.dongguk.cs25server.dto.response.StoreResponseDto
import jakarta.persistence.*
import org.hibernate.annotations.DynamicUpdate
import java.util.List

@Entity
@DynamicUpdate
@Table(name = "stores")
class Store(
    @Column(name = "name", nullable = false)
    val name: String,
    @Column(name = "address", nullable = false)
    val address: String,
    @Column(name = "call_number", nullable = false)
    val callNumber: String,
    @Column(name = "thumbnail", nullable = false)
    val thumbnail: String,
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    val status: AllowStatus = AllowStatus.BEFORE
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    val id: Long? = null

    @OneToMany(mappedBy = "store")
    private lateinit var itemCS: List<ItemCS>

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private lateinit var manager: Manager

    @OneToMany(mappedBy = "store")

    private lateinit var orderApplications: List<OrderApplication>

    constructor(
        name: String,
        address: String,
        callNumber: String,
        thumbnail: String,
        manager: Manager
    ) : this(name, address, callNumber, thumbnail) {
        this.manager = manager
    }

    fun toResponse(): StoreResponseDto = StoreResponseDto(
        id = this.id,
        name = this.name,
        address = this.address
    )
}


