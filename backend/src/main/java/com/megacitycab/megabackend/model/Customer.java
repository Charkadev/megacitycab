package com.megacitycab.megabackend.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "customers") // Specifies the MongoDB collection
public class Customer {
    @Id
    private String id;
    private String name;
    private String address;
    private String phoneNumber;
}
