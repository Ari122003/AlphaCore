package com.alpha.core.gym_management_system.security.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alpha.core.gym_management_system.Exceptions.auth.OtpVerificationException;
import com.alpha.core.gym_management_system.security.entity.OtpToken;
import com.alpha.core.gym_management_system.security.entity.UserAccount;
import com.alpha.core.gym_management_system.security.model.OtpPurpose;
import com.alpha.core.gym_management_system.security.repository.OtpTokenRepository;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OtpService {

    private final SecureRandom secureRandom = new SecureRandom();
    private final OtpTokenRepository otpTokenRepository;
    private final EmailService emailService;
    private final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    @Value("${security.otp.length:6}")
    private int otpLength;

    @Value("${security.otp.ttl-minutes:10}")
    private long otpTtlMinutes;

    @Value("${security.otp.max-attempts:3}")
    private int maxAttempts;

    @Transactional
    public void generateAndDeliverOtp(UserAccount userAccount, OtpPurpose purpose) {
        String rawOtp = buildOtp();
        OtpToken token = new OtpToken();
        token.setUserAccount(userAccount);
        token.setPurpose(purpose);
        token.setOtpHash(passwordEncoder.encode(rawOtp));
        token.setExpiresAt(LocalDateTime.now().plusMinutes(otpTtlMinutes));
        otpTokenRepository.save(token);
        emailService.sendOtpEmail(userAccount.getEmail(), rawOtp);
    }

    @Transactional
    public void validateOtp(UserAccount userAccount, OtpPurpose purpose, @NotBlank String providedOtp) {
        OtpToken token = otpTokenRepository
                .findTopByUserAccountAndPurposeAndUsedIsFalseOrderByCreatedAtDesc(userAccount, purpose)
                .orElseThrow(() -> OtpVerificationException.invalidOtp());

        if (token.isExpired()) {
            token.markUsed();
            otpTokenRepository.save(token);
            throw OtpVerificationException.expiredOtp();
        }

        if (!passwordEncoder.matches(providedOtp, token.getOtpHash())) {
            token.incrementAttempts();
            if (token.getAttemptCount() >= maxAttempts) {
                token.markUsed();
            }
            otpTokenRepository.save(token);

            if (token.getAttemptCount() >= maxAttempts) {
                throw OtpVerificationException.maxAttemptsExceeded();
            } else {
                throw OtpVerificationException.invalidOtp();
            }
        }

        token.markUsed();
        otpTokenRepository.save(token);
    }

    private String buildOtp() {
        StringBuilder otpBuilder = new StringBuilder();
        for (int i = 0; i < otpLength; i++) {
            otpBuilder.append(secureRandom.nextInt(10));
        }
        return otpBuilder.toString();
    }
}
