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
@Table(name = "order_application")
public class OrderApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Column(name = "count")
    private int count;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "is_stocked")
    private Boolean isStocked;

    @Column(name = "stocked_date")
    private Timestamp stockedDate;

    /*--------------------연관 관계 매핑--------------------*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_hq_id")
    private ItemHQ itemHQ;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "convenience_store_id")
//    private ConvenienceStore convenienceStore;

    /*--------------------메서드--------------------*/
    @Builder
    public OrderApplication(int count, Timestamp createdAt, Boolean isStocked, Timestamp stockedDate, ItemHQ itemHQ) {
        this.count = count;
        this.createdAt = createdAt;
        this.isStocked = isStocked;
        this.stockedDate = stockedDate;
        this.itemHQ = itemHQ;
    }
}
