package com.alpha.core.gym_management_system.Exceptions.auth;

import org.springframework.http.HttpStatus;
import com.alpha.core.gym_management_system.Exceptions.BaseException;

/**
 * Exception thrown when attempting to login with unverified email
 */
public class UnverifiedEmailException extends BaseException {

    public UnverifiedEmailException(String email) {
        super("Email '" + email + "' has not been verified. Please verify your email before logging in.",
                HttpStatus.FORBIDDEN,
                "UNVERIFIED_EMAIL");
    }

    public UnverifiedEmailException() {
        super("Email has not been verified. Please verify your email before logging in.",
                HttpStatus.FORBIDDEN,
                "UNVERIFIED_EMAIL");
    }
}
