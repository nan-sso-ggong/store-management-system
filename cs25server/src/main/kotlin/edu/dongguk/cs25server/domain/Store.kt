package edu.dongguk.cs25server.domain

import edu.dongguk.cs25server.domain.type.AllowStatus
import edu.dongguk.cs25server.dto.request.StoreEditRequestDto
import edu.dongguk.cs25server.dto.response.StoreDetailResponseDto
import edu.dongguk.cs25server.dto.response.StoreResponseDto
import jakarta.persistence.*
import org.hibernate.annotations.DynamicUpdate
import java.time.LocalDate
import java.util.List

@Entity
@DynamicUpdate
@Table(name = "stores")
class Store(
    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "address", nullable = false)
    var address: String,

    @Column(name = "call_number", nullable = false)
    var callNumber: String,

    @Column(name = "thumbnail", nullable = false)
    val thumbnail: String,

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    val status: AllowStatus = AllowStatus.BEFORE,

    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDate = LocalDate.now(),
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

    fun toDetailResponse(): StoreDetailResponseDto = StoreDetailResponseDto(
            store_id = this.id,
            store_name = this.name,
            address = this.address,
            store_tel = this.address
    )

    fun editStore(storeEditRequestDto: StoreEditRequestDto): Boolean {
        this.address = storeEditRequestDto.store_address
        this.callNumber = storeEditRequestDto.store_call_number
        return true
    }
}


