package com.alpha.core.gym_management_system.auth.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alpha.core.gym_management_system.Admins.Entity.Admin;
import com.alpha.core.gym_management_system.Admins.Repository.AdminRepository;
import com.alpha.core.gym_management_system.Coaches.Entity.Coach;
import com.alpha.core.gym_management_system.Coaches.Repository.CoachRepository;
import com.alpha.core.gym_management_system.Exceptions.BaseException;
import com.alpha.core.gym_management_system.Exceptions.auth.*;
import com.alpha.core.gym_management_system.auth.dto.AdminRegistrationRequest;
import com.alpha.core.gym_management_system.auth.dto.CoachRegistrationRequest;
import com.alpha.core.gym_management_system.auth.dto.JwtResponse;
import com.alpha.core.gym_management_system.auth.dto.LoginRequest;
import com.alpha.core.gym_management_system.auth.dto.MessageResponse;
import com.alpha.core.gym_management_system.auth.dto.VerifyEmailRequest;
import com.alpha.core.gym_management_system.security.entity.UserAccount;
import com.alpha.core.gym_management_system.security.model.OtpPurpose;
import com.alpha.core.gym_management_system.security.model.Role;
import com.alpha.core.gym_management_system.security.repository.UserAccountRepository;
import com.alpha.core.gym_management_system.security.service.JwtService;
import com.alpha.core.gym_management_system.security.service.JwtService.TokenResult;
import com.alpha.core.gym_management_system.security.service.OtpService;
import com.alpha.core.gym_management_system.security.util.RateLimiter;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthService {

    private final AdminRepository adminRepository;
    private final CoachRepository coachRepository;
    private final UserAccountRepository userAccountRepository;
    private final OtpService otpService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final RateLimiter rateLimiter;

    // Temporary storage for registration details with expiration tracking
    private final ConcurrentHashMap<String, RegistrationData> pendingRegistrations = new ConcurrentHashMap<>();
    private final ScheduledExecutorService cleanupExecutor = Executors.newScheduledThreadPool(1);

    // TTL for pending registrations: 15 minutes (OTP valid for 10 minutes + 5 min
    // buffer)
    private static final long REGISTRATION_TTL_MINUTES = 15;

    public AuthService(AdminRepository adminRepository, CoachRepository coachRepository,
            UserAccountRepository userAccountRepository, OtpService otpService, JwtService jwtService,
            PasswordEncoder passwordEncoder, RateLimiter rateLimiter) {
        this.adminRepository = adminRepository;
        this.coachRepository = coachRepository;
        this.userAccountRepository = userAccountRepository;
        this.otpService = otpService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.rateLimiter = rateLimiter;

        // Start cleanup task to prevent memory leak (runs every 5 minutes)
        cleanupExecutor.scheduleAtFixedRate(this::cleanupExpiredRegistrations, 5, 5, TimeUnit.MINUTES);
    }

    @Transactional
    public MessageResponse registerAdmin(AdminRegistrationRequest request) {
        // Check rate limit
        rateLimiter.checkRateLimit(request.getEmailId());

        // Check if email already exists - throws exception if it does
        ensureEmailIsAvailable(request.getEmailId());

        // Store registration details temporarily (with expiration)
        pendingRegistrations.put(request.getEmailId(),
                new RegistrationData(request, LocalDateTime.now().plusMinutes(REGISTRATION_TTL_MINUTES)));

        // Create UserAccount without admin profile yet
        UserAccount account = UserAccount.builder()
                .email(request.getEmailId())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .role(Role.ADMIN)
                .build();
        userAccountRepository.save(account);

        // Generate and send OTP
        otpService.generateAndDeliverOtp(account, OtpPurpose.REGISTRATION);

        log.info("Admin registration initiated for email: {}", request.getEmailId());
        rateLimiter.resetAttempts(request.getEmailId());

        return MessageResponse.builder()
                .message("Registration initiated. OTP sent to your email.")
                .build();
    }

    @Transactional
    public MessageResponse registerCoach(CoachRegistrationRequest request) {
        // Check rate limit
        rateLimiter.checkRateLimit(request.getEmail());

        // Check if email already exists - throws exception if it does
        ensureEmailIsAvailable(request.getEmail());

        // Store registration details temporarily (with expiration)
        pendingRegistrations.put(request.getEmail(),
                new RegistrationData(request, LocalDateTime.now().plusMinutes(REGISTRATION_TTL_MINUTES)));

        // Create UserAccount without coach profile yet
        UserAccount account = UserAccount.builder()
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .role(Role.COACH)
                .build();
        userAccountRepository.save(account);

        // Generate and send OTP
        otpService.generateAndDeliverOtp(account, OtpPurpose.REGISTRATION);

        log.info("Coach registration initiated for email: {}", request.getEmail());
        rateLimiter.resetAttempts(request.getEmail());

        return MessageResponse.builder()
                .message("Registration initiated. OTP sent to your email.")
                .build();
    }

    @Transactional
    public JwtResponse verifyEmail(VerifyEmailRequest request) {
        // Check rate limit
        rateLimiter.checkRateLimit(request.getEmail());

        try {
            UserAccount account = userAccountRepository.findByEmailIgnoreCase(request.getEmail())
                    .orElseThrow(() -> new InvalidCredentialsException("No account found with the provided email"));

            // Check if already verified
            if (account.isEmailVerified()) {
                throw new InvalidCredentialsException("Email already verified for this account");
            }

            // Validate OTP
            otpService.validateOtp(account, OtpPurpose.REGISTRATION, request.getOtpCode());

            // Get pending registration details
            RegistrationData pendingData = pendingRegistrations.get(request.getEmail());
            if (pendingData == null || pendingData.isExpired()) {
                // Clean up if expired
                if (pendingData != null) {
                    pendingRegistrations.remove(request.getEmail());
                }
                throw new RegistrationExpiredException("Registration details have expired");
            }

            // Create profile based on role and save details
            if (account.getRole() == Role.ADMIN) {
                AdminRegistrationRequest adminRequest = (AdminRegistrationRequest) pendingData.data;
                Admin admin = Admin.builder()
                        .gymName(adminRequest.getGymName())
                        .gymAddress(adminRequest.getGymAddress())
                        .contactNumber(adminRequest.getContactNumber())
                        .emailId(request.getEmail())
                        .build();
                Admin savedAdmin = adminRepository.save(admin);
                account.setAdminProfile(savedAdmin);
            } else if (account.getRole() == Role.COACH) {
                CoachRegistrationRequest coachRequest = (CoachRegistrationRequest) pendingData.data;
                Coach coach = Coach.builder()
                        .name(coachRequest.getName())
                        .number(coachRequest.getNumber())
                        .email(request.getEmail())
                        .gymId(coachRequest.getGymId())
                        .age(coachRequest.getAge())
                        .gender(coachRequest.getGender())
                        .yearsOfExperience(coachRequest.getYearsOfExperience())
                        .otherDetails(coachRequest.getOtherDetails())
                        .build();
                Coach savedCoach = coachRepository.save(coach);
                account.setCoachProfile(savedCoach);
            }

            // Mark email as verified and set last login time
            account.setEmailVerified(true);
            account.setLastLoginAt(LocalDateTime.now());
            userAccountRepository.save(account);

            // Remove from pending registrations
            pendingRegistrations.remove(request.getEmail());

            log.info("Email verified successfully for: {}", request.getEmail());
            rateLimiter.resetAttempts(request.getEmail());

            // Generate JWT token
            TokenResult tokenResult = jwtService.generateToken(account);
            return JwtResponse.builder()
                    .accessToken(tokenResult.getToken())
                    .tokenType("Bearer")
                    .expiresAt(tokenResult.getExpiresAt())
                    .role(account.getRole())
                    .build();
        } catch (BaseException e) {
            rateLimiter.recordFailedAttempt(request.getEmail());
            
            throw e;
        }
    }

    @Transactional
    public JwtResponse login(LoginRequest request) {
        // Check rate limit
        rateLimiter.checkRateLimit(request.getEmail());

        try {
            UserAccount account = userAccountRepository
                    .findByEmailIgnoreCaseAndRole(request.getEmail(), request.getRole())
                    .orElseThrow(() -> InvalidCredentialsException.invalidEmail());

            // Check if email is verified
            if (!account.isEmailVerified()) {
                log.warn("Login attempt with unverified email: {}", request.getEmail());
                throw new UnverifiedEmailException(request.getEmail());
            }

            // Check password
            if (!passwordEncoder.matches(request.getPassword(), account.getPasswordHash())) {
                log.warn("Failed login attempt for: {}", request.getEmail());
                throw InvalidCredentialsException.invalidPassword();
            }

            // Update last login time
            account.setLastLoginAt(LocalDateTime.now());
            userAccountRepository.save(account);

            log.info("Login successful for: {}", request.getEmail());
            rateLimiter.resetAttempts(request.getEmail());

            TokenResult tokenResult = jwtService.generateToken(account);
            return JwtResponse.builder()
                    .accessToken(tokenResult.getToken())
                    .tokenType("Bearer")
                    .expiresAt(tokenResult.getExpiresAt())
                    .role(account.getRole())
                    .build();
        } catch (BaseException e) {
            rateLimiter.recordFailedAttempt(request.getEmail());
            throw e;
        }
    }

    private void ensureEmailIsAvailable(String email) {
        boolean exists = userAccountRepository.existsByEmailIgnoreCase(email);
        if (exists) {
            log.warn("Registration attempt with existing email: {}", email);
            throw new EmailAlreadyExistsException(email);
        }
    }

    /**
     * Clean up expired registration data (called periodically)
     */
    private void cleanupExpiredRegistrations() {
        pendingRegistrations.entrySet().removeIf(entry -> entry.getValue().isExpired());
        log.debug("Cleaned up expired registrations");
    }

    /**
     * Inner class to track expiration of registration data
     */
    private static class RegistrationData {
        final Object data;
        final LocalDateTime expiresAt;

        RegistrationData(Object data, LocalDateTime expiresAt) {
            this.data = data;
            this.expiresAt = expiresAt;
        }

        boolean isExpired() {
            return LocalDateTime.now().isAfter(expiresAt)
                    || ChronoUnit.MINUTES.between(expiresAt, LocalDateTime.now()) > 0;
        }
    }

    // Cleanup on service shutdown
    public void shutdown() {
        cleanupExecutor.shutdown();
        try {
            if (!cleanupExecutor.awaitTermination(10, TimeUnit.SECONDS)) {
                cleanupExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            cleanupExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
