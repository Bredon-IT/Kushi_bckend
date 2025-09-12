package com.kushi.in.app.service.impl;

import com.kushi.in.app.dao.AdminRepository;


import com.kushi.in.app.dao.CustomerRepository;
import com.kushi.in.app.entity.Customer;
import com.kushi.in.app.entity.Services;
import com.kushi.in.app.model.CustomerDTO;
import com.kushi.in.app.model.InvoiceDTO;
import com.kushi.in.app.model.ServiceDTO;
import com.kushi.in.app.service.AdminService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;
   @Autowired
   private final CustomerRepository customerRepository;

    public AdminServiceImpl(AdminRepository adminRepository, CustomerRepository customerRepository) {
        this.adminRepository = adminRepository;
        this.customerRepository = customerRepository;
    }



    @Override
    public List<Customer> getAllBookings() {
        // Retrieves all booking records from the database
        return adminRepository.findAll();
    }


    // Saves a new booking to the database
    @Override
    public Customer saveBooking(Customer customer) {
        return adminRepository.save(customer);
    }
    // Assigns a worker to an existing booking based on the booking ID
    @Override
    public void assignWorker(Long bookingId, String workerName) {
        // Tries to find the booking by ID. If not found, throws a runtime exception with a message.
        Customer booking=adminRepository.findById(bookingId).orElseThrow(()->new RuntimeException("Booking not found ID: "+bookingId));
        booking.setWorker_assign(workerName); // Sets the worker_assign field of the booking to the given worker name

        adminRepository.save(booking);// Saves the updated booking back to the database
    }

    @Override
    public Map<String, Object> getbookingStatistics(String timePeriod) {
        List<Customer> bookings = adminRepository.findAll();
        LocalDateTime now = LocalDateTime.now();  // use LocalDateTime since entity has LocalDateTime

        bookings = bookings.stream()
                .filter(b -> Optional.ofNullable(b.getBookingDate())
                        .map(date -> {
                            switch (timePeriod.toLowerCase()) {
                                case "one-week":
                                    return date.isAfter(now.minusWeeks(1));
                                case "two-weeks":
                                    return date.isAfter(now.minusWeeks(2));
                                case "one-month":
                                    return date.isAfter(now.minusMonths(1));
                                default:
                                    return true;
                            }
                        })
                        .orElse(false))
                .collect(Collectors.toList());


        Map<String, Double> serviceRevenue = new HashMap<>();
        double totalAmount = 0.0;

        for (Customer booking : bookings) {
            String service = booking.getBooking_service_name();
            double amount = booking.getTotal_amount();
            totalAmount += amount;
            serviceRevenue.put(service, serviceRevenue.getOrDefault(service, 0.0) + amount);
        }

        // ✅ Booking Trends by Date
        Map<LocalDate, Long> bookingTrends = bookings.stream()
                .filter(b -> b.getBookingDate() != null)
                .collect(Collectors.groupingBy(
                        b -> b.getBookingDate().toLocalDate(), // convert LocalDateTime → LocalDate
                        TreeMap::new,
                        Collectors.counting()
                ));

        Map<String, Object> response = new HashMap<>();
        response.put("labels", new ArrayList<>(serviceRevenue.keySet()));
        response.put("data", new ArrayList<>(serviceRevenue.values()));
        response.put("totalcustomers", bookings.size());
        response.put("totalbooking_amount", totalAmount);
        response.put("bookingTrends", bookingTrends);

        return response;
    }


    @Override
    public Map<String, Object> getOverview(String timePeriod) {
        List<Customer> bookings = adminRepository.findAll();
        LocalDateTime now = LocalDateTime.now();

        bookings = bookings.stream()
                .filter(b -> Optional.ofNullable(b.getBookingDate())
                        .map(date -> {
                            switch (timePeriod.toLowerCase()) {
                                case "one-week": return date.isAfter(now.minusWeeks(1));
                                case "two-weeks": return date.isAfter(now.minusWeeks(2));
                                case "one-month": return date.isAfter(now.minusMonths(1));
                                default: return true;
                            }
                        })
                        .orElse(false))
                .collect(Collectors.toList());


        double totalAmount = bookings.stream()
                .mapToDouble(b -> b.getTotal_amount() != null ? b.getTotal_amount() : 0.0)
                .sum();

        int totalCustomers = (int) bookings.stream()
                .map(Customer::getCustomer_id)
                .distinct()
                .count();

        int totalBookings = bookings.size();

        Map<String, Object> overview = new HashMap<>();
        overview.put("totalAmount", totalAmount);
        overview.put("totalCustomers", totalCustomers);
        overview.put("totalBookings", totalBookings);

        return overview;
    }




    @Override
    public List<Customer> getRecentBookingsByDate() {
        return adminRepository.findAll().stream()
                .filter(customer -> customer.getBookingDate() != null)
                .sorted(Comparator.comparing(Customer::getBookingDate).reversed())
                .limit(5) // Optional: Limit to recent 10 bookings
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getVisitStatuses() {
        return adminRepository.findAll().stream()
                .map(customer -> {

                if ("Completed".equalsIgnoreCase(customer.getBookingStatus())){
                    return  "visit completed";
                }else {
                   return  "visit not completed";
                }


                }).collect(Collectors.toList());
    }

    @Override
    public List<String> updateVisitStatuses() {
        return adminRepository.findAll().stream()
                .map(customer -> {
                    String status;
                    if ("Completed".equalsIgnoreCase(customer.getBookingStatus())){
                        status=  "visit completed";
                    }else {
                      status= "visit not completed";
                    }

                    customer.setVisit_list(status);
                    adminRepository.save(customer);
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


    @Override
    public List<CustomerDTO> getTopBookedCustomers() {
        List<Object[]> results = adminRepository.findTopBookedCustomers();

        return results.stream().map(obj -> {
            CustomerDTO dto = new CustomerDTO();

            dto.setUserId(((Number) obj[2]).longValue());         // userId
            dto.setCustomer_name((String) obj[1]);                // customer name
            dto.setBooking_id(((Number) obj[2]).longValue());     // booking id
            dto.setCustomer_email((String) obj[3]);               // email
            dto.setCustomer_number((String) obj[4]);              // phone
            dto.setTotal_amount(((Number) obj[5]).doubleValue()); // total amount
            dto.setUserId(((Number) obj[0]).longValue());
            // Optional fields can be left null or set if available
            dto.setAddress_line_1(null);
            dto.setCity(null);
            dto.setBookingDate(null);
            dto.setBookingStatus(null);
            dto.setBooking_time(null);

            return dto;
        }).collect(Collectors.toList());
    }


    @Override
    public List<Map<String, Object>> getTopServices() {
        List<Object[]> results = adminRepository.findTopServices(Pageable.ofSize(3));

        List<Map<String, Object>> responseList = new ArrayList<>();

        for (Object[] row : results) {
            Map<String, Object> servicesData = new HashMap<>();
            servicesData.put("booking_service_name", row[0]);
            servicesData.put("bookingCount", row[1]);
            responseList.add(servicesData);
        }
        return responseList;
    }


    @Override
    public List<ServiceDTO> getTopRatedServices() {
        List<Object[]> results = adminRepository.findTopRatedServices();
        return results.stream()
                .map(obj -> new ServiceDTO(
                        (String) obj[0],
                        ((Number) obj[1]).doubleValue(),
                        ((Number) obj[2]).longValue(),
                        (String) obj[3],
                        ((Number) obj[4]).doubleValue(),
                        (String) obj[5]
                ))
                .collect(Collectors.toList());
    }


    @Override
    public List<InvoiceDTO> getAllInvoices() {
        // Fetch customers from repository and assign to a variable
        List<Customer> customers = customerRepository.findAllByOrderByBookingDateDesc();

        return customers.stream()
                .filter(Objects::nonNull)
                .map(customer -> {
                    InvoiceDTO dto = new InvoiceDTO();

                    dto.setBooking_id(customer.getBooking_id());
                    dto.setBookingDate(customer.getBookingDate());
                    dto.setBooking_amount(customer.getBooking_amount() != null ? customer.getBooking_amount() : 0.0);
                    dto.setTotal_amount(customer.getTotal_amount() != null ? customer.getTotal_amount() : 0.0);
                    dto.setWorker_assign(customer.getWorker_assign());
                    dto.setCity(customer.getCity());

                    dto.setCustomer_id(customer.getCustomer_id()); // safe if type is Integer

                    dto.setCustomer_name(customer.getCustomer_name());
                    dto.setCustomer_email(customer.getCustomer_email());
                    dto.setCustomer_number(customer.getCustomer_number());

                    dto.setService_id(customer.getService_id());

                    Services service = customer.getServices();
                    if (service != null) {
                        dto.setService_name(service.getService_name());
                        dto.setService_type(service.getService_type());
                        dto.setService_cost(service.getService_cost());
                        dto.setService_details(service.getService_details());
                        dto.setService_description(service.getService_description());
                    }

                    return dto;
                })
                .collect(Collectors.toList());
    }


    public List<Map<String, Object>> getServiceReport(){
        List<Customer> bookings = adminRepository.findAll();

        Map<String , List<Customer>> groupedBookings = bookings.stream()
                .filter(customer -> customer.getBooking_service_name() != null)
                .collect(Collectors.groupingBy(Customer::getBooking_service_name, LinkedHashMap::new, Collectors.toList()));

        List<Map<String,Object>> result = new ArrayList<>();

        for(Map.Entry<String, List<Customer>> entry : groupedBookings.entrySet()){
            String booking_service_name = entry.getKey();
            List<Customer> serviceBookings = entry.getValue();

            double totalRevenue = serviceBookings.stream()
                    .mapToDouble(Customer::getTotal_amount)
                    .sum();

            int bookingCount = serviceBookings.size();
            Map<String,Object> map = new HashMap<>();
            map.put("booking_Service_name", booking_service_name);
            map.put("totalRevenue", totalRevenue);
            map.put("bookingCount", bookingCount);

            result.add(map);
        }
        return result;
    }


}
