package com.kushi.in.app.controller;

import com.kushi.in.app.dao.CustomerRepository;
import com.kushi.in.app.entity.Customer;
import com.kushi.in.app.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/add-service")
       public ResponseEntity<?> addService(@RequestBody Customer customer) {
        try {
            if (customer.getService_name() == null || customer.getService_name().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "service name is required"));
            }

                Customer saved = customerService.addService(customer);
                return ResponseEntity.status(HttpStatus.CREATED).body(saved);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("Error", e.getMessage()));
            }




    }

}








