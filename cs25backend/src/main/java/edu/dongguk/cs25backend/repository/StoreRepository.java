package edu.dongguk.cs25backend.repository;

import edu.dongguk.cs25backend.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
}
