// src/main/java/com/kushi/in/app/service/impl/CustomerServiceImpl.java
package com.kushi.in.app.service.impl;

import com.kushi.in.app.dao.CustomerRepository;

import com.kushi.in.app.dao.ServiceRepository;
import com.kushi.in.app.entity.Customer;
import com.kushi.in.app.entity.Services;
import com.kushi.in.app.model.CustomerDTO;
import com.kushi.in.app.service.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private final CustomerRepository customerRepository; // lowercase 'c'
    private final ServiceRepository serviceRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository, ServiceRepository serviceRepository) {
        this.customerRepository = customerRepository;
        this.serviceRepository = serviceRepository;
    }


    // ===========================
    // Customer Management
    // ===========================

    @Override
    public List<CustomerDTO> getAll() {
        return customerRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Customer getById(Long bookingId) {
        return customerRepository.findById(bookingId).orElse(null);
    }

    @Override
    public Customer create(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer update(Long bookingId, Customer update) {
        return customerRepository.findById(bookingId).map(c -> {
            c.setCustomer_name(update.getCustomer_name());
            c.setCustomer_email(update.getCustomer_email());
            c.setCustomer_number(update.getCustomer_number());
            c.setAddress_line_1(update.getAddress_line_1());
            c.setCity(update.getCity());
            c.setZip_code(update.getZip_code());
            c.setBooking_service_name(update.getBooking_service_name());
            c.setBookingStatus(update.getBookingStatus());
            c.setPayment_status(update.getPayment_status());
            c.setRemarks(update.getRemarks());
            return customerRepository.save(c);

        }).orElse(null);
    }

    @Override
    public boolean delete(Long bookingId) {
        if (customerRepository.existsById(bookingId)) {
            customerRepository.deleteById(bookingId);
            return true;
        }
        return false;
    }

    @Override
    public List<CustomerDTO> getOrdersByUserId(Long userId) {
        return customerRepository.findByUserId(userId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CustomerDTO> getLoggedInCustomers() {
        return customerRepository.findByUserIsNotNull()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CustomerDTO> getGuestCustomers() {
        return customerRepository.findByUserIsNull()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CustomerDTO> getCompletedBookings() {
        return customerRepository.findByBookingStatus("Completed")
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<Customer> getCustomersByBookingStatus(String status) {
        return customerRepository.findByBookingStatus(status);
    }

    // ===========================
    // Service Management
    // ===========================

    @Override
    public Services addService(Services services) {
        return serviceRepository.save(services);
    }

    @Override
    public List<Services> getAllServices() {
        return serviceRepository.findAll();
    }

    @Override
    public void deleteService(Long id) {
        if (!serviceRepository.existsById(id)) {
            throw new RuntimeException("Service with ID " + id + " not found");
        }
        serviceRepository.deleteById(id);
    }

    @Override
    public Optional<Services> getServiceById(Long id) {
        return serviceRepository.findById(id);
    }

    @Override
    public Services updateService(Long id, Services services) {
        Services existing = serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found with id " + id));

        // Basic info
        existing.setService_name(services.getService_name());
        existing.setService_type(services.getService_type());
        existing.setService_category(services.getService_category());
        existing.setService_cost(services.getService_cost());
        existing.setService_description(services.getService_description());
        existing.setService_details(services.getService_details());
        existing.setService_image_url(services.getService_image_url());
        existing.setRating(services.getRating());
        existing.setRating_count(services.getRating_count());
        existing.setRemarks(services.getRemarks());
        existing.setActive(services.getActive());
        existing.setCreated_by(services.getCreated_by());
        existing.setCreate_date(services.getCreate_date());
        existing.setUpdated_by(services.getUpdated_by());
        existing.setUpdated_date(services.getUpdated_date());

        // Large LONGTEXT fields
        existing.setService_description(services.getService_description());
        existing.setOverview(services.getOverview());
        existing.setOur_process(services.getOur_process());
        existing.setBenefits(services.getBenefits());
        existing.setWhats_included(services.getWhats_included());
        existing.setWhats_not_included(services.getWhats_not_included());
        existing.setWhy_choose_us(services.getWhy_choose_us());

        return serviceRepository.save(existing);
    }


    // ===========================
    // Helper Methods
    // ===========================

    private CustomerDTO mapToDTO(Customer c) {
        return new CustomerDTO(
                c.getBooking_id(),
                c.getUser() != null ? c.getUser().getId() : null,
                c.getCustomer_name(),
                c.getCustomer_email(),
                c.getCustomer_number(),
                c.getAddress_line_1(),
                c.getCity(),
                c.getTotal_amount(),
                c.getBookingDate(),
                c.getBookingStatus(),
                c.getBooking_time()
        );
    }

    // Helper: ignore null fields when copying
    private String[] getNullPropertyNames(Services source) {
        return java.util.Arrays.stream(source.getClass().getDeclaredFields())
                .filter(f -> {
                    try {
                        f.setAccessible(true);
                        return f.get(source) == null;
                    } catch (IllegalAccessException e) {
                        return false;
                    }
                })
                .map(f -> f.getName())
                .toArray(String[]::new);
    }
}
