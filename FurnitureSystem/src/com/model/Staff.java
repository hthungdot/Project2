package com.model;

import java.sql.Date;


public class Staff {

    private String staffID;
    private String staffName;
    private String gender;
    private Date birthDate;
    private Date hireDate;
    private String address;
    private String phone;
    private String username;
    private String password;
    private String email;
    private String status;

    public Staff() {
    }

    public Staff(String staffID, String staffName, String gender, Date birthDate, Date hireDate, String address, String phone, String username, String password, String email, String status) {
        this.staffID = staffID;
        this.staffName = staffName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.hireDate = hireDate;
        this.address = address;
        this.phone = phone;
        this.username = username;
        this.password = password;
        this.email = email;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    
}
