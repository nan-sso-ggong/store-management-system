package edu.dongguk.cs25backend.repository;

import edu.dongguk.cs25backend.domain.Customer;
import edu.dongguk.cs25backend.domain.type.LoginProvider;
import edu.dongguk.cs25backend.domain.type.UserRole;
import edu.dongguk.cs25backend.security.UserLoginForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findBySocialIdAndProvider(String socialId, LoginProvider provider);

    @Query("SELECT c.id AS id, c.role AS role FROM Customer c WHERE c.id = :customerId AND c.isLogin = true AND c.refreshToken is not null")
    Optional<UserLoginForm> findByIdAndRefreshToken(@Param("customerId") Long customerId);
}
