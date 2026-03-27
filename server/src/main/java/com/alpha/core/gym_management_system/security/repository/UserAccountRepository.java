package com.alpha.core.gym_management_system.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alpha.core.gym_management_system.security.entity.UserAccount;
import com.alpha.core.gym_management_system.security.model.Role;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    Optional<UserAccount> findByEmailIgnoreCase(String email);

    Optional<UserAccount> findByEmailIgnoreCaseAndRole(String email, Role role);

    boolean existsByEmailIgnoreCase(String email);
}
