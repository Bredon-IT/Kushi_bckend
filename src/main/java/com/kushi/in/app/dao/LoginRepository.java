package com.kushi.in.app.dao;

import com.kushi.in.app.entity.Login;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends JpaRepository<Login, Long> {
   boolean existsByUserEmail(String userEmail);
}
