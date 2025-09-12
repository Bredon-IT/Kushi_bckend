package com.kushi.in.app.model;

import java.time.LocalDateTime;

public class CustomerDTO {
    private Long booking_id;
    private Long userId;
    private String customer_name;
    private String customer_email;
    private String customer_number;
    private String address_line_1;
    private String city;
    private Double total_amount;
    private LocalDateTime bookingDate;
    private String bookingStatus;
    private String booking_time;

    // No-arg constructor
    public CustomerDTO() {
    }

    // Existing full-arg constructor
    public CustomerDTO(Long booking_id, Long userId, String customer_name, String customer_email, String customer_number,
                       String address_line_1, String city, Double total_amount, LocalDateTime bookingDate,
                       String bookingStatus, String booking_time) {
        this.booking_id = booking_id;
        this.userId = userId;
        this.customer_name = customer_name;
        this.customer_email = customer_email;
        this.customer_number = customer_number;
        this.address_line_1 = address_line_1;
        this.city = city;
        this.total_amount = total_amount;
        this.bookingDate = bookingDate;
        this.bookingStatus = bookingStatus;
        this.booking_time = booking_time;
    }

    // Getters and setters...

    public Long getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(Long booking_id) {
        this.booking_id = booking_id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_email() {
        return customer_email;
    }

    public void setCustomer_email(String customer_email) {
        this.customer_email = customer_email;
    }

    public String getCustomer_number() {
        return customer_number;
    }

    public void setCustomer_number(String customer_number) {
        this.customer_number = customer_number;
    }

    public String getAddress_line_1() {
        return address_line_1;
    }

    public void setAddress_line_1(String address_line_1) {
        this.address_line_1 = address_line_1;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(Double total_amount) {
        this.total_amount = total_amount;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public String getBooking_time() {
        return booking_time;
    }

    public void setBooking_time(String booking_time) {
        this.booking_time = booking_time;
    }
}
