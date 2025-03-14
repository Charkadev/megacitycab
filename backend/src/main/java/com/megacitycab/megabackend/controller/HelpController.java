package com.megacitycab.megabackend.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/help")
public class HelpController {

    @GetMapping
    public String getGeneralHelp() {
        return """
                🚖 **MegaCityCab - Help Guide**  
                Welcome to MegaCityCab! Select an option below to get started:  
                
                👉 **For Customers:** `/help/customer`  
                👉 **For Admins:** `/help/admin`  
                👉 **Contact Support:** `support@megacitycab.com`  
                
                 **About MegaCityCab**  
                MegaCityCab is an online cab booking system that allows users to book rides easily.  
                Admins can manage bookings, drivers, and track reports.
                """;
    }

    @GetMapping("/customer")
    public String getCustomerHelp() {
        return """
                🚖 **MegaCityCab - Customer Guide**  
                Welcome to MegaCityCab! Here's how to use the system:
                
                 **1. Create an Account**
                -  Go to **Sign Up** and register your account.
                -  You will need your name, email, and phone number.
                -  After signing up, log in with your email & password.

                 **2. Book a Ride**
                -  Click on **Book a Ride**.
                -  Enter pickup & drop-off locations.
                -  Choose a driver (optional, otherwise one will be assigned).
                -  Confirm and check your booking details.

                 **3. View or Cancel a Booking**
                -  Go to **My Bookings** to view past & upcoming rides.
                -  If you need to cancel, click **Cancel Booking**.

                 **4. View Payment & Billing**
                -  After completing a ride, a bill will be generated.
                -  Go to **My Bills** to view fare breakdown.
                -  Payments are made directly to the driver (cash/online).

                 **Need help? Contact support@megacitycab.com**
                """;
    }

    @GetMapping("/admin")
    public String getAdminHelp() {
        return """
                🏢 **MegaCityCab - Admin Guide**  
                Welcome, Admin! Here’s how to manage the system:

                 **1. Manage Customers & Bookings**
                -  View all bookings: **Go to 'Admin Dashboard' > 'Bookings'**
                -  Cancel a booking if necessary.
                -  Mark a booking as **completed** when the ride is finished.

                 **2. Manage Drivers**
                -  Add a driver in **Admin Dashboard > Drivers**.
                -  Update or remove driver information as needed.

                 **3. View Reports**
                -  Check **Total Earnings Report** to see revenue.
                -  View **Booking Summary Report** for trip statistics.
                -  Generate reports for **user booking history**.

                 **For additional support, contact: support@megacitycab.com**
                """;
    }
}
