package com.kushi.in.app.dao;

import com.kushi.in.app.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    // This interface provides CRUD operations for the Admin entity using booking_id as the primary key.
}
