
package com.dao;

import com.toedter.calendar.JDateChooser;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JRadioButton;
import javax.swing.JTextField;


public class Dao_Users {

    static String tk;
    static String id;

    public Dao_Users(String a) {
        tk = a;
    }
    public Dao_Users(){
    }

    public void updateInfo(JTextField name, JTextField jphone, JTextField address, JTextField mail, JDateChooser jdate, JRadioButton ra1, JRadioButton ra2) {
        Connection con;
        String gioitinh = null;
        Date birt = new Date(jdate.getDate().getTime());
        boolean aa = false;
        if (ra1.isSelected()) {
            gioitinh = "Male";
        }
        if (ra2.isSelected()) {
            gioitinh = "Female";
        }
        try {
            con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433; databaseName=FurnitureSystem;integratedSecurity=true");
            PreparedStatement pre = con.prepareStatement("select * from Staffs where Username=?");
            pre.setString(1, tk);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                id = rs.getString(1);
                aa = true;
            }
            if (aa==true) {
                PreparedStatement pre1 = con.prepareStatement("update Staffs set StaffName=?,Gender=?,BirthDate=?,Phone=?,Address=?,Email=? where StaffID =?");
                pre1.setString(1, name.getText());
                pre1.setString(2, gioitinh);
                pre1.setDate(3, birt);
                pre1.setString(4, jphone.getText());
                pre1.setString(5, address.getText());
                pre1.setString(6, mail.getText());
                pre1.setString(7, id);
                int ca = pre1.executeUpdate();
            }
        } catch (Exception e) {
        }
    }
}
