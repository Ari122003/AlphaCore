package com.alpha.core.gym_management_system.Exceptions.auth;

import org.springframework.http.HttpStatus;
import com.alpha.core.gym_management_system.Exceptions.BaseException;

/**
 * Exception thrown when email/user already exists during registration
 */
public class EmailAlreadyExistsException extends BaseException {

    public EmailAlreadyExistsException(String email) {
        super(
                String.format(
                        "This email '%s' is already registered. Please use a different email or login if you already have an account.",
                        email),
                HttpStatus.CONFLICT,
                "EMAIL_ALREADY_EXISTS");
    }
}
