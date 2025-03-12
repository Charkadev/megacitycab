package com.megacitycab.megabackend.controller;

import com.megacitycab.megabackend.model.Booking;
import com.megacitycab.megabackend.service.AdminReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/reports")
@RequiredArgsConstructor
public class AdminReportController {
    private final AdminReportService adminReportService;

    // ✅ Get Total Earnings Report (Admin Only)
    @GetMapping("/total-earnings")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public double getTotalEarnings() {
        return adminReportService.getTotalEarnings();
    }

    // ✅ Get Booking Summary Report (Admin Only)
    @GetMapping("/booking-summary")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Map<String, Long> getBookingSummary() {
        return adminReportService.getBookingSummary();
    }

    // ✅ Get User Booking Report (Admin Only)
    @GetMapping("/user-bookings/{userId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<Booking> getUserBookings(@PathVariable String userId) {
        return adminReportService.getUserBookings(userId);
    }
}
