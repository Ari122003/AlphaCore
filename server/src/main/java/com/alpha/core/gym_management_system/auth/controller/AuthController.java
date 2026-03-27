package com.alpha.core.gym_management_system.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alpha.core.gym_management_system.auth.dto.AdminRegistrationRequest;
import com.alpha.core.gym_management_system.auth.dto.CoachRegistrationRequest;
import com.alpha.core.gym_management_system.auth.dto.JwtResponse;
import com.alpha.core.gym_management_system.auth.dto.LoginRequest;
import com.alpha.core.gym_management_system.auth.dto.MessageResponse;
import com.alpha.core.gym_management_system.auth.dto.VerifyEmailRequest;
import com.alpha.core.gym_management_system.auth.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final AuthService authService;

    @PostMapping("/admin/register")
    public ResponseEntity<MessageResponse> registerAdmin(@Valid @RequestBody AdminRegistrationRequest request) {
        MessageResponse response = authService.registerAdmin(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/coach/register")
    public ResponseEntity<MessageResponse> registerCoach(@Valid @RequestBody CoachRegistrationRequest request) {
        MessageResponse response = authService.registerCoach(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/verify-email")
    public ResponseEntity<JwtResponse> verifyEmail(@Valid @RequestBody VerifyEmailRequest request) {
        JwtResponse response = authService.verifyEmail(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest request) {
        JwtResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}
