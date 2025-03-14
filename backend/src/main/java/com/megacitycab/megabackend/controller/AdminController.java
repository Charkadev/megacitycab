package com.megacitycab.megabackend.controller;

import com.megacitycab.megabackend.model.User;
import com.megacitycab.megabackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserRepository userRepository;

    @GetMapping("/dashboard")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")  //  Ensure correct role format
    public String getAdminDashboard() {
        return "Welcome to the Admin Dashboard!";
    }

    @GetMapping("/all-users")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")  //  Ensure correct role format
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
