package com.kushi.in.app.service.impl;

import com.kushi.in.app.dao.CustomerRepository;
import com.kushi.in.app.entity.Customer;
import com.kushi.in.app.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Override
    public Customer addService(Customer customer) {
        return customerRepository.save(customer);
    }
}
