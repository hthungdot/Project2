
package com.dao;

import com.model.Category;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Dao_Categories {

    private static final Connection con = Dao_Login.conn;
    private static PreparedStatement pstmt;
    private static Statement st;
    private static ResultSet rs;
    private static Category Category;

    public boolean insert(Category Category) throws Exception  {
        String sql = "insert into Categories(CategoryName,Description)values(?,?)";
        boolean b = false;
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, Category.getCategoryName());
            pstmt.setString(2, Category.getDescription());
            b = pstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(Dao_Products.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }
    //search

    public static Category searchid(String CategoryID) {
        String sql = "select * from Categories where CategoryID = ?";
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, CategoryID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                Category = new Category();
                Category.setCategoryID(rs.getString("CategoryID"));
                Category.setCategoryName(rs.getString("CategoryName"));
                Category.setDescription(rs.getString("Description"));
                return Category;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Dao_Products.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static Category searchname(String Categoryname) throws Exception {
        String sql = "select * from Categories where CategoryName = ?";
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, Categoryname);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Category = new Category();
                Category.setCategoryID(rs.getString("CategoryID"));
                Category.setCategoryName(rs.getString("CategoryName"));
                Category.setDescription(rs.getString("Description"));
                return Category;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Dao_Products.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
//show table

    public ArrayList<Category> searchAll() throws Exception {
        ArrayList<Category> list = new ArrayList<>();
        String sql = "select * from Categories";
        try {
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Category = new Category();
                Category.setCategoryID(rs.getString("CategoryID"));
                Category.setCategoryName(rs.getString("CategoryName"));
                Category.setDescription(rs.getString("Description"));
                list.add(Category);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Dao_Products.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    //delete
    public boolean delete(String CategoryID) throws Exception {
        String sql = "delete from Categories where CategoryID =?";
        boolean b = false;
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, CategoryID);
            b = pstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(Dao_Products.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }

    //Update
    public boolean Update(Category crt) throws Exception {
        String sql = " update Categories set CategoryName=?,Description=?" + " where CategoryID = ?";
        boolean b = false;
        //String sql = "insert into Student_SQL(StudentID,NAME,Age,Address,GpA)values(?,?,?,?,?)";
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(3, crt.getCategoryID());
            pstmt.setString(1, crt.getCategoryName());
            pstmt.setString(2, crt.getDescription());
            b = pstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(Dao_Products.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }
}
