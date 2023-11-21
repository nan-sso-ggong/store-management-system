package edu.dongguk.cs25backend.repository;

import edu.dongguk.cs25backend.domain.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {
    Optional<Manager> findTop1ByLoginIdOrNameOrEmailOrPhoneNumber(String loginId, String name, String email, String phoneNumber);
}
