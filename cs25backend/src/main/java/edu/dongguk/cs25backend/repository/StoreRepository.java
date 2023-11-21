package edu.dongguk.cs25backend.repository;

import edu.dongguk.cs25backend.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    Optional<Store> findTop1ByNameOrCallNumber(String name, String callNumber);
}
