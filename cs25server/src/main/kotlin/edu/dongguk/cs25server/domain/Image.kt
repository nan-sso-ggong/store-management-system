package edu.dongguk.cs25server.domain

import edu.dongguk.cs25server.domain.type.Extension
import edu.dongguk.cs25server.domain.type.ImageCategory
import jakarta.persistence.*
import org.hibernate.annotations.DynamicUpdate

@Entity
@DynamicUpdate
@Table(name = "images")
class Image(
    @Column(name = "origin_name")
    private val originName: String?,

    @Column(name = "uuid_name")
    private val uuidName: String,

    @Column(name = "extension")
    @Enumerated(EnumType.STRING)
    private val extension: Extension,

    @Column(name = "image_category")
    @Enumerated(EnumType.STRING)
    private val imageCategory: ImageCategory,

    @Column(name = "access_url")
    private val accessUrl: String // S3 내부 이미지에 접근할 수 있는 URL
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private var id: Long? = null

    fun getUuidName() = this.uuidName

    fun getAccessUrl() = this.accessUrl
}