package com.megacitycab.megabackend.service;

import com.megacitycab.megabackend.model.Booking;
import com.megacitycab.megabackend.model.BookingStatus;
import com.megacitycab.megabackend.model.DriverEarnings;
import com.megacitycab.megabackend.repository.BookingRepository;
import com.megacitycab.megabackend.repository.DriverEarningsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final BillingService billingService;
    private final DriverEarningsRepository driverEarningsRepository;

    // ✅ Create a Booking
    public Booking createBooking(Booking booking) {
        booking.setStatus(BookingStatus.PENDING);
        booking.setTimestamp(LocalDateTime.now());
        return bookingRepository.save(booking);
    }

    // ✅ Get Booking by ID
    public Optional<Booking> getBookingById(String id) {
        return bookingRepository.findById(id);
    }

    // ✅ Get Bookings by User ID
    public List<Booking> getUserBookings(String userId) {
        return bookingRepository.findByUserId(userId);
    }

    // ✅ Get All Bookings (For Admin)
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    // ✅ Cancel a Booking (User/Admin)
    public Booking cancelBooking(String bookingId, String userId, String userRole) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        // ✅ Allow only the user who made the booking OR an Admin to cancel
        if (!booking.getUserId().equals(userId) && !userRole.equals("ROLE_ADMIN")) {
            throw new AccessDeniedException("Unauthorized to cancel this booking.");
        }

        booking.setStatus(BookingStatus.CANCELLED);
        return bookingRepository.save(booking);
    }

    // ✅ Complete a Booking and Generate a Bill
    public Booking completeBooking(String id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (booking.getStatus() == BookingStatus.COMPLETED) {
            throw new RuntimeException("Booking is already completed.");
        }

        // ✅ Update booking status to COMPLETED
        booking.setStatus(BookingStatus.COMPLETED);
        bookingRepository.save(booking);

        // ✅ Automatically generate a bill
        billingService.generateBill(id);

        // ✅ Update driver earnings
        updateDriverEarnings(booking.getDriverId(), booking.getFare());

        return booking;
    }

    private void updateDriverEarnings(String driverId, double fare) {
        DriverEarnings earnings = driverEarningsRepository.findByDriverId(driverId)
                .orElse(DriverEarnings.builder().driverId(driverId).totalEarnings(0).build());

        earnings.setTotalEarnings(earnings.getTotalEarnings() + fare);
        driverEarningsRepository.save(earnings);
    }
}
