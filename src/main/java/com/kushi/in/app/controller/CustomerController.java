// src/main/java/com/kushi/in/app/controller/CustomerController.java
package com.kushi.in.app.controller;

import com.kushi.in.app.dao.ServiceRepository;
import com.kushi.in.app.entity.Customer;
import com.kushi.in.app.entity.Services;
import com.kushi.in.app.model.CustomerDTO;
import com.kushi.in.app.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "http://localhost:5174")
public class CustomerController {

    private final CustomerService customerService;


    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // ===========================
    // Customer Management Endpoints
    // ===========================

    // All customers
    @GetMapping
    public List<CustomerDTO> getAll() {
        return customerService.getAll();
    }

    // Get single customer by bookingId
    @GetMapping("/{bookingId}")
    public ResponseEntity<Customer> getOne(@PathVariable Long bookingId) {
        Customer c = customerService.getById(bookingId);
        return c != null ? ResponseEntity.ok(c) : ResponseEntity.notFound().build();
    }

    // Create new customer
    @PostMapping
    public Customer create(@RequestBody Customer customer) {
        return customerService.create(customer);
    }

    // Update customer
    @PutMapping("/{bookingId}")
    public ResponseEntity<Customer> update(@PathVariable Long bookingId, @RequestBody Customer update) {
        Customer c = customerService.update(bookingId, update);
        return c != null ? ResponseEntity.ok(c) : ResponseEntity.notFound().build();
    }

    // Delete customer
    @DeleteMapping("/{bookingId}")
    public ResponseEntity<?> delete(@PathVariable Long bookingId) {
        return customerService.delete(bookingId) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    // Customers by booking status
    @GetMapping("/by-booking-status")
    public ResponseEntity<List<Customer>> getCustomersByBookingStatus(@RequestParam String status) {
        try {
            return ResponseEntity.ok(customerService.getCustomersByBookingStatus(status));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    // Logged-in customers
    @GetMapping("/logged-in")
    public List<CustomerDTO> getLoggedInCustomers() {
        return customerService.getLoggedInCustomers();
    }

    // Guest customers
    @GetMapping("/guests")
    public List<CustomerDTO> getGuestCustomers() {
        return customerService.getGuestCustomers();
    }

    // Completed bookings
    @GetMapping("/completed")
    public List<CustomerDTO> getCompletedBookings() {
        return customerService.getCompletedBookings();
    }

    // ===========================
    // Service Management Endpoints
    // ===========================

    // Add a new service
    @PostMapping("/add-service")
    public ResponseEntity<?> addService(@RequestBody Services services) {
        try {
            if (services.getService_name() == null || services.getService_name().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Service name is required"));
            }
            Services saved = customerService.addService(services);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
        }
    }

    // Get all services
    @GetMapping("/all-services")
    public ResponseEntity<List<Services>> getAllServices() {
        List<Services> services = customerService.getAllServices();
        return ResponseEntity.ok(services);
    }

    // Delete a service by ID
    @DeleteMapping("/delete-service/{id}")
    public ResponseEntity<?> deleteService(@PathVariable Long id) {
        try {
            customerService.deleteService(id);
            return ResponseEntity.ok(Map.of("message", "Service deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
        }
    }

    // Get single service by ID
    @GetMapping("/service/{id}")
    public ResponseEntity<Services> getServiceById(@PathVariable Long id) {
        Optional<Services> serviceData = customerService.getServiceById(id);
        return serviceData.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Update service by ID
    // ✅ Update service by ID
    @PutMapping("/update-service/{id}")
    public ResponseEntity<?> updateService(
            @PathVariable Long id,
            @RequestBody Services services) {

        try {
            Services updated = customerService.updateService(id, services);
            return ResponseEntity.ok(updated);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to update service", "details", e.getMessage()));
        }
    }


}
