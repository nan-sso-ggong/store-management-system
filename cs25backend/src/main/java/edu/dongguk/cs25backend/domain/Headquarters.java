package edu.dongguk.cs25backend.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "headquarters")
public class Headquarters {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "headquarters_id")
    private Long id;

    @Column(name = "login_id")
    private String loginId;

    @Column(name = "password")
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    /*--------------------연관 관계 매핑--------------------*/
    @OneToMany(mappedBy = "headquarters")
    private List<ItemHQ> itemHQs = new ArrayList<>();

    /*--------------------메서드--------------------*/
    @Builder
    public Headquarters(String loginId, String password, String phoneNumber, List<ItemHQ> itemHQs) {
        this.loginId = loginId;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.itemHQs = itemHQs;
    }
}
