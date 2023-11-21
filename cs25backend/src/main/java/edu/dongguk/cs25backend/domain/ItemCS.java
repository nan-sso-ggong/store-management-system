package edu.dongguk.cs25backend.domain;

import edu.dongguk.cs25backend.domain.type.ItemCategory;
import edu.dongguk.cs25backend.exception.CS25Exception;
import edu.dongguk.cs25backend.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;

@Entity
@Getter
@NoArgsConstructor
@DynamicUpdate
@Table(name = "item_CS")
public class ItemCS {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_cs_id")
    private Long id;

    private String name;

    private int price;

    private int stock;

    private String thumbnail;

    @Enumerated(EnumType.STRING)
    private ItemCategory itemCategory;

    //== 연관 관계 매핑 ==//

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_hq_id")
    private ItemHQ itemHQ;

    @OneToMany(mappedBy = "itemCS", cascade = ALL)
    private List<OrderItemCS> orderItems = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;


    public void addStock(int stock) {
        this.stock += stock;
    }

    public void removeStock(int stock) {
        int restStock = this.stock - stock;
        if (restStock < 0) {
            throw new CS25Exception(ErrorCode.NOT_ENOUGH_ERROR);
        }
        this.stock = restStock;
    }

    @Builder
    public ItemCS(String name, int price, int stock, String thumbnail, ItemCategory itemCategory, ItemHQ itemHQ, Store store) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.thumbnail = thumbnail;
        this.itemCategory = itemCategory;
        this.itemHQ = itemHQ;
        this.store = store;
    }
}
