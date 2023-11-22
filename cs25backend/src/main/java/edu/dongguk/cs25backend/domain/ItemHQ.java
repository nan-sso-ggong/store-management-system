package edu.dongguk.cs25backend.domain;

import edu.dongguk.cs25backend.domain.type.ItemCategory;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
@DynamicUpdate
@Table(name = "item_HQ")
public class ItemHQ {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_hq_id")
    private Long id;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "price")
    private int price;

    @Column(name = "stock")
    private int stock;

    @Enumerated(EnumType.STRING)
    private ItemCategory category;

    /*--------------------연관 관계 매핑--------------------*/
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "headquarters_id")
    private Headquarters headquarters;

    @OneToMany(mappedBy = "itemHQ")
    private List<OrderApplication> orderApplications = new ArrayList<>();

    @OneToMany(mappedBy = "itemHQ")
    private List<ItemCS> itemCS = new ArrayList<>();

    /*--------------------메서드--------------------*/
    @Builder
    public ItemHQ(String itemName, int price, int stock, ItemCategory category, Headquarters headquarters) {
        this.itemName = itemName;
        this.price = price;
        this.stock = stock;
        this.category = category;
        this.headquarters = headquarters;
    }
}
