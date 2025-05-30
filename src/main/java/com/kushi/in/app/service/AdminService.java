package com.kushi.in.app.service;

import com.kushi.in.app.entity.Admin;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public interface AdminService {
    // Declares a method to retrieve all booking records
    List<Admin> getAllBookings();

    Admin saveBooking(Admin admin);
}
