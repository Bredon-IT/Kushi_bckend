package com.kushi.in.app.service;

import com.kushi.in.app.entity.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {
     Customer addService(Customer customer);

     List<Customer> getAllServices();

    void deleteService(Long id);
}
