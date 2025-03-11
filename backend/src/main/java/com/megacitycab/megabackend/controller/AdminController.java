package com.megacitycab.megabackend.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/dashboard")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")  // ✅ Ensure correct role format
    public String getAdminDashboard() {
        return "Welcome to the Admin Dashboard! ";
    }

    @GetMapping("/all-users")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")  // ✅ Ensure correct role format
    public String getAllUsers() {
        return "List of all registered users (Admin Access Only)";
    }
}
