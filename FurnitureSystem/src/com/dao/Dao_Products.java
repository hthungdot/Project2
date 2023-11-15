
package com.dao;

import com.model.OrderDetails;
import com.model.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Dao_Products {

    private static final Connection con = Dao_Login.conn;
    private static PreparedStatement pstmt;
    private static Statement st;
    private static ResultSet rs;

    public boolean insert(Product Products) {
        boolean b = false;
        String sql = "insert into Products(ProductName,SupplierID,CategoryID,UnitPrice,SellPrice,QuantityInStock,Status)values(?,?,?,?,?,?,?)";
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, Products.getProductName());
            pstmt.setString(2, Products.getSupplierID());
            pstmt.setString(3, Products.getCategoryID());
            pstmt.setDouble(4, Products.getUnitPrice());
            pstmt.setDouble(5, Products.getSellPrice());
            pstmt.setInt(6, Products.getQuantity());
            pstmt.setString(7, Products.getStatus());
            b = pstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(Dao_Products.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }

    public boolean Update(Product Products) throws Exception {
        boolean b = false;
        String sql = " update Products set ProductName=?,SupplierID=?,CategoryID=?,UnitPrice=?,SellPrice=?,QuantityInStock=?,Status=?" + " where ProductID = ?";
        //String sql = "insert into Student_SQL(StudentID,NAME,Age,Address,GpA)values(?,?,?,?,?)";
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(8, Products.getProductID());
            pstmt.setString(1, Products.getProductName());
            pstmt.setString(2, Products.getSupplierID());
            pstmt.setString(3, Products.getCategoryID());
            pstmt.setDouble(4, Products.getUnitPrice());
            pstmt.setDouble(5, Products.getSellPrice());
            pstmt.setInt(6, Products.getQuantity());
            pstmt.setString(7, Products.getStatus());
            b = pstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(Dao_Products.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }

    public boolean UpdatePriceall(Product Products) throws Exception {
        boolean b = false;
        String sql = " update Products set ProductName=?,SupplierID=?,UnitPrice=?,SellPrice=?,QuantityInStock=?,Status=?" + " where CategoryID = ?";
        //String sql = "insert into Student_SQL(StudentID,NAME,Age,Address,GpA)values(?,?,?,?,?)";
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(7, Products.getCategoryID());
            pstmt.setString(1, Products.getProductName());
            pstmt.setString(2, Products.getSupplierID());
            pstmt.setDouble(3, Products.getUnitPrice());
            pstmt.setDouble(4, Products.getSellPrice());
            pstmt.setInt(5, Products.getQuantity());
            pstmt.setString(6, Products.getStatus());
            b = pstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(Dao_Products.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }
//    //delete

    public boolean delete(Product products) throws Exception {
        String sql = " update Products set Status=? " + " where ProductID = ?";
        boolean b = false;
        //String sql = "insert into Student_SQL(StudentID,NAME,Age,Address,GpA)values(?,?,?,?,?)";
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(2, products.getProductID());
            pstmt.setString(1, products.getStatus());
            b = pstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(Dao_Products.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }

    //search
    public static Product search(String ProductsID) {
        String sql = "select * from Products where ProductID = ?";
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, ProductsID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Product Products = new Product();
                Products.setProductID(rs.getString("ProductID"));
                Products.setProductName(rs.getString("ProductName"));
                Products.setSupplierID(rs.getString("SupplierID"));
                Products.setCategoryID(rs.getString("CategoryID"));
                Products.setUnitPrice(rs.getDouble("UnitPrice"));
                Products.setSellPrice(rs.getDouble("SellPrice"));
                Products.setQuantity(rs.getInt("QuantityInStock"));
                Products.setStatus(rs.getString("Status"));
                return Products;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Dao_Products.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
//show table

    public static ArrayList<Product> searchAll() {
        String sql = "select * from Products";
        ArrayList<Product> list = new ArrayList<Product>();
        try {
            pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Product Products = new Product();
                Products.setProductID(rs.getString("ProductID"));
                Products.setProductName(rs.getString("ProductName"));
                Products.setSupplierID(rs.getString("SupplierID"));
                Products.setCategoryID(rs.getString("CategoryID"));
                Products.setUnitPrice(rs.getDouble("UnitPrice"));
                Products.setSellPrice(rs.getDouble("SellPrice"));
                Products.setQuantity(rs.getInt("QuantityInStock"));
                Products.setStatus(rs.getString("Status"));
                list.add(Products);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Dao_Products.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public static boolean updateProductOrder(String productID, int productQuantity) {
        boolean test = true;
        String sql = "UPDATE Products SET QuantityInStock = ? WHERE ProductID = ?";
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, productQuantity);
            pstmt.setString(2, productID);
            int row = pstmt.executeUpdate();
            if (row == 0) {
                test = false;
                return test;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Dao_Products.class.getName()).log(Level.SEVERE, null, ex);
        }
        return test;
    }

    public OrderDetails searchOrderDetails(String productsID) throws Exception {
        String sql = "select * from [Order Details] where ProductID = ?";
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, productsID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                OrderDetails orderDetails = new OrderDetails();
                orderDetails.setProductID(rs.getString("ProductID"));
                orderDetails.setOrderID(rs.getString("OrderID"));
                orderDetails.setUnitPrice(rs.getDouble("UnitPrice"));
                orderDetails.setQuantity(rs.getInt("Quantity"));
                return orderDetails;

            }
            } catch (SQLException ex) {
            Logger.getLogger(Dao_Products.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public Product searchname(String productName) throws Exception {
        String sql = "select * from Products where ProductName = ?";
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, productName);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                Product products = new Product();
                products.setProductID(rs.getString("ProductID"));
                products.setProductName(rs.getString("ProductName"));
                products.setSupplierID(rs.getString("SupplierID"));
                products.setCategoryID(rs.getString("CategoryID"));
                products.setUnitPrice(rs.getDouble("UnitPrice"));
                products.setSellPrice(rs.getDouble("SellPrice"));
                products.setQuantity(rs.getInt("QuantityInStock"));
                products.setStatus(rs.getString("Status"));
                return products;

            }
            } catch (SQLException ex) {
            Logger.getLogger(Dao_Products.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
