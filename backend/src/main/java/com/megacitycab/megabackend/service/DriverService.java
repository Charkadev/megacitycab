package com.megacitycab.megabackend.service;

import com.megacitycab.megabackend.model.Driver;
import com.megacitycab.megabackend.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DriverService {
    private final DriverRepository driverRepository;

    public Driver addDriver(Driver driver) {
        driver.setAvailable(true); // ✅ New drivers are available by default
        return driverRepository.save(driver);
    }

    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }

    public Optional<Driver> getDriverById(String id) {
        return driverRepository.findById(id);
    }

    public List<Driver> getAvailableDrivers() {
        return driverRepository.findByAvailable(true);
    }

    public Driver updateDriver(String id, Driver updatedDriver) {
        return driverRepository.findById(id).map(driver -> {
            driver.setName(updatedDriver.getName());
            driver.setLicenseNumber(updatedDriver.getLicenseNumber());
            driver.setPhone(updatedDriver.getPhone());
            driver.setAvailable(updatedDriver.isAvailable());
            return driverRepository.save(driver);
        }).orElseThrow(() -> new RuntimeException("Driver not found"));
    }

    public void deleteDriver(String id) {
        driverRepository.deleteById(id);
    }
}
