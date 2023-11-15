package com.dao;

import com.model.Customer;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Dao_Customer {

    private static final Connection con = Dao_Login.conn;
    private static PreparedStatement pst;
    private static Statement st;
    private static ResultSet rs;
    private static Customer customer;

    public static ArrayList getCustomers() {
        String sql = "SELECT * FROM Customers WHERE Status = 'Enable'";
        ArrayList<Customer> arr = new ArrayList<Customer>();
        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                customer = new Customer(rs.getString("CustomerID").trim(), rs.getString("CustomerName").trim(), rs.getString("Gender").trim(), rs.getDate("BirthDate"), rs.getString("Address").trim(), rs.getString("Phone").trim(), rs.getString("Status").trim());
                arr.add(customer);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Process.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arr;
    }
    
    public static ArrayList getCustomersForSearch() {
        String sql = "SELECT * FROM Customers";
        ArrayList<Customer> arr = new ArrayList<Customer>();
        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                customer = new Customer(rs.getString("CustomerID").trim(), rs.getString("CustomerName").trim(), rs.getString("Gender").trim(), rs.getDate("BirthDate"), rs.getString("Address").trim(), rs.getString("Phone").trim(), rs.getString("Status").trim());
                arr.add(customer);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Process.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arr;
    }
    
    public static Customer getCustomerByID(String customerID) {
        String sql = "SELECT * FROM Customers WHERE CustomerID = ?";
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, customerID);
            rs = pst.executeQuery();
            while (rs.next()) {
                customer = new Customer(rs.getString("CustomerID").trim(), rs.getString("CustomerName").trim(), rs.getString("Gender").trim(), rs.getDate("BirthDate"),
                        rs.getString("Address").trim(), rs.getString("Phone").trim(), rs.getString("Status").trim());
            }
        } catch (SQLException ex) {
            Logger.getLogger(Process.class.getName()).log(Level.SEVERE, null, ex);
        }
        return customer;
    }
    
    public static boolean insert(String customerName, String gender, Date birthdate, String address, String phone, String status) {
        boolean test = true;
        String sql = "INSERT INTO Customers (CustomerName, Gender, BirthDate, Address, Phone, Status) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, customerName);
            pst.setString(2, gender);
            pst.setDate(3, birthdate);
            pst.setString(4, address);
            pst.setString(5, phone);
            pst.setString(6, status);
            int row = pst.executeUpdate();
            if (row == 0) {
                test = false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Dao_Customer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return test;
    }
    
    public static boolean update(String customerID, String customerName, String gender, Date birthdate, String address, String phone, String status) {
        boolean test = true;
        String sql = "UPDATE Customers SET CustomerName = ?, Gender = ?, BirthDate = ?, Address = ?, Phone = ?, Status = ? WHERE CustomerID = ?";
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, customerName);
            pst.setString(2, gender);
            pst.setDate(3, birthdate);
            pst.setString(4, address);
            pst.setString(5, phone);
            pst.setString(6, status);
            pst.setString(7, customerID);
            int row = pst.executeUpdate();
            if (row == 0) {
                test = false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Dao_Customer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return test;
    }
    
    public static boolean delete(String customerID, String status) {
        boolean test = true;
        String sql = "UPDATE Customers SET Status = ? WHERE CustomerID = ?";
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, status);
            pst.setString(2, customerID);
            int row = pst.executeUpdate();
            if (row == 0) {
                test = false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Dao_Customer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return test;
    }
}
