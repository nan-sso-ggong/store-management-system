package edu.dongguk.cs25backend.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.*;

@Entity
@Getter
@NoArgsConstructor
public class OrderItemCS {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    private int orderPrice; //주문가격

    private int count;  //주문 수량

    //== 연관 관계 매핑 ==//
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_cs_id")
    private ItemCS itemCS;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;


    @Builder
    public OrderItemCS(int orderPrice, int count, ItemCS itemCS, Order order) {
        this.orderPrice = orderPrice;
        this.count = count;
        this.itemCS = itemCS;
        this.order = order;
    }

    //==생성 메서드==//
    public static OrderItemCS createOrderItem(ItemCS itemCS, int orderPrice, int count) {
        OrderItemCS orderItemCS = OrderItemCS.builder()
                .itemCS(itemCS)
                .orderPrice(orderPrice)
                .count(count)
                .build();

        itemCS.removeStock(count);
        return orderItemCS;
    }

    //== 연관 관계 메소드 ==//
    public void setOrder(Order order) {
        this.order = order;
    }



    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }

    public void cancel() {
        getItemCS().addStock(count);
    }


}
