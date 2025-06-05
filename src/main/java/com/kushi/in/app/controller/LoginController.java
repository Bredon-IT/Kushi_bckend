package com.kushi.in.app.controller;

import com.kushi.in.app.entity.Login;
import com.kushi.in.app.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
@CrossOrigin(origins = "*") // Allow from any frontend
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/register")
     public ResponseEntity<String> registerUser(@RequestBody Login login){
        String result = loginService.registeruser(login);
        if ("Email alreay exist".equals(result)){
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }




}
