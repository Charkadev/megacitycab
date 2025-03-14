package com.megacitycab.megabackend.controller;

import com.megacitycab.megabackend.model.Driver;
import com.megacitycab.megabackend.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/admin/drivers")
@RequiredArgsConstructor
public class DriverController {
    private final DriverService driverService;

    //  Get available driver for a specific car
    @GetMapping("/available/{carId}")
    public Optional<Driver> getAvailableDriverForCar(@PathVariable String carId) {
        return driverService.getAvailableDriverForCar(carId);
    }

    //  Add a new driver (vehicle number removed, car selection added)
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> addDriver(@RequestBody Driver driver) {
        if (driver.getName() == null || driver.getPhone() == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Driver name and phone are required!"));
        }

        Driver savedDriver = driverService.addDriver(driver);
        return ResponseEntity.ok(Map.of("message", "Driver added successfully!", "driver", savedDriver));
    }

    //  Assign a car to a driver
    @PutMapping("/{driverId}/assign-car/{carId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> assignCarToDriver(@PathVariable String driverId, @PathVariable String carId) {
        try {
            Driver updatedDriver = driverService.assignCarToDriver(driverId, carId);
            return ResponseEntity.ok(Map.of("message", "Car assigned successfully!", "driver", updatedDriver));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    //  Fetch all drivers
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<Driver> getAllDrivers() {
        return driverService.getAllDrivers();
    }

    //  Fetch only available drivers
    @GetMapping("/available")
    public List<Driver> getAvailableDrivers() {
        return driverService.getAvailableDrivers();
    }
}
