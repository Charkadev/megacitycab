package com.megacitycab.megabackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor // Add default constructor
public class AuthResponse {
    private String token;
}
