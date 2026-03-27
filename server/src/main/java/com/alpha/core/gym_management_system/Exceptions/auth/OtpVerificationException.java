package com.alpha.core.gym_management_system.Exceptions.auth;

import org.springframework.http.HttpStatus;
import com.alpha.core.gym_management_system.Exceptions.BaseException;

/**
 * Exception thrown when OTP verification fails
 */
public class OtpVerificationException extends BaseException {

    public OtpVerificationException(String message) {
        super(message, HttpStatus.UNAUTHORIZED, "OTP_VERIFICATION_FAILED");
    }

    public static OtpVerificationException invalidOtp() {
        return new OtpVerificationException("Invalid OTP code. Please try again.");
    }

    public static OtpVerificationException expiredOtp() {
        return new OtpVerificationException("OTP has expired. Please request a new one.");
    }

    public static OtpVerificationException maxAttemptsExceeded() {
        return new OtpVerificationException("Maximum OTP attempts exceeded. Please request a new OTP.");
    }
}
