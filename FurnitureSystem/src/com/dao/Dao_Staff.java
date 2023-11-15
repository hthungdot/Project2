package com.dao;

import com.model.Staff;
import com.toedter.calendar.JDateChooser;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;


public class Dao_Staff {

    private static final Connection con = Dao_Login.conn;
    private static PreparedStatement pst;
    private static ResultSet rs;
    public static String staffID;
    private static Staff staff;

    public static Staff search(String Username) {
        String sql = "select * from Staffs where Username = ?";
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, Username);
            rs = pst.executeQuery();
            if (rs.next()) {
                Staff nv = new Staff();
                nv.setUsername(rs.getString("Username"));
                nv.setStatus(rs.getString("Status"));
                return nv;
            }

        } catch (SQLException ex) {
            Logger.getLogger(Process.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static String getStaffID(String username) {
        String sql = "SELECT StaffID FROM Staffs WHERE Username = ?";
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, username);
            rs = pst.executeQuery();
            while (rs.next()) {
                staffID = rs.getString("StaffID");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Process.class.getName()).log(Level.SEVERE, null, ex);
        }
        return staffID;
    }

    public static Staff getStaff(String staffID) {
        String sql = "SELECT * FROM Staffs WHERE StaffID = ?";
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, staffID);
            rs = pst.executeQuery();
            while (rs.next()) {
                staff = new Staff(rs.getString("StaffID").trim(), rs.getString("StaffName").trim(), rs.getString("Gender").trim(), rs.getDate("BirthDate"), rs.getDate("HireDate"),
                        rs.getString("Address").trim(), rs.getString("Phone").trim(), rs.getString("Username"), rs.getString("Password").trim(), rs.getString("Email").trim(),
                        rs.getString("Status").trim());
            }
        } catch (SQLException ex) {
            Logger.getLogger(Process.class.getName()).log(Level.SEVERE, null, ex);
        }
        return staff;
    }

    public void searchName(String name, JTable jtable,String check) {
        DefaultTableModel defau = (DefaultTableModel) jtable.getModel();
        Connection con = null;
        boolean vl = false;
        boolean cl = false;
        try {
            con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433; databaseName=FurnitureSystem;integratedSecurity=true");
            
            PreparedStatement pre = con.prepareStatement("select * from Staffs where StaffName like concat('%',?,'%') ");
            
            pre.setString(1, name);
            
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                
                String id = rs.getString(1);
                String namee = rs.getString(2);
                String gender = rs.getString(3);
                
                Date local = rs.getDate(4);
                Date local1 = rs.getDate(5);
                
                String botday = String.valueOf(local);
                String heday = String.valueOf(local1);
                String adres = rs.getString(6);
                String phon = rs.getString(7);
                String user = rs.getString(8);
                String pass = rs.getString(9);
                String emai = rs.getString(10);
                String sta = rs.getString(11);
                
                defau.addRow(new String[]{id, namee, gender, botday, heday, adres, phon, user, pass, emai, sta});
                vl = true;
            }
            if (!vl) {
                PreparedStatement pre1 = con.prepareStatement("select * from Staffs where StaffID like concat('%',?,'%') ");
                
                pre1.setString(1, name);
                
                ResultSet rs1 = pre1.executeQuery();
                while (rs1.next()) {
                    
                    String id = rs1.getString(1);
                    String namee = rs1.getString(2);
                    String gender = rs1.getString(3);
                    
                    Date local = rs1.getDate(4);
                    Date local1 = rs1.getDate(5);
                    
                    String botday = String.valueOf(local);
                    String heday = String.valueOf(local1);
                    String adres = rs1.getString(6);
                    String phon = rs1.getString(7);
                    String user = rs1.getString(8);
                    String pass = rs1.getString(9);
                    String emai = rs1.getString(10);
                    String sta = rs1.getString(11);
                    
                    defau.addRow(new String[]{id, namee, gender, botday, heday, adres, phon, user, pass, emai, sta});
                    cl = true;
                }
            }
            if (!vl && !cl) {
                JOptionPane.showMessageDialog(null, check);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addStaff(String ten, String gioitinh, String diachi, String phone, String tk, String mk, String gmail, String Sta, JRadioButton j1, JRadioButton j2, JDateChooser jcho, JDateChooser jcho3, Date birt, Date birt1) {
        Connection con = null;

        birt = new java.sql.Date(jcho.getDate().getTime());
        birt1 = new java.sql.Date(jcho3.getDate().getTime());

        if (j1.isSelected()) {
            gioitinh = "Male";
        }
        if (j2.isSelected()) {
            gioitinh = "Female";
        }

        try {

            con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433; databaseName=FurnitureSystem;integratedSecurity=true");
            PreparedStatement pre = con.prepareStatement("insert into Staffs(StaffName,Gender,BirthDate,HireDate,Address,Phone,Username,Password,Email,Status)  values(?,?,?,?,?,?,?,?,?,?)");
            pre.setString(1, ten);
            pre.setString(2, gioitinh);
            pre.setDate(3, birt);
            pre.setDate(4, birt1);
            pre.setString(5, diachi);
            pre.setString(6, phone);
            pre.setString(7, tk);
            pre.setString(8, mk);
            pre.setString(9, gmail);
            pre.setString(10, Sta);

            int aa = pre.executeUpdate();
            System.out.println(aa);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void enab(JTable jtable2) {
        ListSelectionModel listSelectionModel = jtable2.getSelectionModel();
        listSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listSelectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int rows[] = jtable2.getSelectedRows();
                int cols[] = jtable2.getSelectedColumns();
                for (int i = 0; i < rows.length; i++) {
                    String val = String.valueOf(jtable2.getValueAt(rows[0], 0));
                    System.out.println(val);
                    jtable2.setValueAt("Enable", rows[0], 10);
                    Connection con;
                    try {
                        con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433; databaseName=FurnitureSystem;integratedSecurity=true");
                        PreparedStatement pre = con.prepareStatement("update Staffs set Status=? where StaffID=?");
                        pre.setString(1, "Enable");
                        pre.setString(2, val);
                        int aa = pre.executeUpdate();
                        System.out.println(aa);
                    } catch (Exception ea) {
                        ea.printStackTrace();
                    }
                    if (rows.length != 0) {
                        break;
                    }
                }

            }
        });
    }

    public void disA(JTable jatble) {

        ListSelectionModel listSelectionModel = jatble.getSelectionModel();
        listSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listSelectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int rows[] = jatble.getSelectedRows();
                int clos[] = jatble.getSelectedColumns();
                for (int i = 0; i < rows.length; i++) {
                    String val = String.valueOf(jatble.getValueAt(rows[0], 0));
                    System.out.println(val);
                    jatble.setValueAt("Disable", rows[0], 10);
                    Connection con;
                    try {
                        con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433; databaseName=FurnitureSystem;integratedSecurity=true");
                        PreparedStatement pre = con.prepareStatement("update Staffs set Status=? where StaffID=?");
                        pre.setString(1, "Disable");
                        pre.setString(2, val);
                        int aaa = pre.executeUpdate();
                        System.out.println(aaa);
                    } catch (Exception ee) {
                        ee.printStackTrace();
                    }
                    if (rows.length != 0) {
                        break;
                    }
                }

            }
        });

    }

    public void resetText() {

    }
    
    public void showList(JTable jyab) {
        DefaultTableModel defau = (DefaultTableModel) jyab.getModel();
        Connection con = null;
        boolean vl = false;
        try {
            con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433; databaseName=FurnitureSystem;integratedSecurity=true");

            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery("select * from Staffs");
            while (rs.next()) {

                String id = rs.getString(1);
                String namee = rs.getString(2);
                String gender = rs.getString(3);

                Date local = rs.getDate(4);
                Date local1 = rs.getDate(5);

                String botday = String.valueOf(local);
                String heday = String.valueOf(local1);
                String adres = rs.getString(6);
                String phon = rs.getString(7);
                String user = rs.getString(8);
                String pass = rs.getString(9);
                String emai = rs.getString(10);
                String sta = rs.getString(11);

                defau.addRow(new String[]{id, namee, gender, botday, heday, adres, phon, user, pass, emai, sta});

                vl = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
