package edu.dongguk.cs25backend.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor
@DynamicUpdate
@Table(name = "warehousing_application")
public class WarehousingApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Column(name = "supplier_name")
    private String supplierName;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "is_stocked")
    private Boolean isStocked;

    /*--------------------연관 관계 매핑--------------------*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_hq_id")
    private ItemHQ itemHQ;


    /*--------------------메서드--------------------*/
    @Builder
    public WarehousingApplication(Long id, String supplierName, Timestamp createdAt, Boolean isStocked, ItemHQ itemHQ) {
        this.id = id;
        this.supplierName = supplierName;
        this.createdAt = createdAt;
        this.isStocked = isStocked;
        this.itemHQ = itemHQ;
    }
}