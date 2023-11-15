package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;


public class Dao_Reports {
    public static JFreeChart createChart() {
        JFreeChart barChart = ChartFactory.createBarChart(
                "BIỂU ĐỒ DOANH THU",
                "Staffs", "Doanh Thu",
                createDataset(), PlotOrientation.VERTICAL, false, false, false);
        return barChart;
    }

    private static CategoryDataset createDataset() {
        Connection con;
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();        
        try {
            con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433; databaseName=FurnitureSystem;integratedSecurity=true");
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery("select a.StaffName,Sum( c.UnitPrice) from Staffs a join Orders b on a.StaffID = b.StaffID join [Order Details] c on b.OrderID = c.OrderID group by a.StaffName");
            while (rs.next()) {
                double doanh = rs.getDouble(2);
                String doanhthu = "Doanh Thu";
                String name = rs.getString(1);
                
                dataset.addValue(doanh, doanhthu, name);

            }
        } catch (Exception e) {
        }


        return dataset;
    }

    public void Create() {
        ChartPanel chartPanel = new ChartPanel(createChart());
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        JDialog frame = new JDialog();
        frame.add(chartPanel);
        frame.setTitle("Biểu đồ JFreeChart trong Java Swing");
        frame.setSize(1400, 400);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }
    
    public void createStaff(JTable jtab) {
        jtab.removeAll();
        Object[] obj = new Object[]{"Staff ID", "Staff Name", "Total Amount"};
        DefaultTableModel defau = new DefaultTableModel(obj, 0);
        jtab.setModel(defau);
        Connection con;
        try {
            con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433; databaseName=FurnitureSystem;integratedSecurity=true");
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery("select a.StaffID,a.StaffName,Sum( c.UnitPrice) as TotalPrice from Staffs a join Orders b on a.StaffID = b.StaffID join [Order Details] c on b.OrderID = c.OrderID group by a.StaffName,a.StaffID");
            while (rs.next()) {                
                String id = rs.getString(1);
                String name = rs.getString(2);
                String total = String.valueOf(rs.getDouble(3));
                defau.addRow(new String[]{id, name, total});
            }
        } catch (Exception e) {
        }
        
    }
}
