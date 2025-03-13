package com.megacitycab.megabackend.controller;

import com.megacitycab.megabackend.dto.AuthRequest;
import com.megacitycab.megabackend.dto.AuthResponse;
import com.megacitycab.megabackend.dto.RegisterRequest;
import com.megacitycab.megabackend.model.User;
import com.megacitycab.megabackend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        return authService.login(request);
    }

    // ✅ Added missing /user-info endpoint
    @GetMapping("/user-info")
    public User getUserInfo(@AuthenticationPrincipal org.springframework.security.core.userdetails.User principal) {
        return authService.getUserInfo(principal.getUsername());
    }
}
