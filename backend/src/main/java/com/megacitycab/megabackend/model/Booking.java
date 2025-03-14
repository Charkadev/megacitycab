package com.megacitycab.megabackend.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "bookings")
public class Booking {
    @Id
    private String id;
    private String userId;
    private String pickupLocation;
    private String dropoffLocation;
    private double fare;
    private BookingStatus status;
    private LocalDateTime timestamp;

    private String selectedCarId; //  Updated for car selection
    private String driverId;

    @DBRef
    private Driver driverDetails;
}
