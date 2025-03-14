package com.megacitycab.megabackend.controller;

import com.megacitycab.megabackend.model.Booking;
import com.megacitycab.megabackend.service.AdminReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/reports")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')") //  Ensures only ADMIN can access
public class AdminReportController {
    private final AdminReportService adminReportService;

    //  Get Total Earnings Report
    @GetMapping("/total-earnings")
    public ResponseEntity<Double> getTotalEarnings() {
        double earnings = adminReportService.getTotalEarnings();
        return ResponseEntity.ok(earnings);
    }

    //  Get Booking Summary Report
    @GetMapping("/booking-summary")
    public ResponseEntity<Map<String, Long>> getBookingSummary() {
        Map<String, Long> summary = adminReportService.getBookingSummary();
        return ResponseEntity.ok(summary);
    }

    //  Get All Bookings (ADMIN ONLY)
    @GetMapping("/all-bookings")
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> bookings = adminReportService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }

    //  Get User-Specific Booking Report
    @GetMapping("/user-bookings/{userId}")
    public ResponseEntity<List<Booking>> getUserBookings(@PathVariable String userId) {
        List<Booking> bookings = adminReportService.getUserBookings(userId);
        return ResponseEntity.ok(bookings);
    }
}
