package com.megacitycab.megabackend.controller;

import com.megacitycab.megabackend.model.DriverEarnings;
import com.megacitycab.megabackend.service.DriverEarningsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/drivers/earnings")
@RequiredArgsConstructor
public class DriverEarningsController {
    private final DriverEarningsService driverEarningsService;

    //  Get driver earnings by driver ID
    @GetMapping("/{driverId}")
    public List<DriverEarnings> getDriverEarnings(@PathVariable String driverId) {
        return driverEarningsService.getDriverEarnings(driverId);
    }
}
