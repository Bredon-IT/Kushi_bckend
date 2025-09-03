package com.kushi.in.app.service.impl;

import com.kushi.in.app.dao.AdminRepository;
import com.kushi.in.app.entity.Admin;


import com.kushi.in.app.service.AdminService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private EntityManager entityManager;


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
        List<Admin> bookings = adminRepository.findAll();
        LocalDate today = LocalDate.now();

        if ("one-week".equalsIgnoreCase(timePeriod)) {
            bookings = bookings.stream()
                    .filter(b -> b.getBooking_date() != null && b.getBooking_date().isAfter(today.minusWeeks(1)))
                    .collect(Collectors.toList());
        } else if ("two-weeks".equalsIgnoreCase(timePeriod)) {
            bookings = bookings.stream()
                    .filter(b -> b.getBooking_date() != null && b.getBooking_date().isAfter(today.minusWeeks(2)))
                    .collect(Collectors.toList());
        } else if ("one-month".equalsIgnoreCase(timePeriod)) {
            bookings = bookings.stream()
                    .filter(b -> b.getBooking_date() != null && b.getBooking_date().isAfter(today.minusMonths(1)))
                    .collect(Collectors.toList());
        }

        Map<String, Double> serviceRevenue = new HashMap<>();
        double totalAmount = 0.0;

        for (Admin booking : bookings) {
            String service = booking.getBooking_service_name();
            double amount = booking.getTotal_amount();
            totalAmount += amount;
            serviceRevenue.put(service, serviceRevenue.getOrDefault(service, 0.0) + amount);
        }

        // ✅ Booking Trends by Date
        Map<LocalDate, Long> bookingTrends = bookings.stream()
                .filter(b -> b.getBooking_date() != null)
                .collect(Collectors.groupingBy(
                        Admin::getBooking_date,
                        TreeMap::new, // Sorted by date
                        Collectors.counting()
                ));

        Map<String, Object> response = new HashMap<>();
        response.put("lables", new ArrayList<>(serviceRevenue.keySet()));
        response.put("data", new ArrayList<>(serviceRevenue.values()));
        response.put("totalcustomers", bookings.size());
        response.put("totalbooking_amount", totalAmount);
        response.put("bookingTrends", bookingTrends); // ✅ Add trend data for frontend chart

        return response;
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
           double totalAmount=bookings.stream().mapToDouble(Admin::getTotal_amount).sum();
           int totalCustomers = bookings.size();
           int totalBookings= bookings.size();

           Map<String,Object> overview = new HashMap<>();
        overview.put("totalAmount", totalAmount);
        overview.put("totalCustomers",totalCustomers);
        overview.put("totalBookings",totalBookings);

           return overview;
    }


    @Override
    public List<Admin> getRecentBookingsByDate() {
        return adminRepository.findAll().stream()
                .filter(admin -> admin.getBooking_date() != null)
                .sorted(Comparator.comparing(Admin::getBooking_date).reversed())
                .limit(5) // Optional: Limit to recent 10 bookings
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getVisitStatuses() {
        return adminRepository.findAll().stream()
                .map(admin -> {

                if ("Completed".equalsIgnoreCase(admin.getBooking_status())){
                    return  "visit completed";
                }else {
                   return  "visit not completed";
                }


                }).collect(Collectors.toList());
    }

    @Override
    public List<String> updateVisitStatuses() {
        return adminRepository.findAll().stream()
                .map(admin -> {
                    String status;
                    if ("Completed".equalsIgnoreCase(admin.getBooking_status())){
                        status=  "visit completed";
                    }else {
                      status= "visit not completed";
                    }

                    admin.setVisit_list(status);
                    adminRepository.save(admin);
                    return  status;

                }).collect(Collectors.toList());
    }


    @Override
    public List<Map<String, Object>> getRevenueByService() {
        return adminRepository.getRevenueByService();
    }


    @Override
    public long getTodayBookings() {
        return adminRepository.countTodayBookings();
    }

    @Override
    public long getPendingApprovals() {
        return adminRepository.countPendingApprovals();
    }
}
