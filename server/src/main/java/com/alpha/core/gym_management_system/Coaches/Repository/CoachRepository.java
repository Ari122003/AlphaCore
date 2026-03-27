package com.alpha.core.gym_management_system.Coaches.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alpha.core.gym_management_system.Coaches.Entity.Coach;

public interface CoachRepository extends JpaRepository<Coach, Long> {

    Optional<Coach> findByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCase(String email);
}
