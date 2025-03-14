package com.megacitycab.megabackend.controller;

import com.megacitycab.megabackend.model.Car;
import com.megacitycab.megabackend.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/cars")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllCars() {
        List<Car> cars = carService.getAllCars();

        return cars.isEmpty() ? ResponseEntity.ok(List.of()) : ResponseEntity.ok(cars);
    }

    @GetMapping("/available")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAvailableCars() {
        List<Car> availableCars = carService.getAvailableCars();

        return availableCars.isEmpty() ? ResponseEntity.ok(List.of()) : ResponseEntity.ok(availableCars);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addCar(@RequestBody Car car) {
        try {
            //  Fix: Ensure status is stored as a String
            String status = car.getStatus().trim().toLowerCase(); // Convert to lowercase for consistency

            //  Validate allowed status values
            if (!status.equals("available") && !status.equals("maintenance") && !status.equals("assigned")) {
                return ResponseEntity.badRequest().body(" Invalid car status! Allowed values: Available, Maintenance, Assigned");
            }

            //  Capitalize first letter
            car.setStatus(status.substring(0, 1).toUpperCase() + status.substring(1));

            Car savedCar = carService.addCar(car);
            return ResponseEntity.ok(savedCar);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(" Error saving car: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteCar(@PathVariable String id) {
        carService.deleteCar(id);
        return ResponseEntity.ok("✅ Car deleted successfully");
    }
}
