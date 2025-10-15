package com.kushi.in.app.service;


import com.kushi.in.app.entity.Customer;
import com.kushi.in.app.model.BookingDTO;
import com.kushi.in.app.model.BookingRequest;
import com.kushi.in.app.model.OrderDTO;

import java.util.List;

public interface BookingService {
    Customer createBooking(BookingRequest request);

    List<BookingDTO> getAllBookings();

    Customer updateBookingStatus(Long bookingId, String status, String canceledBy);

    void sendBookingNotification(String email, String phoneNumber, String status);

    void updateBookingDiscount(Long bookingId, Double discount);




}
