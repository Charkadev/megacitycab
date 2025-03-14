package com.megacitycab.megabackend.controller;

import com.megacitycab.megabackend.model.Booking;
import com.megacitycab.megabackend.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    //  Users can create their own bookings
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public Booking createBooking(@RequestBody Booking booking, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            throw new RuntimeException("Unauthorized - No user details found.");
        }
        return bookingService.createBooking(booking, userDetails.getUsername());
    }

    //  Admin can view all bookings
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    //  Users can view their own bookings
    @GetMapping("/user")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public List<Booking> getUserBookings(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            throw new RuntimeException("Unauthorized - No user details found.");
        }
        return bookingService.getUserBookings(userDetails.getUsername());
    }

    //  Admin can update bookings
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Booking updateBooking(@PathVariable String id, @RequestBody Booking booking) {
        return bookingService.updateBooking(id, booking);
    }

    //  Admin can cancel bookings
    @DeleteMapping("/cancel/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String cancelBooking(@PathVariable String id) {
        bookingService.cancelBooking(id);
        return "Booking cancelled successfully.";
    }
}
