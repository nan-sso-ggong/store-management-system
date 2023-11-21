package edu.dongguk.cs25backend.domain;

import edu.dongguk.cs25backend.domain.type.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@Table(name = "orders")
@Getter
@DynamicUpdate
@NoArgsConstructor(access = PROTECTED)
public class Order {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime stateModifyAt;

    //== 연관 관계 매핑 ==//
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = ALL)
    private List<OrderItemCS> orderItems;


    //==연관관계 편의 메소드 -> 양방향일때 ==//
    public void setCustomer(Customer customer) {
        this.customer = customer;
        customer.getOrders().add(this);
    }

    public void addOrderItem(OrderItemCS orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void updateOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    // TODO 결제 진행
    public static Order createOrder(Customer customer, OrderItemCS... orderItems) {
        Order order = new Order();
        order.setCustomer(customer);

        for (OrderItemCS orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.updateOrderStatus(OrderStatus.READY);
        return order;
    }


    public void cancel() {
        if (this.getOrderStatus() == OrderStatus.PICKUP) {
            throw new IllegalStateException("이미 픽업된 상품은 취소가 불가능합니다.");
        }

        this.updateOrderStatus(OrderStatus.CANCEL);
        for (OrderItemCS orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    public int getTotalPrice() {
        return orderItems.stream().mapToInt(OrderItemCS::getTotalPrice).sum();
    }



}
