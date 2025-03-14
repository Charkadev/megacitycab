package com.megacitycab.megabackend.controller;

import com.megacitycab.megabackend.model.Billing;
import com.megacitycab.megabackend.service.BillingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/billing")
@RequiredArgsConstructor
public class BillingController {
    private final BillingService billingService;

    //  Fetch user's billing history correctly
    @GetMapping("/user")
    public List<Billing> getUserBills(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            throw new RuntimeException("Unauthorized - No user details found.");
        }

        String userEmail = userDetails.getUsername();
        return billingService.getUserBills(userEmail);
    }
}
