package com.kushi.in.app.service.impl;

import com.kushi.in.app.dao.LoginRepository;
import com.kushi.in.app.entity.Login;
import com.kushi.in.app.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginRepository loginRepository;

    @Override
    public String registeradmin(Login login) {
        if (loginRepository.existsByEmail(login.getEmail())){
            return "Email already exists";
        }
        loginRepository.save(login);
        return "User registred successfully";
    }

    @Override
    public Login loginAdmin(String email, String password) {
        return loginRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public Login getAdminByEmail(String email) {
        return loginRepository.findByEmail(email).orElse(null);
    }

    @Override
    public Login updateAdmin(Long id, Login updatedAdmin) {
        return loginRepository.findById(id).map(existingAdmin -> {
            existingAdmin.setAdminname(updatedAdmin.getAdminname());
            existingAdmin.setEmail(updatedAdmin.getEmail());
            existingAdmin.setPhoneNumber(updatedAdmin.getPhoneNumber());
            existingAdmin.setPassword(updatedAdmin.getPassword());
            return loginRepository.save(existingAdmin);
        }).orElse(null);
    }


    @Override
    public Login updatePassword(Long adminId, String oldPassword, String newPassword) {
        Login admin = loginRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        if (!admin.getPassword().equals(oldPassword)) {
            throw new RuntimeException("Old password is incorrect");
        }

        admin.setPassword(newPassword);
        return loginRepository.save(admin);
    }



}

