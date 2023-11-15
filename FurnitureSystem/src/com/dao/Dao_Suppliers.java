
package com.dao;

import com.model.Category;
import com.model.Supplier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Dao_Suppliers {

    private static final Connection con = Dao_Login.conn;
    private static PreparedStatement pstmt;
    private static Statement st;
    private static ResultSet rs;

    public boolean insert(Supplier Suppliers) throws Exception {
        String sql = "insert into Suppliers(CompanyName,Address,Phone,Email,Status)values(?,?,?,?,?)";
        boolean b = false;
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, Suppliers.getCompanyName());
            pstmt.setString(2, Suppliers.getAddress());
            pstmt.setString(3, Suppliers.getPhone());
            pstmt.setString(4, Suppliers.getEmail());
            pstmt.setString(5, Suppliers.getStatus());
            b = pstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(Process.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }

    public boolean Update(Supplier Suppliers) throws Exception {
        String sql = " update Suppliers set CompanyName=?,Address=?,Phone=?,Email=?,Status=?" + " where SupplierID = ?";
        boolean b = false;
        //String sql = "insert into Student_SQL(StudentID,NAME,Age,Address,GpA)values(?,?,?,?,?)";
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(6, Suppliers.getSupplierID());
            pstmt.setString(1, Suppliers.getCompanyName());
            pstmt.setString(2, Suppliers.getAddress());
            pstmt.setString(3, Suppliers.getPhone());
            pstmt.setString(4, Suppliers.getEmail());
            pstmt.setString(5, Suppliers.getStatus());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(Process.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }
    //delete

    public boolean delete(Supplier supplier) throws Exception {
        String sql = " update Suppliers set Status=?" + " where SupplierID = ?";
        boolean b = false;
        try {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, supplier.getStatus());
            pstmt.setString(2, supplier.getSupplierID());
            b = pstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(Process.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }

    //search
    public static Supplier search(String SuppliersID) {
        String sql = "select * from Suppliers where SupplierID = ?";
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, SuppliersID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                Supplier Suppliers = new Supplier();
                Suppliers.setSupplierID(rs.getString("SupplierID"));
                Suppliers.setCompanyName(rs.getString("CompanyName"));
                Suppliers.setAddress(rs.getString("Address"));
                Suppliers.setPhone(rs.getString("Phone"));
                Suppliers.setEmail(rs.getString("Email"));
                Suppliers.setStatus(rs.getString("Status"));
                return Suppliers;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Process.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public Supplier searchname(String CompanyName) throws Exception {
        String sql = "select * from Suppliers where CompanyName = ?";
       try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, CompanyName);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                Supplier Suppliers = new Supplier();
                Suppliers.setSupplierID(rs.getString("SupplierID"));
                Suppliers.setCompanyName(rs.getString("CompanyName"));
                Suppliers.setAddress(rs.getString("Address"));
                Suppliers.setPhone(rs.getString("Phone"));
                Suppliers.setEmail(rs.getString("Email"));
                Suppliers.setStatus(rs.getString("Status"));
                return Suppliers;

            }
        } catch (SQLException ex) {
            Logger.getLogger(Process.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
//show table

    public ArrayList<Supplier> searchAll() throws Exception {
        String sql = "select * from Suppliers";
        ArrayList<Supplier> list = new ArrayList<>();
        try {
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Supplier Suppliers = new Supplier();
                Suppliers.setSupplierID(rs.getString("SupplierID"));
                Suppliers.setCompanyName(rs.getString("CompanyName"));
                Suppliers.setAddress(rs.getString("Address"));
                Suppliers.setPhone(rs.getString("Phone"));
                Suppliers.setEmail(rs.getString("Email"));
                Suppliers.setStatus(rs.getString("Status"));
                list.add(Suppliers);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Process.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}
