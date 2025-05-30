package com.kushi.in.app.service.impl;

import com.kushi.in.app.dao.AdminRepository;
import com.kushi.in.app.entity.Admin;

import com.kushi.in.app.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public List<Admin> getAllBookings() {
        // Retrieves all booking records from the database
        return adminRepository.findAll();
    }

    @Override
    public Admin saveBooking(Admin admin) {
        return adminRepository.save(admin);
    }

    @Override
    public void assignWorker(Long bookingId, String workerName) {
        Admin booking=adminRepository.findById(bookingId).orElseThrow(()->new RuntimeException("Booking not found ID: "+bookingId));
        booking.setWorker_assign(workerName);
        adminRepository.save(booking);
    }
}
