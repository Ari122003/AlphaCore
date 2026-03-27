package com.alpha.core.gym_management_system.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alpha.core.gym_management_system.security.entity.OtpToken;
import com.alpha.core.gym_management_system.security.entity.UserAccount;
import com.alpha.core.gym_management_system.security.model.OtpPurpose;

public interface OtpTokenRepository extends JpaRepository<OtpToken, Long> {

    Optional<OtpToken> findTopByUserAccountAndPurposeAndUsedIsFalseOrderByCreatedAtDesc(UserAccount userAccount,
            OtpPurpose purpose);
}
