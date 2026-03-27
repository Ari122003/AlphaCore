package com.alpha.core.gym_management_system.Exceptions.auth;

import org.springframework.http.HttpStatus;
import com.alpha.core.gym_management_system.Exceptions.BaseException;

/**
 * Exception thrown when rate limit is exceeded for an operation
 */
public class RateLimitExceededException extends BaseException {

    public RateLimitExceededException(String email) {
        super("Too many attempts for email: " + email + ". Please try again after 15 minutes.",
                HttpStatus.TOO_MANY_REQUESTS,
                "RATE_LIMIT_EXCEEDED");
    }

    public RateLimitExceededException(String email, long minutesRemaining) {
        super("Too many attempts. Please try again in " + minutesRemaining + " minutes.",
                HttpStatus.TOO_MANY_REQUESTS,
                "RATE_LIMIT_EXCEEDED");
    }
}
