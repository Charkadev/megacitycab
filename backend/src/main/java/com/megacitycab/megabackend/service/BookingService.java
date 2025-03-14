package com.megacitycab.megabackend.service;

import com.megacitycab.megabackend.model.*;
import com.megacitycab.megabackend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final DriverRepository driverRepository;
    private final CarRepository carRepository;

    //  Users can create their own bookings
    public Booking createBooking(Booking booking, String email) {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        booking.setUserId(user.getId());
        booking.setStatus(BookingStatus.PENDING);
        booking.setTimestamp(LocalDateTime.now());

        //  Validate Car Availability (Compare as String)
        Car selectedCar = carRepository.findById(booking.getSelectedCarId())
                .orElseThrow(() -> new RuntimeException("Selected car not found"));

        if (!selectedCar.getStatus().equalsIgnoreCase("Available")) {
            throw new RuntimeException("Selected car is not available for booking.");
        }

        //  Find an Available Driver for the Car
        Optional<Driver> availableDriver = driverRepository.findByAssignedCarIdAndAvailabilityTrue(booking.getSelectedCarId());

        if (availableDriver.isEmpty()) {
            throw new RuntimeException("No available driver for the selected car.");
        }

        //  Assign Driver & Update Car Status
        Driver assignedDriver = availableDriver.get();
        assignedDriver.assignCar(selectedCar.getId());
        driverRepository.save(assignedDriver);

        selectedCar.setStatus("Assigned"); //  Set status as String
        carRepository.save(selectedCar);

        booking.setDriverId(assignedDriver.getId());
        booking.setDriverDetails(assignedDriver);

        return bookingRepository.save(booking);
    }

    //  Admin can view all bookings
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    //  Users can view their own bookings
    public List<Booking> getUserBookings(String email) {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return bookingRepository.findByUserId(user.getId());
    }

    //  Admin can update booking status
    public Booking updateBooking(String bookingId, Booking updatedBooking) {
        var booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        booking.setStatus(updatedBooking.getStatus());
        booking.setDropoffLocation(updatedBooking.getDropoffLocation());
        booking.setPickupLocation(updatedBooking.getPickupLocation());

        return bookingRepository.save(booking);
    }

    //  Admin can cancel any booking
    public Booking cancelBooking(String bookingId) {
        var booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        booking.setStatus(BookingStatus.CANCELLED);

        //  Reset Car & Driver Availability
        if (booking.getDriverId() != null) {
            driverRepository.findById(booking.getDriverId()).ifPresent(driver -> {
                driver.unassignCar();
                driverRepository.save(driver);
            });

            carRepository.findById(booking.getSelectedCarId()).ifPresent(car -> {
                car.setStatus("Available"); //  Set status as String
                carRepository.save(car);
            });
        }

        return bookingRepository.save(booking);
    }
}
