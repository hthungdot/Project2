package com.dao;

import com.model.Shipper;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;


public class Dao_Shippers {

    private static final Connection con = Dao_Login.conn;
    private static PreparedStatement pst;
    private static Statement st;
    private static ResultSet rs;
    private static Shipper shipper;

    public static Shipper getShipper(String shipperID) {
        String sql = "SELECT * FROM Shippers WHERE ShipperID = ?";
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, shipperID);
            rs = pst.executeQuery();
            while (rs.next()) {
                shipper = new Shipper(rs.getString("ShipperID").trim(), rs.getString("CompanyName").trim(), rs.getString("Phone").trim(), rs.getString("Status").trim());
            }
        } catch (SQLException ex) {
            Logger.getLogger(Process.class.getName()).log(Level.SEVERE, null, ex);
        }
        return shipper;
    }

    public static ArrayList getShippers() {
        String sql = "SELECT * FROM Shippers";
        ArrayList<Shipper> arr = new ArrayList<Shipper>();
        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                shipper = new Shipper(rs.getString("ShipperID").trim(), rs.getString("CompanyName").trim(), rs.getString("Phone").trim(), rs.getString("Status").trim());
                arr.add(shipper);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Process.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arr;
    }

    public void search(String tex, JTable jtable) {
        DefaultTableModel defau = (DefaultTableModel) jtable.getModel();
        Connection con;
        boolean bl = false;
        try {
            con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433; databaseName=FurnitureSystem;integratedSecurity=true");
            PreparedStatement pre = con.prepareStatement("select * from Shippers where CompanyName like concat('%',?,'%')");
            pre.setString(1, tex);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {

                String id = rs.getString(1);
                String com = rs.getString(2);
                String phon = rs.getString(3);
                String sta = rs.getString(4);

                defau.addRow(new String[]{id, com, phon, sta});
                bl = true;
            }
            if (!bl) {
                PreparedStatement pre1 = con.prepareStatement("select * from Shippers where ShipperID like concat('%',?,'%')");
                pre1.setString(1, tex);
                ResultSet rs1 = pre1.executeQuery();
                while (rs1.next()) {

                    String id = rs1.getString(1);
                    String com = rs1.getString(2);
                    String phon = rs1.getString(3);
                    String sta = rs1.getString(4);

                    defau.addRow(new String[]{id, com, phon, sta});
                    bl = true;
                }
            }
        } catch (Exception e) {
        }
    }
    
    public void addShip(String com, String phone) {

        Connection con;

        try {
            con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433; databaseName=FurnitureSystem;integratedSecurity=true");
            PreparedStatement pre = con.prepareStatement("insert into Shippers (CompanyName,Phone,Status) values(?,?,?)");
            pre.setString(1, com);
            pre.setString(2, phone);
            pre.setString(3, "Enable");
            int aaa = pre.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(JTable jtable) {
        ListSelectionModel listSelectionModel = jtable.getSelectionModel();
        listSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listSelectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int rows[] = jtable.getSelectedRows();
                int cols[] = jtable.getSelectedColumns();
                for (int i = 0; i < rows.length; i++) {
                    jtable.setValueAt("Disable", rows[0], 3);
                    String val = String.valueOf(jtable.getValueAt(rows[0], 0));
                    System.out.println(val);
                    Connection con;
                    try {
                        con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433; databaseName=FurnitureSystem;integratedSecurity=true");
                        PreparedStatement pre = con.prepareStatement("update Shippers set Status=? where ShipperID=?");
                        pre.setString(1, "Disable");
                        pre.setString(2, val);
                        int aaaa = pre.executeUpdate();
                        System.out.println(aaaa);
                    } catch (Exception eq) {
                        eq.printStackTrace();
                    }
                    if (rows.length != 0) {
                        break;
                    }
                }

            }
        });
    }

    public void update(JTable jtable) {
        ListSelectionModel listSelectionModel = jtable.getSelectionModel();
        listSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listSelectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int rows[] = jtable.getSelectedRows();
                int cols[] = jtable.getSelectedColumns();
                for (int i = 0; i < rows.length; i++) {
                    String val = String.valueOf(jtable.getValueAt(rows[0], 0));
                    jtable.setValueAt("Enable", rows[0], 3);
                    Connection con;
                    try {
                        con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433; databaseName=FurnitureSystem;integratedSecurity=true");
                        PreparedStatement pre = con.prepareStatement("update Shippers set Status=? where ShipperID=?");
                        pre.setString(1, "Enable");
                        pre.setString(2, val);
                        int aaaa = pre.executeUpdate();
                    } catch (Exception eq) {
                        eq.printStackTrace();
                    }
                    if (rows.length != 0) {
                        break;
                    }
                }

            }
        });
    }
    
    public void showList(JTable jtable) {
        DefaultTableModel defau = (DefaultTableModel) jtable.getModel();
        Connection con;
        boolean bl = false;
        try {
            con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433; databaseName=FurnitureSystem;integratedSecurity=true");
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery("select * from Shippers");
            while (rs.next()) {

                String id = rs.getString(1);
                String com = rs.getString(2);
                String phon = rs.getString(3);
                String sta = rs.getString(4);

                defau.addRow(new String[]{id, com, phon, sta});
                bl = true;
            }
        } catch (Exception e) {
        }
    }
}
