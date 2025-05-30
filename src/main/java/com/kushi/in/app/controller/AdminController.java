package com.kushi.in.app.controller;

import com.kushi.in.app.entity.Admin;
import com.kushi.in.app.service.AdminService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "*") // Update with actual frontend URL for production
public class AdminController {

    private  AdminService adminService;

    // Constructor injection is preferred for better testability and immutability
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    public List<Admin> getAllBookings() {
        // Returns all booking records from the database
        return adminService.getAllBookings();
    }
    @PostMapping
    public Admin createBooking(@RequestBody Admin admin){
        // Saves a new booking record to the database
         return adminService.saveBooking(admin);
    }

}
