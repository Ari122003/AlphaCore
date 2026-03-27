package com.alpha.core.gym_management_system.Exceptions.auth;

import org.springframework.http.HttpStatus;
import com.alpha.core.gym_management_system.Exceptions.BaseException;

/**
 * Exception thrown when credentials (email or password) are invalid
 */
public class InvalidCredentialsException extends BaseException {

    public InvalidCredentialsException() {
        super("Invalid email or password. Please try again.",
                HttpStatus.UNAUTHORIZED,
                "INVALID_CREDENTIALS");
    }

    public InvalidCredentialsException(String message) {
        super(message,
                HttpStatus.UNAUTHORIZED,
                "INVALID_CREDENTIALS");
    }

    public static InvalidCredentialsException invalidEmail() {
        return new InvalidCredentialsException("No account found with the provided email.");
    }

    public static InvalidCredentialsException invalidPassword() {
        return new InvalidCredentialsException("Invalid password. Please try again.");
    }
}
