package edu.dongguk.cs25backend.domain;

import edu.dongguk.cs25backend.domain.type.MemberShip;
import edu.dongguk.cs25backend.domain.type.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manager_id")
    private Long id;

    private String loginId;

    private String password;

    private String name;

    private String email;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Enumerated(EnumType.STRING)
    private MemberShip memberShip;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    //== 연관 관계 매핑 ==//
    @OneToMany(mappedBy = "manage")
    private List<Store> stores;

}
