package com.kushi.in.app.service;

import com.kushi.in.app.model.CustomerDTO;
import org.springframework.http.ResponseEntity;

public interface AdminService {
    ResponseEntity<CustomerDTO> getTopCustomers();
}
