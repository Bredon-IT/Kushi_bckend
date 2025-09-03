package com.kushi.in.app.controller;

import com.kushi.in.app.entity.Login;
import com.kushi.in.app.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
@CrossOrigin(origins = "*") // Allow from any frontend
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/register")
     public ResponseEntity<String> registerAdmin(@RequestBody Login login){
        String result = loginService.registeradmin(login);
        if ("Email alreay exist".equals(result)){
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }


    // ✅ Login
    @PostMapping("/login")
    public ResponseEntity<?> loginAdmin(@RequestBody Login login) {
        Login loggedInAdmin = loginService.loginAdmin(login.getEmail(), login.getPassword());
        if (loggedInAdmin != null) {
            return ResponseEntity.ok(loggedInAdmin); // return user details instead of plain string
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }


    // Fetch admin by email
    @GetMapping("/me/{email}")
    public ResponseEntity<?> getAdminByEmail(@PathVariable String email) {
        Login admin = loginService.getAdminByEmail(email);
        if (admin != null) {
            return ResponseEntity.ok(admin);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Admin not found");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateAdmin(@PathVariable Long id, @RequestBody Login updatedAdmin) {
        Login admin = loginService.updateAdmin(id, updatedAdmin);
        if (admin != null) {
            return ResponseEntity.ok(admin);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Admin not found");
        }
    }

    @PutMapping("/update-password/{adminId}")
    public ResponseEntity<?> updatePassword(@PathVariable Long adminId,
                                            @RequestParam String oldPassword,
                                            @RequestParam String newPassword) {
        try {
            loginService.updatePassword(adminId, oldPassword, newPassword);
            return ResponseEntity.ok("Password updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
