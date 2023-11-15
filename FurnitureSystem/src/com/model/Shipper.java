package com.model;


public class Shipper {

    private String shipperID;
    private String companyName;
    private String phone;
    private String status;

    public Shipper() {
    }

    public Shipper(String shipperID, String companyName, String phone, String status) {
        this.shipperID = shipperID;
        this.companyName = companyName;
        this.phone = phone;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getShipperID() {
        return shipperID;
    }

    public void setShipperID(String shipperID) {
        this.shipperID = shipperID;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    
}
