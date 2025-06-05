package com.kushi.in.app.service.impl;

import com.kushi.in.app.dao.LoginRepository;
import com.kushi.in.app.entity.Login;
import com.kushi.in.app.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginRepository loginRepository;

    @Override
    public String registeruser(Login login) {
        if (loginRepository.existsByUserEmail(login.getUserEmail())){
            return "Email already exists";
        }
        loginRepository.save(login);
        return "User registred successfully";
    }
}
