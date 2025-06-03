package com.kushi.in.app.service.impl;

import com.kushi.in.app.dao.AdminRepository;
import com.kushi.in.app.entity.Admin;

import com.kushi.in.app.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public List<Admin> getAllBookings() {
        // Retrieves all booking records from the database
        return adminRepository.findAll();
    }
    // Saves a new booking to the database
    @Override
    public Admin saveBooking(Admin admin) {
        return adminRepository.save(admin);
    }
    // Assigns a worker to an existing booking based on the booking ID
    @Override
    public void assignWorker(Long bookingId, String workerName) {
        // Tries to find the booking by ID. If not found, throws a runtime exception with a message.
        Admin booking=adminRepository.findById(bookingId).orElseThrow(()->new RuntimeException("Booking not found ID: "+bookingId));
        booking.setWorker_assign(workerName); // Sets the worker_assign field of the booking to the given worker name

        adminRepository.save(booking);// Saves the updated booking back to the database
    }

    @Override
    public Map<String, Object> getbookingStatistics(String timePeriod) {
        List<Admin> bookings=adminRepository.findAll();// Get all booking records from the database
                LocalDate today= LocalDate.now();// Get today's Date
//filter bookings from the last 1 week
        if ("one-week".equalsIgnoreCase(timePeriod)){
            bookings = bookings.stream().filter(b-> b.getBooking_date() !=null && b.getBooking_date().isAfter(today.minusWeeks(1)))
                    .collect(Collectors.toList());
    } else if("two-weeks".equalsIgnoreCase(timePeriod)){
            bookings = bookings.stream().filter(b-> b.getBooking_date() !=null && b.getBooking_date().isAfter(today.minusWeeks(2)))
                    .collect(Collectors.toList());//filter bookingns from the last 2 weeks
            
        } else if ("one-month".equalsIgnoreCase(timePeriod)) {
            bookings = bookings.stream().filter(b-> b.getBooking_date() !=null && b.getBooking_date().isAfter(today.minusMonths(1)))
                    .collect(Collectors.toList());//filter bookings from the last 1 month
        }
// Create a map to store total revenue per service
        Map<String ,Double> serviceRevenue= new HashMap<>();
            double totalbooking_amount=0.0;
//loop through each booking
            for (Admin booking: bookings){
                String service = booking.getBooking_service_name();//get service name
                double amount = booking.getTotal_amount();//get booking amount
                totalbooking_amount += amount;//get total amount
                serviceRevenue.put(service, serviceRevenue.getOrDefault(service, 0.0) + amount);//Add or update revenue for this service
            }
            //prepare the response map
            Map<String,Object> response=new HashMap<>();
            response.put("lables",new ArrayList<>(serviceRevenue.keySet()));//List of service names
            response.put("data",new ArrayList<>(serviceRevenue.values()));//total revenue amount per service
            response.put("totalcustomers",bookings.size());//total no.of bookings
            response.put("totalbooking_amount",totalbooking_amount);//total revenue from all bookings

            return response; // Return the statistics as a response


    }

    @Override
    public Map<String, Object> getOverview(String timePeriod) {
        List<Admin> bookings= adminRepository.findAll();
           LocalDate today= LocalDate.now();

           if ("one-week".equalsIgnoreCase(timePeriod)){
               bookings=bookings.stream().filter(b-> b.getBooking_date() !=null && b.getBooking_date().isAfter(today.minusWeeks(1)))
                       .collect(Collectors.toList());
           } else if ("two-weeks".equalsIgnoreCase(timePeriod)) {
               bookings = bookings.stream().filter(b-> b.getBooking_date() !=null&& b.getBooking_date().isAfter(today.minusWeeks(2)))
                       .collect(Collectors.toList());

           } else if ("one-month".equalsIgnoreCase(timePeriod)) {
                bookings = bookings.stream().filter(b-> b.getBooking_date() !=null && b.getBooking_date().isAfter(today.minusMonths(1)))
                        .collect(Collectors.toList());

           }
           double totalbookingamount=bookings.stream().mapToDouble(Admin::getTotal_amount).sum();
           int totalCustomers = bookings.size();

           Map<String,Object> overview = new HashMap<>();
           overview.put("totalbookingamount",totalbookingamount);
           overview.put("totalCustomers",totalCustomers);

           return overview;
    }
}
