package com.kushi.in.app.controller;

import com.kushi.in.app.dao.BookingRepository;
import com.kushi.in.app.dao.CustomerRepository;
import com.kushi.in.app.entity.Customer;
import com.kushi.in.app.model.*;
import com.kushi.in.app.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "https://main.dhtawzq4yzgjo.amplifyapp.com") // frontend React dev server
public class BookingController {

    private final BookingService bookingService;
    private final BookingRepository bookingRepository;

    public BookingController(BookingService bookingService, BookingRepository bookingRepository) {
        this.bookingService = bookingService;
        this.bookingRepository = bookingRepository;

    }

    // ✅ Create Booking
    @PostMapping("/newbookings")
    public ResponseEntity<?> createBooking(@RequestBody BookingRequest request) {
        try {
            return ResponseEntity.ok(bookingService.createBooking(request));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    // ✅ Fetch All Bookings
    @GetMapping("/allbookings")
    public ResponseEntity<List<BookingDTO>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    // ✅ Update Booking Status
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long id,
                                          @RequestBody Map<String, String> body) {
        try {
            String status = body.get("status");
            String canceledBy = body.get("canceledBy"); // NEW
            Customer updated = bookingService.updateBookingStatus(id, status, canceledBy);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to update status: " + e.getMessage());
        }
    }

    // ✅ Send Booking Notification
    @PostMapping("/notify")
    public ResponseEntity<?> sendBookingNotification(@RequestBody BookingNotificationRequest request) {
        try {
            bookingService.sendBookingNotification(
                    request.getEmail(),
                    request.getPhoneNumber(),
                    request.getStatus()
            );
            return ResponseEntity.ok("Notification sent successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to send notification: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable Long id) {
        try {
            bookingRepository.deleteById(id);
            return ResponseEntity.ok("Booking deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to delete booking: " + e.getMessage());
        }
    }


    // ✅ Update Booking Discount
    // ✅ Update Booking Discount
    @PutMapping("/{id}/discount")
    public ResponseEntity<?> updateDiscount(@PathVariable Long id, @RequestBody Map<String, Double> body) {
        try {
            Double discount = body.get("discount");
            bookingService.updateBookingDiscount(id, discount);
            return ResponseEntity.ok("Discount updated successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to update discount: " + e.getMessage());
        }
    }



    // Fetch all bookings for a specific user





}
