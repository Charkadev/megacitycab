package com.megacitycab.megabackend.controller;

import com.megacitycab.megabackend.model.Driver;
import com.megacitycab.megabackend.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/drivers")
@RequiredArgsConstructor
public class DriverController {
    private final DriverService driverService;

    // ✅ Admin can add a driver (Now returns confirmation message)
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String addDriver(@RequestBody Driver driver) {
        driverService.addDriver(driver);
        return "Driver added successfully.";
    }

    // ✅ Admin can update driver details
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String updateDriver(@PathVariable String id, @RequestBody Driver driver) {
        driverService.updateDriver(id, driver);
        return "Driver updated successfully.";
    }

    // ✅ Admin can delete a driver
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String deleteDriver(@PathVariable String id) {
        driverService.deleteDriver(id);
        return "Driver deleted successfully.";
    }

    // ✅ Admin can view all drivers
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<Driver> getAllDrivers() {
        return driverService.getAllDrivers();
    }

    // ✅ Users can view available drivers
    @GetMapping("/available")
    public List<Driver> getAvailableDrivers() {
        return driverService.getAvailableDrivers();
    }
}
