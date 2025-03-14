package com.megacitycab.megabackend.service;

import com.megacitycab.megabackend.model.DriverEarnings;
import com.megacitycab.megabackend.repository.DriverEarningsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverEarningsService {
    private final DriverEarningsRepository driverEarningsRepository;

    //  Fetch earnings for a specific driver
    public List<DriverEarnings> getDriverEarnings(String driverId) {
        return driverEarningsRepository.findByDriverId(driverId);
    }
}
