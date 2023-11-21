package edu.dongguk.cs25backend.domain;

import edu.dongguk.cs25backend.domain.type.ItemCategory;
import edu.dongguk.cs25backend.exception.CS25Exception;
import edu.dongguk.cs25backend.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

import static jakarta.persistence.CascadeType.ALL;

@Entity
@Getter
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
    private List<OrderItemCS> orderItems;

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


}
