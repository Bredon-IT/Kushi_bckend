package com.kushi.in.app.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;




@Entity
@Table(name = "tbl_booking_info")

public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long booking_id;

    private String address_line_1;

    private String address_line_2;

    private String address_line_3;

    private String booking_amount;

    private LocalDate booking_date;

    private String booking_service_name;

    private String  booking_status;

    private String booking_time;

    private String city;

    private String confirmation_date;

    private String created_by;

    private String created_date;

    private String customer_id;

    private String customer_name;

    private String customer_number;

    private String payment_status;

    private String reference_details;

    private String reference_name;

    private String remarks;

    private Double total_amount;

    private String updated_by;

    private String updated_date;

    private String zip_code;

    private String customer_email;

    private String worker_assign;

    private String visit_list;

    @Override
    public String toString() {
        return "Admin{" +
                "booking_id=" + booking_id +
                ", address_line_1='" + address_line_1 + '\'' +
                ", address_line_2='" + address_line_2 + '\'' +
                ", address_line_3='" + address_line_3 + '\'' +
                ", booking_amount='" + booking_amount + '\'' +
                ", booking_date='" + booking_date + '\'' +
                ", booking_service_name='" + booking_service_name + '\'' +
                ", booking_status='" + booking_status + '\'' +
                ", booking_time='" + booking_time + '\'' +
                ", city='" + city + '\'' +
                ", confirmation_date='" + confirmation_date + '\'' +
                ", created_by='" + created_by + '\'' +
                ", created_date='" + created_date + '\'' +
                ", customer_id='" + customer_id + '\'' +
                ", customer_name='" + customer_name + '\'' +
                ", customer_number='" + customer_number + '\'' +
                ", payment_status='" + payment_status + '\'' +
                ", reference_details='" + reference_details + '\'' +
                ", reference_name='" + reference_name + '\'' +
                ", remarks='" + remarks + '\'' +
                ", total_amount='" + total_amount + '\'' +
                ", updated_by='" + updated_by + '\'' +
                ", updated_date='" + updated_date + '\'' +
                ", zip_code='" + zip_code + '\'' +
                ", customer_email='" + customer_email + '\'' +
                ", worker_assign='" + worker_assign + '\'' +
                ", visit_list='" + visit_list + '\'' +
                '}';
    }

    public Long getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(Long booking_id) {
        this.booking_id = booking_id;
    }

    public String getAddress_line_1() {
        return address_line_1;
    }

    public void setAddress_line_1(String address_line_1) {
        this.address_line_1 = address_line_1;
    }

    public String getAddress_line_2() {
        return address_line_2;
    }

    public void setAddress_line_2(String address_line_2) {
        this.address_line_2 = address_line_2;
    }

    public String getAddress_line_3() {
        return address_line_3;
    }

    public void setAddress_line_3(String address_line_3) {
        this.address_line_3 = address_line_3;
    }

    public String getBooking_amount() {
        return booking_amount;
    }

    public void setBooking_amount(String booking_amount) {
        this.booking_amount = booking_amount;
    }



    public String getBooking_service_name() {
        return booking_service_name;
    }

    public LocalDate getBooking_date() {
        return booking_date;
    }

    public void setBooking_date(LocalDate booking_date) {
        this.booking_date = booking_date;
    }

    public void setBooking_service_name(String booking_service_name) {
        this.booking_service_name = booking_service_name;
    }

    public String getBooking_status() {
        return booking_status;
    }

    public void setBooking_status(String booking_status) {
        this.booking_status = booking_status;
    }

    public String getBooking_time() {
        return booking_time;
    }

    public void setBooking_time(String booking_time) {
        this.booking_time = booking_time;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getConfirmation_date() {
        return confirmation_date;
    }

    public void setConfirmation_date(String confirmation_date) {
        this.confirmation_date = confirmation_date;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_number() {
        return customer_number;
    }

    public void setCustomer_number(String customer_number) {
        this.customer_number = customer_number;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }

    public String getReference_details() {
        return reference_details;
    }

    public void setReference_details(String reference_details) {
        this.reference_details = reference_details;
    }

    public String getReference_name() {
        return reference_name;
    }

    public void setReference_name(String reference_name) {
        this.reference_name = reference_name;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }


    public double getTotal_amount() {
        return total_amount == null ? 0.0 : total_amount;
    }

    public void setTotal_amount(Double total_amount) {
        this.total_amount = total_amount;
    }

    public String getUpdated_by() {
        return updated_by;
    }

    public void setUpdated_by(String updated_by) {
        this.updated_by = updated_by;
    }

    public String getUpdated_date() {
        return updated_date;
    }

    public void setUpdated_date(String updated_date) {
        this.updated_date = updated_date;
    }

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

    public String getCustomer_email() {
        return customer_email;
    }

    public void setCustomer_email(String customer_email) {
        this.customer_email = customer_email;
    }

    public String getWorker_assign() {
        return worker_assign;
    }

    public void setWorker_assign(String worker_assign) {
        this.worker_assign = worker_assign;
    }

    public String getVisit_list() {
        return visit_list;
    }

    public void setVisit_list(String visit_list) {
        this.visit_list = visit_list;
    }
}
