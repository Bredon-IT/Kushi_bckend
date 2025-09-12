package com.kushi.in.app.dao;

import com.kushi.in.app.entity.Customer;
import com.kushi.in.app.model.CustomerDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findByUserId(Long userId);

    List<Customer> findByBookingStatus(String bookingStatus);

    List<Customer> findByUserIsNotNull();

    List<Customer> findByUserIsNull();

    List<Customer> findAllByOrderByBookingDateDesc();

    @Query("SELECT c FROM Customer c WHERE c.bookingStatus = 'Completed'")
    List<Customer> findCompletedBookings();

    // DTO projection example
    @Query("SELECT CustomerDTO(c.booking_id, c.user.id, c.customer_name, c.customer_email, c.customer_number, c.address_line_1, c.city, c.total_amount, c.bookingDate, c.bookingStatus, c.booking_time) FROM Customer c")
    List<CustomerDTO> findAllCustomersDTO();

}
