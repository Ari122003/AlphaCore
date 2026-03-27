package com.alpha.core.gym_management_system.auth.dto;

import java.time.Instant;

import com.alpha.core.gym_management_system.security.model.Role;

import lombok.Builder;

@Builder
public record JwtResponse(String accessToken, String tokenType, Instant expiresAt, Role role) {
}
