package com.kushi.in.app.controller;

import com.kushi.in.app.model.CustomerDTO;
import com.kushi.in.app.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * To handle Admin related actions
 */
@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;

    /**
     * To bring top customers
     * @return
     */
    @GetMapping("v1/topCustomers")
    public ResponseEntity<CustomerDTO> getTopCustomers(){
        return adminService.getTopCustomers();
    }
}
