package com.alpha.core.gym_management_system.security.util;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import com.alpha.core.gym_management_system.Exceptions.auth.RateLimitExceededException;

/**
 * Rate limiting utility to prevent brute force attacks
 */
@Component
public class RateLimiter {

    private static final int MAX_ATTEMPTS = 5;
    private static final int BLOCK_DURATION_MINUTES = 15;

    private final ConcurrentHashMap<String, RateLimitEntry> rateLimitMap = new ConcurrentHashMap<>();

    /**
     * Check if email should be rate limited
     * 
     * @param email the email to check
     * @throws RateLimitExceededException if rate limit exceeded
     */
    public void checkRateLimit(String email) {
        RateLimitEntry entry = rateLimitMap.get(email);

        if (entry != null) {
            if (isBlocked(entry)) {
                throw new RateLimitExceededException(email);
            }

            // Reset if block duration expired
            if (isBlockExpired(entry)) {
                rateLimitMap.remove(email);
            } else if (entry.attemptCount >= MAX_ATTEMPTS) {
                entry.blockedUntil = LocalDateTime.now().plusMinutes(BLOCK_DURATION_MINUTES);
                throw new RateLimitExceededException(email);
            }
        }
    }

    /**
     * Record a failed attempt
     */
    public void recordFailedAttempt(String email) {
        RateLimitEntry entry = rateLimitMap.getOrDefault(email, new RateLimitEntry());
        entry.attemptCount++;
        entry.lastAttempt = LocalDateTime.now();

        if (entry.attemptCount >= MAX_ATTEMPTS) {
            entry.blockedUntil = LocalDateTime.now().plusMinutes(BLOCK_DURATION_MINUTES);
        }

        rateLimitMap.put(email, entry);
    }

    /**
     * Reset attempts for successful operation
     */
    public void resetAttempts(String email) {
        rateLimitMap.remove(email);
    }

    /**
     * Clean up expired entries (can be called periodically)
     */
    public void cleanup() {
        rateLimitMap.entrySet().removeIf(entry -> isBlockExpired(entry.getValue()));
    }

    private boolean isBlocked(RateLimitEntry entry) {
        return entry.blockedUntil != null && LocalDateTime.now().isBefore(entry.blockedUntil);
    }

    private boolean isBlockExpired(RateLimitEntry entry) {
        return entry.blockedUntil != null && LocalDateTime.now().isAfter(entry.blockedUntil)
                && ChronoUnit.HOURS.between(entry.blockedUntil, LocalDateTime.now()) > 1;
    }

    @SuppressWarnings("unused")
    private static class RateLimitEntry {
        int attemptCount = 0;
        LocalDateTime lastAttempt = LocalDateTime.now();
        LocalDateTime blockedUntil = null;
    }
}
