package edu.dongguk.cs25backend.domain;

import edu.dongguk.cs25backend.domain.type.MemberShip;
import edu.dongguk.cs25backend.domain.type.UserRole;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "managers")
public class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manager_id")
    private Long id;

    @JoinColumn(name = "login_id", nullable = false)
    private String loginId;

    @JoinColumn(name = "password", nullable = false)
    private String password;

    @JoinColumn(name = "name", nullable = false)
    private String name;

    @JoinColumn(name = "email", nullable = false)
    private String email;

    @JoinColumn(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "user_role", nullable = false)
    private UserRole userRole;

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "membership", nullable = false)
    private MemberShip memberShip;

    @Column(updatable = false)
    @JoinColumn(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @JoinColumn(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    //== 연관 관계 매핑 ==//
    @OneToMany(mappedBy = "manage")
    @JoinColumn(name = "store_id", nullable = false)
    private List<Store> stores;

    @Builder
    public Manager(String loginId, String password, String name, String email, String phoneNumber, UserRole userRole, MemberShip memberShip) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.userRole = userRole;
        this.memberShip = memberShip;
        this.createdAt = LocalDateTime.now();
    }
}
