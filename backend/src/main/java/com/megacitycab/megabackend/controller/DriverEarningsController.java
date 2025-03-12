package com.megacitycab.megabackend.controller;

import com.megacitycab.megabackend.service.DriverEarningsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/drivers")
@RequiredArgsConstructor
public class DriverEarningsController {
    private final DriverEarningsService driverEarningsService;

    // ✅ Only Admins can check driver earnings
    @GetMapping("/earnings/{driverId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')") // ✅ Restrict access to Admins
    public ResponseEntity<Double> getDriverEarnings(@PathVariable String driverId) {
        double earnings = driverEarningsService.getDriverEarnings(driverId);
        return ResponseEntity.ok(earnings);
    }
}
