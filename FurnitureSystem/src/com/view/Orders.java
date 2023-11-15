
package com.view;

import com.dao.Common;
import com.dao.Dao_Customer;
import com.dao.Dao_Login;
import com.dao.Dao_Orders;
import com.dao.Dao_Products;
import com.dao.Dao_Promotion;
import com.dao.Dao_Shippers;
import com.dao.Dao_Staff;
import com.model.Customer;
import com.model.Order;
import com.model.OrderDetails;
import com.model.Product;
import com.model.Promotion;
import com.model.Shipper;
import java.awt.Color;
import java.awt.Font;
import java.io.InputStream;
import java.sql.Date;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;


public class Orders extends javax.swing.JPanel {

    
    private static Customer customer = new Customer();
    private static Product product = new Product();
    private static ArrayList arrProduct;
    private final static NumberFormat currency = NumberFormat.getCurrencyInstance(new Locale("en", "US"));
    private static Order order;
    private static OrderDetails orderDetail;
    private final String notification = "Notification";
//    private final static Font FONT_SIZE_14_BOLD = new Font(Font.DIALOG, Font.BOLD, 14);

    public Orders() {
        initComponents();
        customer.setCustomerName("");
        product.setProductName("");
        getOrders(Dao_Orders.getOrders());
        java.util.Date date = new java.util.Date();
        dcrOrderDate.setDate(date);
        loadShipper();
        disableComponent();
    }

    public static void getCustomer(String id, String name) {
        customer.setCustomerID(id);
        customer.setCustomerName(name);
    }

    public static void getProduct(String id, String name, double price, int quantity) {
        product = new Product();
        product.setProductID(id);
        product.setProductName(name);
        product.setSellPrice(price);
        product.setQuantity(quantity);
    }

    private ArrayList search() {
        ArrayList arr;
        ArrayList<Object> arrOrder = new ArrayList<Object>();
        arr = Dao_Orders.getOrdersSearch();
        for (int i = 0; i < arr.size(); i++) {
            String orderid = ((Order) arr.get(i)).getOrderID().trim();
            String date = ((Order) arr.get(i)).getOrderDate().toString().trim();
            String name = Dao_Customer.getCustomerByID(((Order) arr.get(i)).getCustomerID()).getCustomerName().toUpperCase().trim();
            String staff = Dao_Staff.getStaff(((Order) arr.get(i)).getStaffID()).getStaffName().toUpperCase().trim();
            if (orderid.contains(txtSearch.getText().toUpperCase()) || date.contains(txtSearch.getText().toUpperCase()) || name.contains(txtSearch.getText().toUpperCase()) || staff.contains(txtSearch.getText().toUpperCase())) {
                arrOrder.add(arr.get(i));
            }
        }
        return arrOrder;
    }

    private void getOrders(ArrayList arr) {
        tblOrders.removeAll();
        Object[] obj = new Object[]{"Order ID", "Customer Name", "Staff Name", "Order Date", "Shipper", "Total Amount", "Discount", "Payment Method", "Status"};
        DefaultTableModel tableModel = new DefaultTableModel(obj, 0);
        tblOrders.setModel(tableModel);

        for (int i = (arr.size() - 1); i >= 0; i--) {
            Object[] objOrder = new Object[9];
            objOrder[0] = ((Order) arr.get(i)).getOrderID();
            objOrder[1] = Dao_Customer.getCustomerByID(((Order) arr.get(i)).getCustomerID()).getCustomerName();
            objOrder[2] = Dao_Staff.getStaff(((Order) arr.get(i)).getStaffID()).getStaffName();
            objOrder[3] = ((Order) arr.get(i)).getOrderDate();
            objOrder[4] = Dao_Shippers.getShipper(((Order) arr.get(i)).getShipperID()).getCompanyName();
            objOrder[5] = ((Order) arr.get(i)).getTotalAmount();
            objOrder[6] = Dao_Promotion.search(((Order) arr.get(i)).getPromotionID()).getDiscount();
            objOrder[7] = ((Order) arr.get(i)).getPaymentMethod();
            objOrder[8] = ((Order) arr.get(i)).getStatus();
            tableModel.addRow(objOrder);
        }
    }

    private void getOrderDetails() {
        orderDetail = null;
        Object[] obj = new Object[]{"Order ID", "Product ID", "Product Name", "Unit Price", "Quantity", "Total Amount"};
        DefaultTableModel tableOrderDetails = new DefaultTableModel(obj, 0);
        tblProducts.setModel(tableOrderDetails);
        ArrayList arr = Dao_Orders.getOrderDetails(order.getOrderID());
        if (arr.size() > 0) {
            for (int i = 0; i < arr.size(); i++) {
                Object[] objProduct = new Object[6];
                objProduct[0] = order.getOrderID();
                objProduct[1] = ((OrderDetails) arr.get(i)).getProductID();
                objProduct[2] = Dao_Products.search(((OrderDetails) arr.get(i)).getProductID()).getProductName();
                objProduct[3] = currency.format(((OrderDetails) arr.get(i)).getUnitPrice());
                objProduct[4] = ((OrderDetails) arr.get(i)).getQuantity();
                objProduct[5] = currency.format((((OrderDetails) arr.get(i)).getUnitPrice() * ((OrderDetails) arr.get(i)).getQuantity()));
                tableOrderDetails.addRow(objProduct);
            }
        }
    }

    private void loadOrderIF() {
        int click = tblOrders.getSelectedRow();
        TableModel model = tblOrders.getModel();
        order = Dao_Orders.getOrder(model.getValueAt(click, 0).toString());
        txtCustomerName.setText(model.getValueAt(click, 1).toString());
        customer.setCustomerName(model.getValueAt(click, 1).toString());
        java.util.Date date;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(model.getValueAt(click, 3).toString());
            dcrOrderDate.setDate(date);
        } catch (ParseException ex) {
            Logger.getLogger(Orders.class.getName()).log(Level.SEVERE, null, ex);
        }

        cbxShipper.setSelectedItem(order.getShipperID() + " (" + Dao_Shippers.getShipper(order.getShipperID()).getCompanyName() + ")");
        if (model.getValueAt(click, 7).toString().equals("Money")) {
            cbxPayment.setSelectedItem("Money");
        } else {
            cbxPayment.setSelectedItem("ATM");
        }
        lblSubToTal.setText(currency.format(intoMoney()));
        if (!"PM006".equals(order.getPromotionID())) {
            chkPromotion.setSelected(true);
            lblPromotionStatus.setText("Discount: " + currency.format(Dao_Promotion.search(order.getPromotionID()).getDiscount() * intoMoney()));
            lblGrandTotal.setText(currency.format(intoMoney() - intoMoney() * Dao_Promotion.search(order.getPromotionID()).getDiscount()));
        } else {
            chkPromotion.setSelected(false);
            lblPromotionStatus.setText("");
            lblGrandTotal.setText(currency.format(intoMoney() - intoMoney() * Dao_Promotion.search(order.getPromotionID()).getDiscount()));
        }
    }

    private void loadOrderIF(Order order) {
        txtCustomerName.setText(Dao_Customer.getCustomerByID(order.getCustomerID()).getCustomerName());
        customer.setCustomerName(Dao_Customer.getCustomerByID(order.getCustomerID()).getCustomerName());
        java.util.Date date;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(order.getOrderDate().toString());
            dcrOrderDate.setDate(date);
        } catch (ParseException ex) {
            Logger.getLogger(Orders.class.getName()).log(Level.SEVERE, null, ex);
        }

        cbxShipper.setSelectedItem(order.getShipperID() + " (" + Dao_Shippers.getShipper(order.getShipperID()).getCompanyName() + ")");
        if (order.getPaymentMethod().equals("Money")) {
            cbxPayment.setSelectedItem("Money");
        } else {
            cbxPayment.setSelectedItem("ATM");
        }
        lblSubToTal.setText(currency.format(intoMoney()));
        if (!"PM006".equals(order.getPromotionID())) {
            chkPromotion.setSelected(true);
            lblPromotionStatus.setText("Discount: " + currency.format(Dao_Promotion.search(order.getPromotionID()).getDiscount() * intoMoney()));
            lblGrandTotal.setText(currency.format(intoMoney() - intoMoney() * Dao_Promotion.search(order.getPromotionID()).getDiscount()));
        } else {
            chkPromotion.setSelected(false);
            lblPromotionStatus.setText("");
            lblGrandTotal.setText(currency.format(intoMoney() - intoMoney() * Dao_Promotion.search(order.getPromotionID()).getDiscount()));
        }
    }

    private void loadProductIF() {
        int click = tblProducts.getSelectedRow();
        TableModel model = tblProducts.getModel();
        orderDetail = Dao_Orders.getOrderDetail(model.getValueAt(click, 0).toString(), model.getValueAt(click, 1).toString());
        txtProductName.setText(model.getValueAt(click, 2).toString());
        product.setProductName(model.getValueAt(click, 2).toString());
        txtProductPrice.setText(model.getValueAt(click, 3).toString());
        txtProductQuantity.setText(model.getValueAt(click, 4).toString());
        txtTotalAmount.setText(model.getValueAt(click, 5).toString());
        lblStatus.setText("");
    }

    private void disableComponent() {
        txtCustomerName.setEnabled(false);
        dcrOrderDate.setEnabled(false);
        cbxShipper.setEnabled(false);
        cbxPayment.setEnabled(false);
        chkPromotion.setEnabled(false);
        txtProductName.setEnabled(false);
        btnSearchProduct.setEnabled(false);
        txtProductPrice.setEnabled(false);
        txtProductQuantity.setEnabled(false);
        txtTotalAmount.setEnabled(false);
    }

    private void enableComponent() {
        cbxShipper.setEnabled(true);
        cbxPayment.setEnabled(true);
    }

    private void refresh() {
        Object[] obj = new Object[]{"Order ID", "Product ID", "Product Name", "Unit Price", "Quantity", "Total Amount"};
        DefaultTableModel tableOrderDetails = new DefaultTableModel(obj, 0);
        tblProducts.setModel(tableOrderDetails);
        txtCustomerName.setText("");
        java.util.Date date = new java.util.Date();
        dcrOrderDate.setDate(date);
        cbxShipper.removeAllItems();
        loadShipper();
        chkPromotion.setSelected(false);
        lblSubToTal.setText("$0");
        lblGrandTotal.setText("$0");
        lblStatus.setText("");
        lblPromotionStatus.setText("");
        txtProductName.setText("");
        txtProductPrice.setText("");
        txtProductQuantity.setText("");
        txtTotalAmount.setText("");
        customer = new Customer();
        product = new Product();
        order = null;
        getOrders(Dao_Orders.getOrders());
        disableComponent();
    }

    private void loadShipper() {
        cbxShipper.removeAllItems();
        ArrayList arrShipper = Dao_Shippers.getShippers();
        for (int i = 0; i < arrShipper.size(); i++) {
            cbxShipper.addItem(((Shipper) arrShipper.get(i)).getShipperID() + " (" + ((Shipper) arrShipper.get(i)).getCompanyName() + ")");
        }
    }

    private String checkAddOrder() {
        String message = "";
        if (dcrOrderDate.getDate() == null) {
            message += "You have not selected Order Date" + "\n";
        }
        return message;
    }

    private void loadProductInfo() {
        txtProductPrice.setText(currency.format(product.getSellPrice()));
        lblStatus.setText("This item has" + " " + product.getQuantity() + " " + "product(s) left");
    }

    private String checkAddProduct() {
        String message = "";
        if (txtProductQuantity.getText().trim().equals("")) {
            message += "You have not entered the product quantity" + "\n";
        }
        return message;
    }

    private String cutChar(String arry) {
        return arry.replaceAll("\\D+", "");
    }

    private void refreshProduct() {
        txtProductName.setText("");
        product = new Product();
        txtProductPrice.setText("");
        txtProductQuantity.setText("");
        txtTotalAmount.setText("");
        lblStatus.setText("");
    }

    private boolean checkProductID(String productID) {
        boolean valid = true;
        arrProduct = Dao_Orders.getOrderDetails(order.getOrderID());
        if (arrProduct.isEmpty()) {
            valid = true;
        } else {
            for (int i = 0; i < arrProduct.size(); i++) {
                if (((OrderDetails) arrProduct.get(i)).getProductID().equals(productID)) {
                    valid = false;
                    break;
                }
            }
        }
        return valid;
    }

    private boolean addProduct() {
        boolean valid = false;
        if (checkProductQuantity()) {
            if (chkPromotion.isSelected()) {
                valid = add();
                lblPromotionStatus.setText("Discount" + ": " + currency.format(Dao_Promotion.search(order.getPromotionID()).getDiscount() * intoMoney()));
            } else {
                if (!checkPromotion("insert").equals("PM006")) {
                    valid = add();
                    lblPromotionStatus.setText("Discount" + ": " + currency.format(Dao_Promotion.search(order.getPromotionID()).getDiscount() * intoMoney()));
                } else {
                    valid = add();
                    if (!checkPromotionDate().equals("")) {
                        lblPromotionStatus.setText("Buy" + " " + currency.format(Dao_Promotion.search(checkPromotionDate()).getAmountOfMoney() - order.getTotalAmount()) + " " + "more to get discount");
                    } else {
                        lblPromotionStatus.setText("");
                    }
                }
            }
        } else {
            Common.notify("Not enough product quantity", notification, JOptionPane.WARNING_MESSAGE);
        }
        return valid;
    }

    private boolean add() {
        boolean p, od, o, valid = false;
        od = Dao_Orders.insertOrderDetails(order.getOrderID(), product.getProductID(), product.getSellPrice(), Integer.parseInt(txtProductQuantity.getText().trim()));
        p = Dao_Products.updateProductOrder(product.getProductID(), Dao_Products.search(product.getProductID()).getQuantity() - Integer.parseInt(txtProductQuantity.getText().trim()));
        o = Dao_Orders.updateOrderIF(order.getOrderID(), order.getPromotionID(), intoMoney());
        if (od && p && o) {
            valid = true;
        }
        DefaultTableModel tableProduct = (DefaultTableModel) tblProducts.getModel();
        Object[] objectPro = new Object[6];
        objectPro[0] = order.getOrderID();
        objectPro[1] = product.getProductID();
        objectPro[2] = txtProductName.getText().trim();
        objectPro[3] = txtProductPrice.getText().trim();
        objectPro[4] = txtProductQuantity.getText().trim();
        objectPro[5] = txtTotalAmount.getText().trim();
        tableProduct.addRow(objectPro);
        getOrders(Dao_Orders.getOrders());
        order = Dao_Orders.getOrder(order.getOrderID());
        loadOrderIF(order);
        return valid;
    }

    private double intoMoney() {
        double money = 0;
        arrProduct = Dao_Orders.getOrderDetails(order.getOrderID());
        for (int i = 0; i < arrProduct.size(); i++) {
            money = money + ((OrderDetails) arrProduct.get(i)).getQuantity() * ((OrderDetails) arrProduct.get(i)).getUnitPrice();
        }
        return money;
    }

    private boolean checkProductQuantity() {
        boolean enough;
        if (Dao_Products.search(product.getProductID()).getQuantity() >= Integer.parseInt(txtProductQuantity.getText().trim())) {
            enough = true;
        } else {
            enough = false;
        }
        return enough;
    }

    private String checkPromotion(String status) {
        String promotionID = "PM006";
        if (status.equals("insert")) {
            Instant instant = dcrOrderDate.getDate().toInstant();
            ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
            LocalDate date = zdt.toLocalDate();
            Date orderDate = Date.valueOf(date);
            ArrayList arr = Dao_Promotion.searchAll();
            for (int i = 0; i < arr.size(); i++) {
                if (((Promotion) arr.get(i)).getPromotionDate().compareTo(orderDate) == 0 && ((Promotion) arr.get(i)).getQuantity() > 0 && (order.getTotalAmount() + Integer.parseInt(txtProductQuantity.getText().trim()) * product.getSellPrice()) > ((Promotion) arr.get(i)).getAmountOfMoney()) {
                    promotionID = ((Promotion) arr.get(i)).getPromotionID();
                    Dao_Orders.updateOrderIF(order.getOrderID(), promotionID, intoMoney());
                    order = Dao_Orders.getOrder(order.getOrderID());
                    chkPromotion.setSelected(true);
                    Dao_Promotion.updatePromotionOrder(1, promotionID);
                    break;
                }
            }
        } else if (status.equals("update")) {
            Instant instant = dcrOrderDate.getDate().toInstant();
            ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
            LocalDate date = zdt.toLocalDate();
            Date orderDate = Date.valueOf(date);
            ArrayList arr = Dao_Promotion.searchAll();
            for (int i = 0; i < arr.size(); i++) {
                if (orderDetail.getQuantity() < Integer.parseInt(txtProductQuantity.getText().trim())) {
                    if (((Promotion) arr.get(i)).getPromotionDate().compareTo(orderDate) == 0 && ((Promotion) arr.get(i)).getQuantity() > 0 && (order.getTotalAmount() + (Integer.parseInt(txtProductQuantity.getText().trim()) - orderDetail.getQuantity()) * orderDetail.getUnitPrice()) > ((Promotion) arr.get(i)).getAmountOfMoney()) {
                        promotionID = ((Promotion) arr.get(i)).getPromotionID();
                        Dao_Orders.updateOrderIF(order.getOrderID(), promotionID, intoMoney());
                        order = Dao_Orders.getOrder(order.getOrderID());
                        chkPromotion.setSelected(true);
                        Dao_Promotion.updatePromotionOrder(1, promotionID);
                        break;
                    }
                }
            }
        }
        return promotionID;
    }

    private String checkPromotionDate() {
        String promotionID = "";
        Instant instant = dcrOrderDate.getDate().toInstant();
        ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
        LocalDate date = zdt.toLocalDate();
        Date orderDate = Date.valueOf(date);
        ArrayList arr = Dao_Promotion.searchAll();
        for (int i = 0; i < arr.size(); i++) {
            if (((Promotion) arr.get(i)).getPromotionDate().compareTo(orderDate) == 0 && ((Promotion) arr.get(i)).getQuantity() > 0) {
                promotionID = ((Promotion) arr.get(i)).getPromotionID();
                break;
            }
        }
        return promotionID;
    }

    private boolean updateProduct() {
        boolean valid = false;
        if (checkUpdateQuantity()) {
            if (chkPromotion.isSelected()) {
                if (!checkUpdatePromotion().equals("PM006")) {
                    valid = update();
                    lblPromotionStatus.setText("Discount" + ": " + currency.format(Dao_Promotion.search(order.getPromotionID()).getDiscount() * intoMoney()));
                } else {
                    valid = update();
                    if (!checkPromotionDate().equals("")) {
                        lblPromotionStatus.setText("Buy" + " " + currency.format(Dao_Promotion.search(checkPromotionDate()).getAmountOfMoney() - order.getTotalAmount()) + " " + "more to get discount");
                    } else {
                        lblPromotionStatus.setText("");
                    }
                }
            } else {
                if (!checkPromotion("update").equals("PM006")) {
                    valid = update();
                    lblPromotionStatus.setText("Discount" + ": " + currency.format(Dao_Promotion.search(order.getPromotionID()).getDiscount() * intoMoney()));
                } else {
                    valid = update();
                    if (!checkPromotionDate().equals("")) {
                        lblPromotionStatus.setText("Buy" + " " + currency.format(Dao_Promotion.search(checkPromotionDate()).getAmountOfMoney() - order.getTotalAmount()) + " " + "more to get discount");
                    } else {
                        lblPromotionStatus.setText("");
                    }
                }
            }
        } else {
            Common.notify("Not enough product quantity", notification, JOptionPane.WARNING_MESSAGE);
        }
        return valid;
    }

    private boolean checkUpdateQuantity() {
        boolean enough = true;
        if (orderDetail.getQuantity() < Integer.parseInt(txtProductQuantity.getText().trim())) {
            if (Dao_Products.search(orderDetail.getProductID()).getQuantity() >= (Integer.parseInt(txtProductQuantity.getText().trim())) - orderDetail.getQuantity()) {
                enough = true;
            } else {
                enough = false;
            }
        }
        return enough;
    }

    private int getUpdateQuantity() {
        int quantity;
        if (orderDetail.getQuantity() < Integer.parseInt(txtProductQuantity.getText().trim())) {
            quantity = Dao_Products.search(orderDetail.getProductID()).getQuantity() - (Integer.parseInt(txtProductQuantity.getText().trim()) - orderDetail.getQuantity());
        } else {
            quantity = Dao_Products.search(orderDetail.getProductID()).getQuantity() + (orderDetail.getQuantity() - Integer.parseInt(txtProductQuantity.getText().trim()));
        }
        return quantity;
    }

    private boolean update() {
        boolean p, od, o, valid = false;
        p = Dao_Products.updateProductOrder(orderDetail.getProductID(), getUpdateQuantity());
        od = Dao_Orders.updateOrderDetails(order.getOrderID(), orderDetail.getProductID(), orderDetail.getUnitPrice(), Integer.parseInt(txtProductQuantity.getText().trim()));
        o = Dao_Orders.updateOrderIF(order.getOrderID(), order.getPromotionID(), intoMoney());
        if (p && od && o) {
            valid = true;
        }
        getOrders(Dao_Orders.getOrders());
        getOrderDetails();
        order = Dao_Orders.getOrder(order.getOrderID());
        loadOrderIF(order);
        refreshProduct();
        return valid;
    }

    private String checkUpdatePromotion() {
        String promotionID = Dao_Orders.getOrder(orderDetail.getOrderID()).getPromotionID();
        Instant instant = dcrOrderDate.getDate().toInstant();
        ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
        LocalDate date = zdt.toLocalDate();
        Date orderDate = Date.valueOf(date);
        if (Dao_Promotion.search(promotionID).getPromotionDate().compareTo(orderDate) == 0 && (intoMoney() - (orderDetail.getQuantity() - Integer.parseInt(txtProductQuantity.getText().trim())) * orderDetail.getUnitPrice()) < Dao_Promotion.search(promotionID).getAmountOfMoney()) {
            Dao_Promotion.updatePromotionOrder(2, promotionID);
            promotionID = "PM006";
            Dao_Orders.updateOrderIF(order.getOrderID(), promotionID, intoMoney());
            chkPromotion.setSelected(false);
            order = Dao_Orders.getOrder(order.getOrderID());
        }
        return promotionID;
    }

    private boolean deleteProduct() {
        boolean valid;
        if (chkPromotion.isSelected()) {
            if (!checkUpdatePromotion().equals("PM006")) {
                valid = delete();
                lblPromotionStatus.setText("Discount" + ": " + currency.format(Dao_Promotion.search(order.getPromotionID()).getDiscount() * intoMoney()));
            } else {
                valid = delete();
                if (!checkPromotionDate().equals("")) {
                    lblPromotionStatus.setText("Buy" + " " + currency.format(Dao_Promotion.search(checkPromotionDate()).getAmountOfMoney() - order.getTotalAmount()) + " " + "more to get discount");
                } else {
                    lblPromotionStatus.setText("");
                }
            }
        } else {
            valid = delete();
            if (!checkPromotionDate().equals("")) {
                lblPromotionStatus.setText("Buy" + " " + currency.format(Dao_Promotion.search(checkPromotionDate()).getAmountOfMoney() - order.getTotalAmount()) + " " + "more to get discount");
            } else {
                lblPromotionStatus.setText("");
            }

        }
        return valid;
    }

    private boolean delete() {
        boolean p, od, o, valid = false;
        p = Dao_Products.updateProductOrder(orderDetail.getProductID(), Dao_Products.search(orderDetail.getProductID()).getQuantity() + orderDetail.getQuantity());
        od = Dao_Orders.deleteOrderDetails(order.getOrderID(), orderDetail.getProductID());
        o = Dao_Orders.updateOrderIF(order.getOrderID(), order.getPromotionID(), intoMoney());
        if (p && od && o) {
            valid = true;
        }
        getOrders(Dao_Orders.getOrders());
        getOrderDetails();
        order = Dao_Orders.getOrder(order.getOrderID());
        loadOrderIF(order);
        refreshProduct();
        return valid;
    }

    private void invoice(String orderID) {
        try {

            Hashtable<String, Object> map = new Hashtable<String, Object>();
            JasperReport report;
//            report = JasperCompileManager.compileReport("/com/view/Bill.jrxml");
            String reportUrl = "/com/view/Bill.jrxml"; //path of your report source.
            InputStream reportFile = null;
            reportFile = getClass().getResourceAsStream(reportUrl);
            report = JasperCompileManager.compileReport(reportFile);

            map.put("OrderID", orderID);

            JasperPrint p = JasperFillManager.fillReport(report, (Map<String, Object>) map, Dao_Login.conn);
            JasperViewer.viewReport(p, false);
        } catch (JRException ex) {
            Logger.getLogger(Orders.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean returnProduct() {
        boolean p, valid = false;
        ArrayList arr = Dao_Orders.getOrderDetails(order.getOrderID());
        for (int i = 0; i < arr.size(); i++) {
            p = Dao_Products.updateProductOrder(((OrderDetails) arr.get(i)).getProductID(), Dao_Products.search(((OrderDetails) arr.get(i)).getProductID()).getQuantity() + ((OrderDetails) arr.get(i)).getQuantity());
            valid = true;
        }
        return valid;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        txtCustomerName = new javax.swing.JTextField();
        btnSearchCustomer = new javax.swing.JButton();
        cbxShipper = new javax.swing.JComboBox<>();
        dcrOrderDate = new com.toedter.calendar.JDateChooser();
        lblStatus = new javax.swing.JLabel();
        lblSubToTal = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        txtProductName = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblProducts = new javax.swing.JTable();
        btnSearchProduct = new javax.swing.JButton();
        txtProductPrice = new javax.swing.JTextField();
        txtProductQuantity = new javax.swing.JTextField();
        txtTotalAmount = new javax.swing.JTextField();
        cbxPayment = new javax.swing.JComboBox<>();
        lblGrandTotal = new javax.swing.JLabel();
        chkPromotion = new javax.swing.JCheckBox();
        lblPromotionStatus = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jButton11 = new javax.swing.JButton();
        btnInvoice = new javax.swing.JButton();
        btnAddOrder = new javax.swing.JButton();
        btnUpdateOrder = new javax.swing.JButton();
        btnDeleteOrder = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        btnAddProduct = new javax.swing.JButton();
        btnUpdateProduct = new javax.swing.JButton();
        btnDeleteProduct = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblOrders = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        txtSearch = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();

        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                formMouseMoved(evt);
            }
        });
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(195, 213, 213));
        jPanel1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jPanel1MouseMoved(evt);
            }
        });
        jPanel1.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jPanel1ComponentShown(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(73, 105, 105));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Algerian", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/img/Bed.png"))); // NOI18N
        jLabel1.setText("Fair deal");

        jLabel16.setBackground(new java.awt.Color(0, 0, 0));
        jLabel16.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Order Management");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 979, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(0, 0, 0));
        jPanel3.setForeground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/img/Out18.png"))); // NOI18N
        jLabel4.setText("Log Out");
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(133, 173, 173));

        jLabel7.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/img/write.png"))); // NOI18N
        jLabel7.setText("Customer Name");

        jLabel8.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/img/write.png"))); // NOI18N
        jLabel8.setText("Shipper   ");

        jLabel9.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/img/write.png"))); // NOI18N
        jLabel9.setText("Order Date");

        jLabel14.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/img/write.png"))); // NOI18N
        jLabel14.setText("Payment Method ");

        jLabel15.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/img/write.png"))); // NOI18N
        jLabel15.setText("Promotion");

        jLabel17.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/img/Total.png"))); // NOI18N
        jLabel17.setText("Grand Total ");

        jLabel19.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/img/Total.png"))); // NOI18N
        jLabel19.setText("Sub Total ");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addComponent(jLabel8)
                    .addComponent(jLabel7)
                    .addComponent(jLabel9)
                    .addComponent(jLabel14)
                    .addComponent(jLabel19)
                    .addComponent(jLabel15)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(133, 173, 173));
        jPanel5.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jPanel5MouseMoved(evt);
            }
        });
        jPanel5.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jPanel5ComponentShown(evt);
            }
        });

        txtCustomerName.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                txtCustomerNameMouseMoved(evt);
            }
        });
        txtCustomerName.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                txtCustomerNameComponentShown(evt);
            }
        });
        txtCustomerName.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                txtCustomerNameInputMethodTextChanged(evt);
            }
        });

        btnSearchCustomer.setBackground(new java.awt.Color(0, 0, 0));
        btnSearchCustomer.setFont(new java.awt.Font("Arial", 3, 12)); // NOI18N
        btnSearchCustomer.setForeground(new java.awt.Color(255, 255, 255));
        btnSearchCustomer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/img/Search.png"))); // NOI18N
        btnSearchCustomer.setText("Search");
        btnSearchCustomer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSearchCustomerMouseClicked(evt);
            }
        });
        btnSearchCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchCustomerActionPerformed(evt);
            }
        });

        cbxShipper.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxShipperActionPerformed(evt);
            }
        });

        lblStatus.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        lblStatus.setForeground(new java.awt.Color(255, 0, 0));

        lblSubToTal.setFont(new java.awt.Font("Arial", 3, 16)); // NOI18N
        lblSubToTal.setText("$0");

        jPanel9.setBackground(new java.awt.Color(133, 173, 173));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)), "Order Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N

        txtProductName.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                txtProductNameInputMethodTextChanged(evt);
            }
        });
        txtProductName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProductNameActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/img/write.png"))); // NOI18N
        jLabel10.setText("Product Name");

        jLabel12.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/img/write.png"))); // NOI18N
        jLabel12.setText("Quantity");

        jLabel13.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/img/write.png"))); // NOI18N
        jLabel13.setText("Price");

        jLabel11.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/img/write.png"))); // NOI18N
        jLabel11.setText("Amount");

        tblProducts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblProducts.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProductsMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tblProducts);

        btnSearchProduct.setBackground(new java.awt.Color(0, 0, 0));
        btnSearchProduct.setFont(new java.awt.Font("Arial", 3, 12)); // NOI18N
        btnSearchProduct.setForeground(new java.awt.Color(255, 255, 255));
        btnSearchProduct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/img/Search.png"))); // NOI18N
        btnSearchProduct.setText("Search");
        btnSearchProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchProductActionPerformed(evt);
            }
        });

        txtProductPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProductPriceActionPerformed(evt);
            }
        });

        txtProductQuantity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProductQuantityActionPerformed(evt);
            }
        });
        txtProductQuantity.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtProductQuantityKeyReleased(evt);
            }
        });

        txtTotalAmount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalAmountActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel10))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtProductPrice, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
                            .addComponent(txtProductName)))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTotalAmount)
                            .addComponent(txtProductQuantity))))
                .addGap(18, 18, 18)
                .addComponent(btnSearchProduct)
                .addGap(180, 180, 180))
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 506, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtProductName, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearchProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtProductPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtProductQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTotalAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        cbxPayment.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Money", "ATM" }));

        lblGrandTotal.setFont(new java.awt.Font("Arial", 3, 16)); // NOI18N
        lblGrandTotal.setText("$0");

        chkPromotion.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        chkPromotion.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkPromotionItemStateChanged(evt);
            }
        });
        chkPromotion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkPromotionActionPerformed(evt);
            }
        });

        lblPromotionStatus.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        lblPromotionStatus.setForeground(new java.awt.Color(255, 51, 0));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chkPromotion)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(lblGrandTotal, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblPromotionStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblSubToTal, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cbxPayment, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cbxShipper, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(dcrOrderDate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
                                    .addComponent(txtCustomerName, javax.swing.GroupLayout.Alignment.LEADING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSearchCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 543, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(128, 128, 128))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCustomerName, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSearchCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(dcrOrderDate, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbxShipper, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbxPayment, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblSubToTal, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(chkPromotion, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                            .addComponent(lblPromotionStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(13, 13, 13)
                        .addComponent(lblGrandTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblStatus)
                .addGap(26, 26, 26))
        );

        jPanel8.setBackground(new java.awt.Color(133, 173, 173));

        jPanel6.setBackground(new java.awt.Color(133, 173, 173));

        jButton11.setBackground(new java.awt.Color(0, 0, 0));
        jButton11.setFont(new java.awt.Font("Arial", 3, 12)); // NOI18N
        jButton11.setForeground(new java.awt.Color(255, 255, 255));
        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/img/Delete.png"))); // NOI18N
        jButton11.setText("Product");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        btnInvoice.setBackground(new java.awt.Color(0, 0, 0));
        btnInvoice.setFont(new java.awt.Font("Arial", 3, 11)); // NOI18N
        btnInvoice.setForeground(new java.awt.Color(255, 255, 255));
        btnInvoice.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/img/Oder18.png"))); // NOI18N
        btnInvoice.setText("Invoice");
        btnInvoice.setPreferredSize(new java.awt.Dimension(99, 45));
        btnInvoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInvoiceActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton11)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(btnInvoice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(btnInvoice, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );

        btnAddOrder.setBackground(new java.awt.Color(0, 0, 0));
        btnAddOrder.setFont(new java.awt.Font("Arial", 3, 11)); // NOI18N
        btnAddOrder.setForeground(new java.awt.Color(255, 255, 255));
        btnAddOrder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/img/Add18.png"))); // NOI18N
        btnAddOrder.setText("Order");
        btnAddOrder.setPreferredSize(new java.awt.Dimension(99, 45));
        btnAddOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddOrderActionPerformed(evt);
            }
        });

        btnUpdateOrder.setBackground(new java.awt.Color(0, 0, 0));
        btnUpdateOrder.setFont(new java.awt.Font("Arial", 3, 11)); // NOI18N
        btnUpdateOrder.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdateOrder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/img/Update.png"))); // NOI18N
        btnUpdateOrder.setText("Order");
        btnUpdateOrder.setPreferredSize(new java.awt.Dimension(80, 45));
        btnUpdateOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateOrderActionPerformed(evt);
            }
        });

        btnDeleteOrder.setBackground(new java.awt.Color(0, 0, 0));
        btnDeleteOrder.setFont(new java.awt.Font("Arial", 3, 11)); // NOI18N
        btnDeleteOrder.setForeground(new java.awt.Color(255, 255, 255));
        btnDeleteOrder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/img/Delete.png"))); // NOI18N
        btnDeleteOrder.setText("Order");
        btnDeleteOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteOrderActionPerformed(evt);
            }
        });

        btnReset.setBackground(new java.awt.Color(0, 0, 0));
        btnReset.setFont(new java.awt.Font("Arial", 3, 11)); // NOI18N
        btnReset.setForeground(new java.awt.Color(255, 255, 255));
        btnReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/img/New.png"))); // NOI18N
        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        btnAddProduct.setBackground(new java.awt.Color(0, 0, 0));
        btnAddProduct.setFont(new java.awt.Font("Arial", 3, 11)); // NOI18N
        btnAddProduct.setForeground(new java.awt.Color(255, 255, 255));
        btnAddProduct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/img/Add18.png"))); // NOI18N
        btnAddProduct.setText("Product");
        btnAddProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddProductActionPerformed(evt);
            }
        });

        btnUpdateProduct.setBackground(new java.awt.Color(0, 0, 0));
        btnUpdateProduct.setFont(new java.awt.Font("Arial", 3, 11)); // NOI18N
        btnUpdateProduct.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdateProduct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/img/Update.png"))); // NOI18N
        btnUpdateProduct.setText("Product");
        btnUpdateProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateProductActionPerformed(evt);
            }
        });

        btnDeleteProduct.setBackground(new java.awt.Color(0, 0, 0));
        btnDeleteProduct.setFont(new java.awt.Font("Arial", 3, 11)); // NOI18N
        btnDeleteProduct.setForeground(new java.awt.Color(255, 255, 255));
        btnDeleteProduct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/img/Delete.png"))); // NOI18N
        btnDeleteProduct.setText("Product");
        btnDeleteProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteProductActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAddOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUpdateOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDeleteOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAddProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUpdateProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDeleteProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(218, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnAddOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnUpdateOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnDeleteOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAddProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnUpdateProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnDeleteProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        tblOrders.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblOrders.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblOrdersMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tblOrders);

        jPanel7.setBackground(new java.awt.Color(133, 173, 173));

        jPanel10.setBackground(new java.awt.Color(133, 173, 173));

        txtSearch.setToolTipText("Hi");
        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSearchKeyTyped(evt);
            }
        });

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/img/Search.png"))); // NOI18N

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 706, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addGap(0, 1, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(230, 230, 230)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(7, 7, 7))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 1075, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnInvoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInvoiceActionPerformed
        if (order != null) {
            invoice(order.getOrderID());
        } else {
            Common.notify("You have not selected any order, please select an order in the table to perform this function", notification, JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnInvoiceActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton11ActionPerformed

    private void btnDeleteProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteProductActionPerformed
        if (order != null) {
            Instant instant = dcrOrderDate.getDate().toInstant();
            ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
            LocalDate date = zdt.toLocalDate();
            LocalDate today = LocalDate.now();
            if (orderDetail != null) {
                if (date.plusDays(3).compareTo(today) == 0 || date.plusDays(3).isAfter(today)) {
                    int click = JOptionPane.showConfirmDialog(null, "Do you want to delete this product from the order?", notification, 2);
                    if (click == JOptionPane.YES_OPTION) {
                        boolean b = deleteProduct();
                        if (b) {
                            Common.notify("The product has been deleted!", notification, 1);
                            refreshProduct();
                        } else {
                            Common.notify("Failed, the product has not been deleted!", notification, JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    if (chkPromotion.isSelected()) {
                        Common.notify("You cannot delete products in this order", notification, JOptionPane.WARNING_MESSAGE);
                        refreshProduct();
                    } else {
                        if (date.plusDays(3).compareTo(today) == 0 || date.plusDays(3).isAfter(today)) {
                            int click = JOptionPane.showConfirmDialog(null, "Do you want to delete this product from the order?", notification, 2);
                            if (click == JOptionPane.YES_OPTION) {
                                boolean b = deleteProduct();
                                if (b) {
                                    Common.notify("The product has been deleted!", notification, 1);
                                    refreshProduct();
                                } else {
                                    Common.notify("Failed, the product has not been deleted!", notification, JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        } else {
                            Common.notify("The time has passed (3 days), you cannot change this order", notification, JOptionPane.WARNING_MESSAGE);
                            refreshProduct();
                        }
                    }
                }
            } else {
                Common.notify("You have not selected any products, please select a product in the table to perform this function", notification, JOptionPane.WARNING_MESSAGE);
            }
        } else {
            Common.notify("You have not selected any order, please select an order in the table to perform this function", notification, JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnDeleteProductActionPerformed

    private void btnUpdateProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateProductActionPerformed
        if (order != null) {
            Instant instant = dcrOrderDate.getDate().toInstant();
            ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
            LocalDate date = zdt.toLocalDate();
            LocalDate today = LocalDate.now();
            if (orderDetail != null) {
                if (date.compareTo(today) == 0) {
                    if (checkProductID(orderDetail.getProductID())) {
                        Common.notify("This product is not in order yet", notification, 1);
                        refreshProduct();
                    } else if (!"".equals(checkAddProduct())) {
                        Common.notify(checkAddProduct(), notification, 1);
                    } else {
                        boolean b = updateProduct();
                        if (b) {
                            Common.notify("The product has been updated!", notification, 1);
                            refreshProduct();
                        } else {
                            Common.notify("Failed, the product has not been updated!", notification, JOptionPane.ERROR_MESSAGE);
                        }
                        refreshProduct();
                    }
                } else {
                    if (chkPromotion.isSelected()) {
                        Common.notify("You cannot update products in this order", notification, JOptionPane.WARNING_MESSAGE);
                        refreshProduct();
                    } else {
                        if (date.plusDays(3).compareTo(today) == 0 || date.plusDays(3).isAfter(today)) {
                            if (!checkProductID(product.getProductID())) {
                                Common.notify("This product is not in order yet", notification, 1);
                                refreshProduct();
                            } else if (!"".equals(checkAddProduct())) {
                                Common.notify(checkAddProduct(), notification, 1);
                            } else {
                                boolean b = updateProduct();
                                if (b) {
                                    Common.notify("The product has been updated!", notification, 1);
                                    refreshProduct();
                                } else {
                                    Common.notify("Failed, the product has not been updated!", notification, JOptionPane.ERROR_MESSAGE);
                                }
                                refreshProduct();
                            }
                        } else {
                            Common.notify("The time has passed (3 days), you cannot change this order", notification, JOptionPane.WARNING_MESSAGE);
                            refreshProduct();
                        }
                    }
                }
            } else {
                Common.notify("You have not selected any products, please select a product in the table to perform this function", notification, JOptionPane.WARNING_MESSAGE);
            }
        } else {
            Common.notify("You have not selected any order, please select an order in the table to perform this function", notification, JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnUpdateProductActionPerformed

    private void btnAddProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddProductActionPerformed
        if (order != null) {
            Instant instant = dcrOrderDate.getDate().toInstant();
            ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
            LocalDate date = zdt.toLocalDate();
            LocalDate today = LocalDate.now();
            if (date.compareTo(today) == 0) {
                if (txtProductName.getText().trim().equals("")) {
                    Common.notify("Please select the product name and fill in the necessary information to perform this function", notification, 1);
                } else if (!checkProductID(product.getProductID())) {
                    Common.notify("This product already exists in the order", notification, 1);
                    refreshProduct();
                } else if (!"".equals(checkAddProduct())) {
                    Common.notify(checkAddProduct(), notification, 1);
                } else {
                    boolean b = addProduct();
                    if (b) {
                        Common.notify("The product has been added to the order!", notification, 1);
                        refreshProduct();
                    } else {
                        Common.notify("Failed, the product has not been added to the order!", notification, JOptionPane.ERROR_MESSAGE);
                    }
                    refreshProduct();
                }
            } else {
                if (chkPromotion.isSelected()) {
                    Common.notify("You cannot add products to this order", notification, JOptionPane.WARNING_MESSAGE);
                    refreshProduct();
                } else {
                    if (date.plusDays(3).compareTo(today) == 0 || date.plusDays(3).isAfter(today)) {
                        if (txtProductName.getText().trim().equals("")) {
                            Common.notify("Please select the product name and fill in the necessary information to perform this function", notification, 1);
                        } else if (!checkProductID(product.getProductID())) {
                            Common.notify("This product already exists in the order", notification, 1);
                            refreshProduct();
                        } else if (!"".equals(checkAddProduct())) {
                            Common.notify(checkAddProduct(), notification, 1);
                        } else {
                            boolean b = addProduct();
                            if (b) {
                                Common.notify("The product has been added to the order!", notification, 1);
                                refreshProduct();
                            } else {
                                Common.notify("Failed, the product has not been added to the order!", notification, JOptionPane.ERROR_MESSAGE);
                            }
                            refreshProduct();
                        }
                    } else {
                        Common.notify("The time has passed (3 days), you cannot change this order", notification, JOptionPane.WARNING_MESSAGE);
                        refreshProduct();
                    }
                }
            }
        } else {
            Common.notify("You have not selected any order, please select an order in the table to perform this function", notification, JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnAddProductActionPerformed

    private void txtProductNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProductNameActionPerformed

    }//GEN-LAST:event_txtProductNameActionPerformed

    private void btnSearchProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchProductActionPerformed
        Search s = new Search("Product");
        refreshProduct();
        s.setLocationRelativeTo(null);
        s.setVisible(true);
    }//GEN-LAST:event_btnSearchProductActionPerformed

    private void txtProductPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProductPriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProductPriceActionPerformed

    private void txtProductQuantityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProductQuantityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProductQuantityActionPerformed

    private void txtTotalAmountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalAmountActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalAmountActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed

    private void chkPromotionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkPromotionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkPromotionActionPerformed

    private void btnSearchCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchCustomerActionPerformed
        Search s = new Search("Customer");
        s.setLocationRelativeTo(null);
        s.setVisible(true);
    }//GEN-LAST:event_btnSearchCustomerActionPerformed

    private void btnSearchCustomerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSearchCustomerMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSearchCustomerMouseClicked

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown

    }//GEN-LAST:event_formComponentShown

    private void jPanel1ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanel1ComponentShown

    }//GEN-LAST:event_jPanel1ComponentShown

    private void jPanel5MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseMoved
        txtCustomerName.setText(customer.getCustomerName());
        txtCustomerName.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent de) {
                enableComponent();
            }

            @Override
            public void removeUpdate(DocumentEvent de) {

            }

            @Override
            public void changedUpdate(DocumentEvent de) {
                enableComponent();
            }
        });

        if (txtProductName.getText().trim().equals("")) {
            txtProductName.setText(product.getProductName());
            txtProductName.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent de) {
                    loadProductInfo();
                }

                @Override
                public void removeUpdate(DocumentEvent de) {

                }

                @Override
                public void changedUpdate(DocumentEvent de) {
                    loadProductInfo();
                }
            });
        }
//        }
    }//GEN-LAST:event_jPanel5MouseMoved

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        refresh();
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnAddOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddOrderActionPerformed
        if (txtCustomerName.getText().trim().equals("")) {
            Common.notify("Please select the customer name and fill in the necessary information to perform this function", notification, 1);
        } else if (!txtCustomerName.getText().trim().equals("")) {
            if (!"".equals(checkAddOrder())) {
                Common.notify(checkAddOrder(), notification, 1);
            } else {
                if (customer.getCustomerID() != null) {
                    String[] ship = cbxShipper.getSelectedItem().toString().split("\\s");
                    boolean b = Dao_Orders.insertOrder(customer.getCustomerID(), Dao_Staff.staffID, "PM006", ship[0], new java.sql.Timestamp(dcrOrderDate.getDate().getTime()), 0, cbxPayment.getSelectedItem().toString(), "Enable");
                    if (b) {
                        Common.notify("The order has been added!", notification, 1);
                        getOrders(Dao_Orders.getOrders());
                        refresh();
                    } else {
                        Common.notify("Failed, the order has not been added!", notification, JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    refresh();
                    Common.notify("Failed, the order has not been added!", notification, JOptionPane.ERROR_MESSAGE);
                }

            }
        }
    }//GEN-LAST:event_btnAddOrderActionPerformed

    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved

    }//GEN-LAST:event_formMouseMoved

    private void jPanel1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseMoved

    }//GEN-LAST:event_jPanel1MouseMoved

    private void txtCustomerNameInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtCustomerNameInputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCustomerNameInputMethodTextChanged

    private void txtProductQuantityKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProductQuantityKeyReleased
        txtProductQuantity.setText(cutChar(txtProductQuantity.getText().trim()));
        if (txtProductQuantity.getText().trim().equals("")) {
            txtTotalAmount.setText(currency.format(0));
        } else {
            int quantity = Integer.parseInt(txtProductQuantity.getText().trim());
            String price = txtProductPrice.getText().trim().substring(1);
            txtTotalAmount.setText(currency.format(quantity * Double.parseDouble(price)));
        }
    }//GEN-LAST:event_txtProductQuantityKeyReleased

    private void jPanel5ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanel5ComponentShown
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel5ComponentShown

    private void txtCustomerNameMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCustomerNameMouseMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCustomerNameMouseMoved

    private void txtCustomerNameComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_txtCustomerNameComponentShown

    }//GEN-LAST:event_txtCustomerNameComponentShown

    private void tblOrdersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblOrdersMouseClicked
        loadOrderIF();
        getOrderDetails();
        btnSearchProduct.setEnabled(true);
        txtProductQuantity.setEnabled(true);
        lblStatus.setText("");
        txtSearch.setText("");
    }//GEN-LAST:event_tblOrdersMouseClicked

    private void tblProductsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProductsMouseClicked
        loadProductIF();
        lblStatus.setText("");
    }//GEN-LAST:event_tblProductsMouseClicked

    private void txtSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyPressed
        if (txtSearch.getText().equals("")) {
            getOrders(Dao_Orders.getOrders());
        } else {
            getOrders(search());
        }
    }//GEN-LAST:event_txtSearchKeyPressed

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        if (txtSearch.getText().equals("")) {
            getOrders(Dao_Orders.getOrders());
        } else {
            getOrders(search());
        }
    }//GEN-LAST:event_txtSearchKeyReleased

    private void txtSearchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyTyped
        if (txtSearch.getText().equals("")) {
            getOrders(Dao_Orders.getOrders());
        } else {
            getOrders(search());
        }
    }//GEN-LAST:event_txtSearchKeyTyped

    private void txtProductNameInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtProductNameInputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProductNameInputMethodTextChanged

    private void cbxShipperActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxShipperActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxShipperActionPerformed

    private void chkPromotionItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkPromotionItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_chkPromotionItemStateChanged

    private void btnUpdateOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateOrderActionPerformed
        Instant instant = dcrOrderDate.getDate().toInstant();
        ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
        LocalDate date = zdt.toLocalDate();
        LocalDate today = LocalDate.now();
        if (order != null) {
            if (date.plusDays(3).compareTo(today) == 0 || date.plusDays(3).isAfter(today)) {
                String[] ship = cbxShipper.getSelectedItem().toString().split("\\s");
                String customerID;
                if (!Dao_Customer.getCustomerByID(order.getCustomerID()).getCustomerName().equals(txtCustomerName.getText())) {
                    customerID = customer.getCustomerID();
                } else {
                    customerID = order.getCustomerID();
                }
                boolean b = Dao_Orders.updateOrder(order.getOrderID(), customerID, Dao_Staff.staffID, order.getPromotionID(), ship[0], new java.sql.Timestamp(dcrOrderDate.getDate().getTime()), order.getTotalAmount(), cbxPayment.getSelectedItem().toString(), "Enable");
                if (b) {
                    Common.notify("The order has been updated!", notification, 1);
                    getOrders(Dao_Orders.getOrders());
                    refresh();
                } else {
                    Common.notify("Failed, the order has not been updated!", notification, JOptionPane.ERROR_MESSAGE);
                }
            } else {
                Common.notify("The time has passed (3 days), you cannot change this order", notification, JOptionPane.WARNING_MESSAGE);
                refresh();
            }
        } else {
            Common.notify("You have not selected any order, please select an order in the table to perform this function", notification, JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnUpdateOrderActionPerformed

    private void btnDeleteOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteOrderActionPerformed
        Instant instant = dcrOrderDate.getDate().toInstant();
        ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
        LocalDate date = zdt.toLocalDate();
        LocalDate today = LocalDate.now();
        if (order != null) {
            if (date.plusDays(3).compareTo(today) == 0 || date.plusDays(3).isAfter(today)) {
                int click = JOptionPane.showConfirmDialog(null, "Do you want to delete this order?", notification, 2);
                if (click == JOptionPane.YES_OPTION) {
                    boolean b = Dao_Orders.deleteOrder(order.getOrderID());
                    boolean p = returnProduct();
                    if (b & p) {
                        Common.notify("The order has been deleted!", notification, 1);
                        getOrders(Dao_Orders.getOrders());
                        refresh();
                    } else if (b) {
                        Common.notify("The order has been deleted!", notification, 1);
                        getOrders(Dao_Orders.getOrders());
                        refresh();
                    } else {
                        Common.notify("Failed, the order has not been deleted!", notification, JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                Common.notify("The time has passed (3 days), you cannot change this order", notification, JOptionPane.WARNING_MESSAGE);
                refresh();
            }
        } else {
            Common.notify("You have not selected any order, please select an order in the table to perform this function", notification, JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnDeleteOrderActionPerformed

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        Login.mainStaff.dispose();
        Login.jlogin.setLocationRelativeTo(null);
        Login.jlogin.setVisible(true);
    }//GEN-LAST:event_jLabel4MouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddOrder;
    private javax.swing.JButton btnAddProduct;
    private javax.swing.JButton btnDeleteOrder;
    private javax.swing.JButton btnDeleteProduct;
    private javax.swing.JButton btnInvoice;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSearchCustomer;
    private javax.swing.JButton btnSearchProduct;
    private javax.swing.JButton btnUpdateOrder;
    private javax.swing.JButton btnUpdateProduct;
    private javax.swing.JComboBox<String> cbxPayment;
    private javax.swing.JComboBox<String> cbxShipper;
    private javax.swing.JCheckBox chkPromotion;
    private com.toedter.calendar.JDateChooser dcrOrderDate;
    private javax.swing.JButton jButton11;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JLabel lblGrandTotal;
    private javax.swing.JLabel lblPromotionStatus;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblSubToTal;
    private javax.swing.JTable tblOrders;
    private javax.swing.JTable tblProducts;
    private javax.swing.JTextField txtCustomerName;
    private javax.swing.JTextField txtProductName;
    private javax.swing.JTextField txtProductPrice;
    private javax.swing.JTextField txtProductQuantity;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtTotalAmount;
    // End of variables declaration//GEN-END:variables
}
