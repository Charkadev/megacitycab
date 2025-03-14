package com.megacitycab.megabackend.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "billings")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Billing {
    @Id
    private String id;
    private String bookingId; // Reference to the completed booking
    private String userId;    // User who booked the ride
    private double fare;      // Original fare
    private double tax;       // Tax amount
    private double totalAmount; // Final bill amount
    private LocalDateTime timestamp; // Billing timestamp
}
