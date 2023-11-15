
package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JTextField;


public class Dao_ChangePass {

    static String tk;

    public Dao_ChangePass(String tk) {
        this.tk += tk;
    }

    public Dao_ChangePass() {
    }

    public void doiMK(JTextField j2, JTextField j3) {
        Connection con;

        String mkcu = j2.getText();
        String mkc = j3.getText();
        boolean bl = false;
        
        try {
            con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433; databaseName=FurnitureSystem;integratedSecurity=true");
            Statement stm = con.createStatement();

            ResultSet rs = stm.executeQuery("select * from Staffs");
            while (rs.next()) {
                if (rs.getString(9).equals(mkcu)) {
                    bl = true;
                }
            }
            if (bl == true) {
                PreparedStatement pre1 = con.prepareStatement("update Staffs set PassWord=? where Username=?");
                pre1.setString(1, mkc);
                pre1.setString(2, tk.substring(4));
                int aa = pre1.executeUpdate();
                System.out.println(aa);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
