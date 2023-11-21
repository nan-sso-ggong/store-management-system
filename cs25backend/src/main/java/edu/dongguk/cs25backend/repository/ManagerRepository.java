package edu.dongguk.cs25backend.repository;

import edu.dongguk.cs25backend.domain.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
    Optional<Manager> findByLoginIdOrNameOrEmailOrPhoneNumber(String loginId);
}
