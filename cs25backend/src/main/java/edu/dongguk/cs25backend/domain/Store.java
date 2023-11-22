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
@Table(name = "stores")
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long id;

    @JoinColumn(name = "name", nullable = false)
    private String name;

    @JoinColumn(name = "address", nullable = false)
    private String address;

    @JoinColumn(name = "call_number", nullable = false)
    private String callNumber;

    @JoinColumn(name = "thumbnail", nullable = false)
    private String thumbnail;

    //== 연관 관계 매핑 ==//
    @OneToMany(mappedBy = "store")
    private List<ItemCS> itemCSs = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private Manager manager;

    @OneToMany(mappedBy = "store")
    private List<OrderApplication> orderApplications = new ArrayList<>();

    @Builder
    public Store(String name, String address, String callNumber, String thumbnail, Manager manager) {
        this.name = name;
        this.address = address;
        this.callNumber = callNumber;
        this.thumbnail = thumbnail;
        this.manager = manager;
    }
}
