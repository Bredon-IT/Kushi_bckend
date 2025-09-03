package com.kushi.in.app.dao;

import com.kushi.in.app.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    // This interface provides CRUD operations for the Admin entity using booking_id as the primary key.
    @Query("SELECT a.booking_service_name AS serviceName, SUM(a.booking_amount) AS totalRevenue " +
            "FROM Admin a GROUP BY a.booking_service_name")
    List<Map<String, Object>> getRevenueByService();

    @Query("SELECT COUNT(a) FROM Admin a WHERE a.booking_date = CURRENT_DATE")
    long countTodayBookings();

    // 👇 Pending approvals (where booking_status = 'Pending')
    @Query("SELECT COUNT(a) FROM Admin a WHERE LOWER(a.booking_status) = 'pending'")
    long countPendingApprovals();
}
