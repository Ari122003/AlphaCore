package com.alpha.core.gym_management_system.Exceptions.auth;

import org.springframework.http.HttpStatus;
import com.alpha.core.gym_management_system.Exceptions.BaseException;

/**
 * Exception thrown when registration data has expired
 */
public class RegistrationExpiredException extends BaseException {

    public RegistrationExpiredException() {
        super("Registration has expired. Please start the registration process again.",
                HttpStatus.BAD_REQUEST,
                "REGISTRATION_EXPIRED");
    }

    public RegistrationExpiredException(String details) {
        super("Registration has expired: " + details + ". Please start the registration process again.",
                HttpStatus.BAD_REQUEST,
                "REGISTRATION_EXPIRED");
    }
}
