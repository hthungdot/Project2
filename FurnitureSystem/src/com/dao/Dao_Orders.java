package com.dao;

import com.model.Order;
import com.model.OrderDetails;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Dao_Orders {

    private static final Connection con = Dao_Login.conn;
    private static PreparedStatement pst;
    private static Statement st;
    private static ResultSet rs;
    private static Order order;
    private static OrderDetails orderDetails;

    public static ArrayList getOrders() {
        String sql = "SELECT * FROM Orders WHERE Status = 'Enable'";
        ArrayList<Order> arr = new ArrayList<Order>();
        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                order = new Order(rs.getString("OrderID").trim(),
                        rs.getString("CustomerID").trim(),
                        rs.getString("StaffID").trim(),
                        rs.getString("PromotionID").trim(),
                        rs.getString("ShipperID").trim(),
                        rs.getDate("OrderDate"),
                        rs.getDouble("TotalAmount"),
                        rs.getString("PaymentMethod").trim(),
                        rs.getString("Status").trim());
                arr.add(order);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Process.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arr;
    }
    
    public static ArrayList getOrders(Date date1, Date date2, String type) {
        String sql = "SELECT * FROM Orders WHERE OrderDate BETWEEN ? AND ? AND Status = 'Enable'";
        ArrayList<Order> arr = new ArrayList<Order>();
        try {
            pst = con.prepareStatement(sql);
            pst.setDate(1, date1);
            pst.setDate(2, date2);
            rs = pst.executeQuery();
            while (rs.next()) {
                if (type.equals("daily")) {
                    order = new Order(rs.getString("OrderID").trim(),
                        rs.getString("CustomerID").trim(),
                        rs.getString("StaffID").trim(),
                        rs.getString("PromotionID").trim(),
                        rs.getString("ShipperID").trim(),
                        rs.getTime("OrderDate"),
                        rs.getDouble("TotalAmount"),
                        rs.getString("PaymentMethod").trim(),
                        rs.getString("Status").trim());
                } else if (type.equals("notdaily")) {
                    order = new Order(rs.getString("OrderID").trim(),
                        rs.getString("CustomerID").trim(),
                        rs.getString("StaffID").trim(),
                        rs.getString("PromotionID").trim(),
                        rs.getString("ShipperID").trim(),
                        rs.getDate("OrderDate"),
                        rs.getDouble("TotalAmount"),
                        rs.getString("PaymentMethod").trim(),
                        rs.getString("Status").trim());
                }
                arr.add(order);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Process.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arr;
    }
    
//    public static ArrayList getDates(Date date1, Date date2) {
//        String sql = "SELECT OrderDate FROM Orders WHERE OrderDate BETWEEN ? AND ?";
//        ArrayList arr = new ArrayList();
//        try {
//            pst = con.prepareStatement(sql);
//            pst.setDate(1, date1);
//            pst.setDate(2, date2);
//            rs = pst.executeQuery();
//            while (rs.next()) {
//                rs.getDate(1);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(Dao_Orders.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//    }

    public static ArrayList getOrdersSearch() {
        String sql = "SELECT * FROM Orders";
        ArrayList<Order> arr = new ArrayList<Order>();
        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                order = new Order(rs.getString("OrderID").trim(),
                        rs.getString("CustomerID").trim(),
                        rs.getString("StaffID").trim(),
                        rs.getString("PromotionID").trim(),
                        rs.getString("ShipperID").trim(),
                        rs.getDate("OrderDate"),
                        rs.getDouble("TotalAmount"),
                        rs.getString("PaymentMethod").trim(),
                        rs.getString("Status").trim());
                arr.add(order);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Process.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arr;
    }

    public static Order getOrder(String orderID) {
        String sql = "SELECT * FROM Orders WHERE OrderID = ?";
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, orderID);
            rs = pst.executeQuery();
            while (rs.next()) {
                order = new Order(rs.getString("OrderID").trim(),
                        rs.getString("CustomerID").trim(),
                        rs.getString("StaffID").trim(),
                        rs.getString("PromotionID").trim(),
                        rs.getString("ShipperID").trim(),
                        rs.getObject("OrderDate"),
                        rs.getDouble("TotalAmount"),
                        rs.getString("PaymentMethod").trim(),
                        rs.getString("Status").trim());
            }
        } catch (SQLException ex) {
            Logger.getLogger(Process.class.getName()).log(Level.SEVERE, null, ex);
        }
        return order;
    }

    public static ArrayList getOrderDetails(String orderID) {
        String sql = "SELECT * FROM [Order Details] WHERE OrderID = ?";
        ArrayList<OrderDetails> arr = new ArrayList<OrderDetails>();
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, orderID);
            rs = pst.executeQuery();
            while (rs.next()) {
                orderDetails = new OrderDetails(rs.getString("OrderID"), rs.getString("ProductID"), rs.getDouble("UnitPrice"), rs.getInt("Quantity"));
                arr.add(orderDetails);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Process.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arr;
    }

    public static OrderDetails getOrderDetail(String orderID, String productID) {
        String sql = "SELECT * FROM [Order Details] WHERE OrderID = ? AND ProductID = ?";
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, orderID);
            pst.setString(2, productID);
            rs = pst.executeQuery();
            while (rs.next()) {
                orderDetails = new OrderDetails(rs.getString("OrderID"), rs.getString("ProductID"), rs.getDouble("UnitPrice"), rs.getInt("Quantity"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Process.class.getName()).log(Level.SEVERE, null, ex);
        }
        return orderDetails;
    }

    public static boolean insertOrder(String customerID, String staffID, String promotionID, String shipperID, Object orderDate, double totalAmount, String payment, String status) {
        boolean test = true;
        String sql = "INSERT INTO Orders (CustomerID, StaffID, PromotionID, ShipperID, OrderDate, TotalAmount, PaymentMethod, Status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, customerID);
            pst.setString(2, staffID);
            pst.setString(3, promotionID);
            pst.setString(4, shipperID);
            pst.setObject(5, orderDate);
            pst.setDouble(6, totalAmount);
            pst.setString(7, payment);
            pst.setString(8, status);
            int row = pst.executeUpdate();
            if (row == 0) {
                test = false;
                return test;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Process.class.getName()).log(Level.SEVERE, null, ex);
        }
        return test;
    }

    public static boolean updateOrder(String orderID, String customerID, String staffID, String promotionID, String shipperID, Object orderDate, double totalAmount, String payment, String status) {
        boolean test = true;
        String sql = "UPDATE Orders SET CustomerID = ?, StaffID = ?, PromotionID = ?, ShipperID = ?, OrderDate = ?, TotalAmount = ?, PaymentMethod = ?, Status = ? WHERE OrderID = ?";
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, customerID);
            pst.setString(2, staffID);
            pst.setString(3, promotionID);
            pst.setString(4, shipperID);
            pst.setObject(5, orderDate);
            pst.setDouble(6, totalAmount);
            pst.setString(7, payment);
            pst.setString(8, status);
            pst.setString(9, orderID);
            int row = pst.executeUpdate();
            if (row == 0) {
                test = false;
                return test;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Process.class.getName()).log(Level.SEVERE, null, ex);
        }
        return test;
    }

    public static boolean deleteOrder(String orderID) {
        boolean test = true;
        String sql = "UPDATE Orders SET Status = 'Disable' WHERE OrderID = ?";
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, orderID);
            int row = pst.executeUpdate();
            if (row == 0) {
                test = false;
                return test;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Process.class.getName()).log(Level.SEVERE, null, ex);
        }
        return test;
    }

    public static boolean insertOrderDetails(String orderID, String productID, double unitPrice, int quantity) {
        boolean test = true;
        String sql = "INSERT INTO [Order Details] VALUES (?, ?, ?, ?)";
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, orderID);
            pst.setString(2, productID);
            pst.setDouble(3, unitPrice);
            pst.setInt(4, quantity);
            int row = pst.executeUpdate();
            if (row == 0) {
                test = false;
                return test;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Dao_Orders.class.getName()).log(Level.SEVERE, null, ex);
        }
        return test;
    }

    public static boolean updateOrderDetails(String orderID, String productID, double unitPrice, int quantity) {
        boolean test = true;
        String sql = "UPDATE [Order Details] SET UnitPrice = ?, Quantity = ? WHERE OrderID = ? AND ProductID = ?";
        String sql1 = "DELETE FROM [Order Details] WHERE OrderID = ? AND ProductID = ?";
        if (quantity > 0) {
            try {
                pst = con.prepareStatement(sql);
                pst.setDouble(1, unitPrice);
                pst.setInt(2, quantity);
                pst.setString(3, orderID);
                pst.setString(4, productID);
                int row = pst.executeUpdate();
                if (row == 0) {
                    test = false;
                    return test;
                }
            } catch (SQLException ex) {
                Logger.getLogger(Dao_Orders.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                pst = con.prepareStatement(sql1);
                pst.setString(1, orderID);
                pst.setString(2, productID);
                int row = pst.executeUpdate();
                if (row == 0) {
                    test = false;
                    return test;
                }
            } catch (SQLException ex) {
                Logger.getLogger(Dao_Orders.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return test;
    }

    public static boolean deleteOrderDetails(String orderID, String productID) {
        boolean test = true;
        String sql = "DELETE FROM [Order Details] WHERE OrderID = ? AND ProductID = ?";
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, orderID);
            pst.setString(2, productID);
            int row = pst.executeUpdate();
            if (row == 0) {
                test = false;
                return test;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Dao_Orders.class.getName()).log(Level.SEVERE, null, ex);
        }
        return test;
    }

    public static boolean updateOrderIF(String orderID, String promotionID, double totalAmount) {
        boolean test = true;
        String sql = "UPDATE Orders SET PromotionID = ?, TotalAmount = ? WHERE orderID = ?";
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, promotionID);
            pst.setDouble(2, totalAmount);
            pst.setString(3, orderID);
            int row = pst.executeUpdate();
            if (row == 0) {
                test = false;
                return test;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Dao_Orders.class.getName()).log(Level.SEVERE, null, ex);
        }
        return test;
    }
}
