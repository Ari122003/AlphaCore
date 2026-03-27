package com.alpha.core.gym_management_system.security.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.mail.from:no-reply@alphacore.local}")
    private String fromAddress;

    public void sendOtpEmail(String recipientEmail, String otpCode) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(recipientEmail);
            message.setFrom(fromAddress);
            message.setSubject("Your AlphaCore one-time password");
            message.setText(buildMessageBody(otpCode));
            mailSender.send(message);
        } catch (Exception ex) {
            log.error("Failed to send OTP email to {}", recipientEmail, ex);
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "Unable to dispatch OTP email. Please try again shortly.");
        }
    }

    private String buildMessageBody(String otpCode) {
        return "Use the following one-time password to verify your AlphaCore account: " + otpCode
                + "\nThis code will expire shortly. If you did not initiate this request, please ignore this email.";
    }
}
