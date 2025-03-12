package com.megacitycab.megabackend.service;

import com.megacitycab.megabackend.model.Booking;
import com.megacitycab.megabackend.model.BookingStatus;
import com.megacitycab.megabackend.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverEarningsService {

    private final BookingRepository bookingRepository;

    // ✅ Dynamically calculate total earnings for a driver from completed bookings
    public double getDriverEarnings(String driverId) {
        List<Booking> completedBookings = bookingRepository.findByDriverId(driverId);

        // ✅ Ensure we sum only COMPLETED bookings
        return completedBookings.stream()
                .filter(booking -> booking.getStatus() == BookingStatus.COMPLETED) // Only completed rides
                .mapToDouble(Booking::getFare) // Get the fare from each completed ride
                .sum(); // Calculate total earnings
    }
}
