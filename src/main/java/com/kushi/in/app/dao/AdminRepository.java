package com.kushi.in.app.dao;


import com.kushi.in.app.entity.Customer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AdminRepository extends JpaRepository<Customer, Long> {
    // This interface provides CRUD operations for the Admin entity using booking_id as the primary key.
    @Query("SELECT a.booking_service_name AS serviceName, SUM(a.booking_amount) AS totalRevenue " +
            "FROM Customer a GROUP BY a.booking_service_name")
    List<Map<String, Object>> getRevenueByService();

    @Query("SELECT COUNT(a) FROM Customer a WHERE a.bookingDate = CURRENT_DATE")
    long countTodayBookings();

    // 👇 Pending approvals (where booking_status = 'Pending')
    @Query("SELECT COUNT(a) FROM Customer a WHERE LOWER(a.bookingStatus) = 'pending'")
    long countPendingApprovals();


    @Query(value = "SELECT b.customer_id, b.customer_name, COUNT(b.customer_id) AS booking_count, " +
            "b.address_line_1, b.customer_number, SUM(b.booking_amount) as total_amount  " +
            "FROM tbl_booking_info b " +
            "GROUP BY b.customer_id, b.customer_name, b.address_line_1, b.customer_number " +
            "ORDER BY booking_count DESC LIMIT 5", nativeQuery = true)
    List<Object[]> findTopBookedCustomers();



    @Query("SELECT c.booking_service_name, COUNT(c) FROM Customer c GROUP BY c.booking_service_name ORDER BY COUNT(c) DESC")
    List<Object[]> findTopServices(Pageable pageable);


    @Query(value = "SELECT s.service_name, AVG(s.rating) AS rating, COUNT(s.rating) AS rating_count, " +
            "s.service_type, s.service_cost, s.service_image_url " +
            "FROM tbl_service_info s " +
            "GROUP BY s.service_name, s.service_image_url, s.service_cost, s.service_type " +
            "ORDER BY rating DESC LIMIT 5", nativeQuery = true)
    List<Object[]> findTopRatedServices();








}
