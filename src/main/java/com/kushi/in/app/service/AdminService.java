package com.kushi.in.app.service;

import com.kushi.in.app.entity.Admin;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service

public interface AdminService {
    // Declares a method to retrieve all booking records
    List<Admin> getAllBookings(); // Declares a method to retrieve all bookings from the database

    Admin saveBooking(Admin admin); // Declares a method to save a new booking to the database

    void assignWorker(Long bookingId,String workerName);// Declares a method to assign a worker to a booking using its ID

    Map<String, Object> getbookingStatistics(String timePeriod);// This method will return booking statistics based on the selected time period


    Map<String, Object> getOverview(String timePeriod);
    // String timePeriod - input parameter that decides how to filter the data (e.g., "one-week", "one-month")
}
