package com.kushi.in.app.service.impl;


import com.kushi.in.app.dao.CustomerRepository;
import com.kushi.in.app.model.CustomerDTO;
import com.kushi.in.app.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private CustomerRepository customerRepository;
    /**
     * @return
     */
    @Override
    public ResponseEntity<CustomerDTO> getTopCustomers() {
        return (ResponseEntity<CustomerDTO>) customerRepository.findAll();
    }
}
