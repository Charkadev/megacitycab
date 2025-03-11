package com.megacitycab.megabackend.service;

import com.megacitycab.megabackend.dto.AuthRequest;
import com.megacitycab.megabackend.dto.AuthResponse;
import com.megacitycab.megabackend.dto.RegisterRequest;
import com.megacitycab.megabackend.model.Role;
import com.megacitycab.megabackend.model.User;
import com.megacitycab.megabackend.repository.UserRepository;
import com.megacitycab.megabackend.config.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthResponse register(RegisterRequest request) {
        // ✅ Ensure role is set correctly (default to ROLE_USER)
        Role role = request.getRole() != null ? request.getRole() : Role.ROLE_USER;

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role) // ✅ Assign correct role
                .build();

        userRepository.save(user);
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name()); // ✅ Pass role in token

        return new AuthResponse(token);
    }

    public AuthResponse login(AuthRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name()); // ✅ Use correct parameters

        return new AuthResponse(token);
    }
}
