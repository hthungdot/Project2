
package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
//     public static String connString = "jdbc:sqlserver://localhost;database=FurnitureSystem;user=sa;password=trucanh1709;";
     public static Connection getConnection() throws Exception {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//        String connectionUrl = "jdbc:sqlserver://localhost;database=FurnitureSystem;";//dùng trình duyệt mặc định localhost cổng 1433
//        String use = "sa";//tài khoản SQL
//        String pass = "sa";//Mật khẩu SQL
//        Connection con = DriverManager.getConnection(connectionUrl, use, pass);
        Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433; databaseName=FurnitureSystem;integratedSecurity=true");
        return con;
    }
     
}
