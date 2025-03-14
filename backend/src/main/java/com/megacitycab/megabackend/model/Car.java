package com.megacitycab.megabackend.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cars")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Car {
    @Id
    private String id;
    private String model;
    private String plateNumber;
    private String type; // Sedan, SUV, etc.

    private String status; //  Store status as a simple String
}
