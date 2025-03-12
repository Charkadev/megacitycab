package com.megacitycab.megabackend.controller;

import com.megacitycab.megabackend.model.Billing;
import com.megacitycab.megabackend.service.BillingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/billing")
@RequiredArgsConstructor
public class BillingController {

    private final BillingService billingService;

    // Generate a bill when booking is completed
    @PostMapping("/generate/{bookingId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')") // Admin only
    public Billing generateBill(@PathVariable String bookingId) {
        return billingService.generateBill(bookingId);
    }

    // Get all bills (Admin only)
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')") // Admin only
    public List<Billing> getAllBills() {
        return billingService.getAllBills();
    }

    // Get bills for a specific user
    @GetMapping("/user/{userId}")
    public List<Billing> getUserBills(@PathVariable String userId) {
        return billingService.getUserBills(userId);
    }
}
