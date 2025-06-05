package com.kushi.in.app.entity;

import jakarta.persistence.*;

@Entity
@Table(name="tbl_login")
public class Login {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long login_id;

    private String userName;

    private String userEmail;

    private String user_Phonenumber;

    private String user_Address;

    private String user_Password;

    public Long getLogin_id() {
        return login_id;
    }

    public void setLogin_id(Long login_id) {
        this.login_id = login_id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUser_Phonenumber() {
        return user_Phonenumber;
    }

    public void setUser_Phonenumber(String user_Phonenumber) {
        this.user_Phonenumber = user_Phonenumber;
    }

    public String getUser_Address() {
        return user_Address;
    }

    public void setUser_Address(String user_Address) {
        this.user_Address = user_Address;
    }

    public String getUser_Password() {
        return user_Password;
    }

    public void setUser_Password(String user_Password) {
        this.user_Password = user_Password;
    }
}