package com.kushi.in.app.entity;

import jakarta.persistence.*;

@Entity
@Table(name="tbl_service_info" )
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String active;

    private String create_date;

    private String created_by;

    private String rating;

    private String rating_count;

    private String remarks;

    private double service_cost;

    private String service_description;

    private String service_details;

    private String service_image_url;

    private String service_name;

    private String service_type;

    private String updated_by;

    private String updated_date;

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", active='" + active + '\'' +
                ", create_date='" + create_date + '\'' +
                ", created_by='" + created_by + '\'' +
                ", rating='" + rating + '\'' +
                ", rating_count='" + rating_count + '\'' +
                ", remarks='" + remarks + '\'' +
                ", service_cost=" + service_cost +
                ", service_description='" + service_description + '\'' +
                ", service_details='" + service_details + '\'' +
                ", service_image_url='" + service_image_url + '\'' +
                ", service_name='" + service_name + '\'' +
                ", service_type='" + service_type + '\'' +
                ", updated_by='" + updated_by + '\'' +
                ", updated_date='" + updated_date + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getRating_count() {
        return rating_count;
    }

    public void setRating_count(String rating_count) {
        this.rating_count = rating_count;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public double getService_cost() {
        return service_cost;
    }

    public void setService_cost(double service_cost) {
        this.service_cost = service_cost;
    }

    public String getService_description() {
        return service_description;
    }

    public void setService_description(String service_description) {
        this.service_description = service_description;
    }

    public String getService_details() {
        return service_details;
    }

    public void setService_details(String service_details) {
        this.service_details = service_details;
    }

    public String getService_image_url() {
        return service_image_url;
    }

    public void setService_image_url(String service_image_url) {
        this.service_image_url = service_image_url;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getService_type() {
        return service_type;
    }

    public void setService_type(String service_type) {
        this.service_type = service_type;
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
}
