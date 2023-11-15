
package com.dao;

import com.model.Promotion;
//import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Dao_Promotion {

    private static final Connection con = Dao_Login.conn;
    private static PreparedStatement pstmt;
    private static Statement st;
    private static ResultSet rs;

    public boolean insert(Promotion Promotion) {
        String sql = "insert into Promotion(PromotionName,PromotionDate,Discount,AmountOfMoney,Quantity,Note)values(?,?,?,?,?,?)";
        boolean b = false;
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, Promotion.getPromotionName());
            pstmt.setDate(2, Promotion.getPromotionDate());
            pstmt.setFloat(3, Promotion.getDiscount());
            pstmt.setInt(4, Promotion.getAmountOfMoney());
            pstmt.setInt(5, Promotion.getQuantity());
            pstmt.setString(6, Promotion.getNote());
            b = pstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(Process.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }

    public boolean Update(Promotion Promotion) {
        String sql = " update Promotion set PromotionName=?,PromotionDate=?,Discount=?,AmountOfMoney=?,Quantity=?,Note=?" + " where PromotionID = ?";
        boolean b = false;
        //String sql = "insert into Student_SQL(StudentID,NAME,Age,Address,GpA)values(?,?,?,?,?)";
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(7, Promotion.getPromotionID());
            pstmt.setString(1, Promotion.getPromotionName());
            pstmt.setDate(2, Promotion.getPromotionDate());
            pstmt.setFloat(3, Promotion.getDiscount());
            pstmt.setInt(4, Promotion.getAmountOfMoney());
            pstmt.setInt(5, Promotion.getQuantity());
            pstmt.setString(6, Promotion.getNote());
            b = pstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(Process.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }
    //search

    public static Promotion search(String PromotionID) {
        String sql = "select * from Promotion where PromotionID = ?";
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, PromotionID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Promotion Promotion = new Promotion();
                Promotion.setPromotionID(rs.getString("PromotionID"));
                Promotion.setPromotionName(rs.getString("PromotionName"));
                Promotion.setPromotionDate(rs.getDate("PromotionDate"));
                Promotion.setDiscount(rs.getFloat("Discount"));
                Promotion.setAmountOfMoney(rs.getInt("AmountOfMoney"));
                Promotion.setQuantity(rs.getInt("Quantity"));
                Promotion.setNote(rs.getString("Note"));
                return Promotion;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Process.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public Promotion searchname(String PromotionName) throws Exception {
        String sql = "select * from Promotion where PromotionName = ?";
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, PromotionName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Promotion Promotion = new Promotion();
                Promotion.setPromotionID(rs.getString("PromotionID"));
                Promotion.setPromotionName(rs.getString("PromotionName"));
                Promotion.setPromotionDate(rs.getDate("PromotionDate"));
                Promotion.setDiscount(rs.getFloat("Discount"));
                Promotion.setAmountOfMoney(rs.getInt("AmountOfMoney"));
                Promotion.setQuantity(rs.getInt("Quantity"));
                Promotion.setNote(rs.getString("Note"));
                return Promotion;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Process.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
//show table

    public static ArrayList<Promotion> searchAll() {
        String sql = "select * from Promotion";
        ArrayList<Promotion> list = new ArrayList<>();
        try {
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Promotion Promotion = new Promotion();
                Promotion.setPromotionID(rs.getString("PromotionID"));
                Promotion.setPromotionName(rs.getString("PromotionName"));
                Promotion.setPromotionDate(rs.getDate("PromotionDate"));
                Promotion.setDiscount(rs.getFloat("Discount"));
                Promotion.setAmountOfMoney(rs.getInt("AmountOfMoney"));
                Promotion.setQuantity(rs.getInt("Quantity"));
                Promotion.setNote(rs.getString("Note"));
                list.add(Promotion);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Process.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public static boolean updatePromotionOrder(int status, String promotionID) {
        boolean test = true;
        String sql = "UPDATE Promotion SET Quantity = ? WHERE PromotionID = ?";
        int quantity = search(promotionID).getQuantity();
        try {
            if (status == 1) {
                quantity = search(promotionID).getQuantity() - 1;
            } else if (status == 2) {
                quantity = search(promotionID).getQuantity() + 1;
            }
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, quantity);
            pstmt.setString(2, promotionID);
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
}
