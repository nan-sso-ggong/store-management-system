package edu.dongguk.cs25backend.domain;

import edu.dongguk.cs25backend.domain.type.LoginProvider;
import edu.dongguk.cs25backend.domain.type.MemberShip;
import edu.dongguk.cs25backend.domain.type.UserRole;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@DynamicUpdate
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;

    private String name;

    private String socialId;

    private String refreshToken;

    private int point;

    @Enumerated(EnumType.STRING)
    private LoginProvider provider;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    private MemberShip memberShip; //멤버십 레벨

    private Boolean isLogin;

    private Boolean isValid;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    //== 연관 관계 매핑 ==//
    @OneToMany(mappedBy = "customer")
    private List<Order> orders = new ArrayList<>();


    @Builder
    public Customer(String name, String socialId, LoginProvider provider) {
        this.name = name;
        this.socialId = socialId;
        this.provider = provider;
        this.role = UserRole.CUSTOMER;
        this.memberShip = MemberShip.NORMAL;
        this.point = 0;
        this.isLogin = true;
        this.isValid = true;
        this.createdAt = LocalDateTime.now();
    }

    public void savePoint(int totalPrice) {
        switch(this.memberShip) {
            // 총 금액의 2, 4, 6퍼 적립
            case BRONZE -> this.point += (int)(totalPrice * 0.02);
            case SILVER -> this.point += (int)(totalPrice * 0.04);
            case GOLD -> this.point += (int)(totalPrice * 0.06);
        }
    }

    public void setIsLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

}
