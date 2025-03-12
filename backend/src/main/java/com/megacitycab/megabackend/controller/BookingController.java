package com.megacitycab.megabackend.controller;

import com.megacitycab.megabackend.model.Booking;
import com.megacitycab.megabackend.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    // ✅ User Books a Ride
    @PostMapping("/create")
    public Booking createBooking(@RequestBody Booking booking) {
        return bookingService.createBooking(booking);
    }

    // ✅ Get Booking by ID
    @GetMapping("/{id}")
    public Optional<Booking> getBookingById(@PathVariable String id) {
        return bookingService.getBookingById(id);
    }

    // ✅ User Views Their Bookings
    @GetMapping("/user/{userId}")
    public List<Booking> getUserBookings(@PathVariable String userId) {
        return bookingService.getUserBookings(userId);
    }

    // ✅ Admin Views All Bookings
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    // ✅ User/Admin Cancels a Booking
    @PutMapping("/cancel/{id}")
    public Booking cancelBooking(@PathVariable String id) {
        return bookingService.cancelBooking(id);
    }
}
