package edu.dongguk.cs25backend.domain;

import edu.dongguk.cs25backend.exception.CS25Exception;
import edu.dongguk.cs25backend.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.Getter;

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

    // TODO 카테고리 추가
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
