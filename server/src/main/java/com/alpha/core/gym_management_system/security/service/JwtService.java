package com.alpha.core.gym_management_system.security.service;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alpha.core.gym_management_system.security.entity.UserAccount;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;

@Service
public class JwtService {

    @Value("${security.jwt.secret}")
    private String jwtSecret;

    @Value("${security.jwt.expiration-minutes:60}")
    private long expirationMinutes;

    public TokenResult generateToken(UserAccount userAccount) {
        Instant issuedAt = Instant.now();
        Instant expiresAt = issuedAt.plus(expirationMinutes, ChronoUnit.MINUTES);
        String token = Jwts.builder()
                .setClaims(Map.of("role", userAccount.getRole().name()))
                .setSubject(userAccount.getEmail())
                .setIssuedAt(Date.from(issuedAt))
                .setExpiration(Date.from(expiresAt))
                .signWith(signingKey(), SignatureAlgorithm.HS256)
                .compact();
        return new TokenResult(token, expiresAt);
    }

    public String extractUsername(String jwt) {
        return extractAllClaims(jwt).getSubject();
    }

    public boolean isTokenValid(String jwt, UserAccount userAccount) {
        Claims claims = extractAllClaims(jwt);
        return userAccount.getEmail().equalsIgnoreCase(claims.getSubject())
                && !claims.getExpiration().before(new Date());
    }

    private Claims extractAllClaims(String jwt) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }

    private Key signingKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Getter
    public static class TokenResult {
        private final String token;
        private final Instant expiresAt;

        public TokenResult(String token, Instant expiresAt) {
            this.token = token;
            this.expiresAt = expiresAt;
        }
    }
}
