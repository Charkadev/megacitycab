package com.megacitycab.megabackend.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
@Slf4j
public class JwtUtil {
    private static final String SECRET_KEY = "your-very-secure-secret-key-should-be-long-enough";
    private static final long EXPIRATION_TIME = 1000L * 60 * 60 * 24 * 30; // 30 days

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // ✅ Generate Token (Keeps role format consistent)
    public String generateToken(String email, String role) {
        return Jwts.builder()
                .setClaims(Map.of("role", role)) //  Store role as-is
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // ✅ Extract Claims Safely
    private Claims extractClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            log.error("🚨 Invalid Token: {}", e.getMessage());
            throw new RuntimeException("Invalid or expired token");
        }
    }

    // ✅ Extract Email
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    // ✅ Extract Role
    public String extractRole(String token) {
        return extractClaims(token).get("role", String.class);
    }

    // ✅ Validate Token (Ensures token is not expired)
    public boolean validateToken(String token, String email) {
        return email.equals(extractUsername(token)) && !extractClaims(token).getExpiration().before(new Date());
    }
}
