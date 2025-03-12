package com.megacitycab.megabackend.controller;

import com.megacitycab.megabackend.config.JwtUtil;
import com.megacitycab.megabackend.model.Booking;
import com.megacitycab.megabackend.model.User;
import com.megacitycab.megabackend.service.BookingService;
import com.megacitycab.megabackend.repository.UserRepository;
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
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

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
    public Booking cancelBooking(@PathVariable String id, @RequestHeader("Authorization") String token) {
        String email = jwtUtil.extractUsername(token.substring(7)); // Extract user email from JWT
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return bookingService.cancelBooking(id, user.getId(), user.getRole().name());
    }

    // ✅ Admin Completes a Booking (Marks as COMPLETED)
    @PutMapping("/complete/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')") // ✅ Admin only
    public Booking completeBooking(@PathVariable String id) {
        return bookingService.completeBooking(id);
    }
}
