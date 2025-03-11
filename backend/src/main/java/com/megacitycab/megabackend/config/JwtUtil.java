package com.megacitycab.megabackend.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    private static final String SECRET_KEY = "your-super-secure-and-long-secret-key-your-super-secure-and-long-secret-key"; // ✅ Ensure at least 256-bit key
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24; // ✅ 1 Day Expiry

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // ✅ Generate Token with Email & Role
    public String generateToken(String email, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role.startsWith("ROLE_") ? role : "ROLE_" + role); // ✅ Ensure "ROLE_" prefix

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // ✅ Extract Claims
    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // ✅ Extract Email (Username)
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    // ✅ Extract Role
    public String extractRole(String token) {
        return extractClaims(token).get("role", String.class);
    }

    // ✅ Validate Token
    public boolean validateToken(String token, String email) {
        return email.equals(extractUsername(token)) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }
}
