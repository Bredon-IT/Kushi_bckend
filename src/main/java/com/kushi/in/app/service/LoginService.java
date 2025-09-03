package com.kushi.in.app.service;

import com.kushi.in.app.entity.Login;

public interface LoginService {
  String registeradmin(Login login);

  Login loginAdmin(String email, String password);



  Login getAdminByEmail(String email);

  Login updateAdmin(Long id, Login updatedAdmin);

  // LoginService.java
  Login updatePassword(Long adminId, String oldPassword, String newPassword);

}
