package com.alpha.core.gym_management_system.Admins.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alpha.core.gym_management_system.Admins.Entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByEmailIdIgnoreCase(String emailId);

    boolean existsByEmailIdIgnoreCase(String emailId);
}
