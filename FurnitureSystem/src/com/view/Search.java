
package com.view;

import com.dao.Common;
import com.dao.Dao_Categories;
import com.dao.Dao_Customer;
import com.dao.Dao_Products;
import com.dao.Dao_Suppliers;
import com.model.Customer;
import com.model.Product;
import java.awt.HeadlessException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;


public class Search extends javax.swing.JFrame {

    
    String form;
    private Customer customer;
    private Product product;
    private String notification, errorSearch, id, cusName, gender, birthdate, address, phone, status;
    private String proID, proName, supplier, category, unitPrice, sellPrice, quantity;

    public Search() {
        initComponents();
    }
    
    public Search(String form) throws HeadlessException {
        initComponents();
        this.form = form;
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel9 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        txtSearch = new javax.swing.JTextField();
        btnSelect = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSearch = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        jPanel9.setBackground(new java.awt.Color(73, 105, 105));

        jLabel23.setBackground(new java.awt.Color(0, 0, 0));
        jLabel23.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("Search");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel10.setBackground(new java.awt.Color(133, 173, 173));

        jPanel11.setBackground(new java.awt.Color(133, 173, 173));

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

        btnSelect.setBackground(new java.awt.Color(0, 0, 0));
        btnSelect.setFont(new java.awt.Font("Arial", 3, 12)); // NOI18N
        btnSelect.setForeground(new java.awt.Color(255, 255, 255));
        btnSelect.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/img/Search.png"))); // NOI18N
        btnSelect.setText("Select");
        btnSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 515, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSelect)
                .addContainerGap(103, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(149, 149, 149)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(175, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7))
        );

        tblSearch.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSearchMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblSearch);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed

    private void btnSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectActionPerformed
        if (form.equals("Customer")) {
            if (customer != null) {
                Orders.getCustomer(customer.getCustomerID(), customer.getCustomerName());
                this.dispose();
            } else {
                Common.notify(errorSearch, notification, JOptionPane.ERROR_MESSAGE);
            }
        } else if (form.equals("Product")) {
            if (customer != null) {
                Orders.getProduct(product.getProductID(), product.getProductName(), product.getSellPrice(), product.getQuantity());
                this.dispose();
            } else {
                Common.notify(errorSearch, notification, JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnSelectActionPerformed

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        if (form.equals("Customer")) {
            loadData(Dao_Customer.getCustomers());
        } else if (form.equals("Product")) {
            loadData(Dao_Products.searchAll());
        }
    }//GEN-LAST:event_formComponentShown

    private void tblSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSearchMouseClicked
        int Click = tblSearch.getSelectedRow();
        TableModel model = tblSearch.getModel();
        customer = new Customer();
        product = new Product();
        if (form.equals("Customer")) {
            customer.setCustomerID(model.getValueAt(Click, 0).toString());
            customer.setCustomerName(model.getValueAt(Click, 1).toString());
        } else if (form.equals("Product")) {
            product.setProductID((String) model.getValueAt(Click, 0));
            product.setProductName((String) model.getValueAt(Click, 1));
            product.setSellPrice(Double.valueOf(model.getValueAt(Click, 5).toString()));
            product.setQuantity(Integer.valueOf(model.getValueAt(Click, 6).toString()));
        }
    }//GEN-LAST:event_tblSearchMouseClicked

    private void txtSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyPressed
        loadData(search());
    }//GEN-LAST:event_txtSearchKeyPressed

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        loadData(search());
    }//GEN-LAST:event_txtSearchKeyReleased

    private void txtSearchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyTyped
        loadData(search());
    }//GEN-LAST:event_txtSearchKeyTyped

    /**
     * @param args the command line arguments
     */
    private void loadData(ArrayList arr) {
        if (form.equals("Customer")) {
            tblSearch.removeAll();
            Object[] obj = new Object[]{id, cusName, gender, birthdate, address, phone, status};
            DefaultTableModel tableSearch = new DefaultTableModel(obj, 0);
            tblSearch.setModel(tableSearch);

            for (int i = 0; i < arr.size(); i++) {
                if (((Customer) arr.get(i)).getStatus().equals("Enable")) {
                    Object[] objCustomer = new Object[7];
                    objCustomer[0] = ((Customer) arr.get(i)).getCustomerID();
                    objCustomer[1] = ((Customer) arr.get(i)).getCustomerName();
                    objCustomer[2] = ((Customer) arr.get(i)).getGender();
                    objCustomer[3] = ((Customer) arr.get(i)).getBirthdate();
                    objCustomer[4] = ((Customer) arr.get(i)).getAddress();
                    objCustomer[5] = ((Customer) arr.get(i)).getPhone();
                    objCustomer[6] = ((Customer) arr.get(i)).getStatus();
                    tableSearch.addRow(objCustomer);
                }
            }
        } else if (form.equals("Product")) {
            tblSearch.removeAll();
            Object[] obj = new Object[]{proID, proName, supplier, category, unitPrice, sellPrice, quantity, status};
            DefaultTableModel tableSearch = new DefaultTableModel(obj, 0);
            tblSearch.setModel(tableSearch);

            for (int i = 0; i < arr.size(); i++) {
                if (((Product) arr.get(i)).getStatus().equals("Enable")) {
                    Object[] objProduct = new Object[8];
                    objProduct[0] = ((Product) arr.get(i)).getProductID();
                    objProduct[1] = ((Product) arr.get(i)).getProductName();
                    objProduct[2] = Dao_Suppliers.search(((Product) arr.get(i)).getSupplierID()).getCompanyName();
                    objProduct[3] = Dao_Categories.searchid(((Product) arr.get(i)).getCategoryID()).getCategoryName();
                    objProduct[4] = ((Product) arr.get(i)).getUnitPrice();
                    objProduct[5] = ((Product) arr.get(i)).getSellPrice();
                    objProduct[6] = ((Product) arr.get(i)).getQuantity();
                    objProduct[7] = ((Product) arr.get(i)).getStatus();
                    tableSearch.addRow(objProduct);
                }
            }
        }
    }

    private ArrayList search() {
        ArrayList arr = new ArrayList();
        if (form.equals("Customer")) {
            arr = Dao_Customer.getCustomers();
            ArrayList<Object> arrCus = new ArrayList<Object>();
            for (int i = 0; i < arr.size(); i++) {
                if (((Customer) arr.get(i)).getStatus().equals("Enable")) {
                    String cusID = ((Customer) arr.get(i)).getCustomerID().trim();
                    String name = ((Customer) arr.get(i)).getCustomerName().toUpperCase().trim();
                    String cusPhone = ((Customer) arr.get(i)).getPhone().trim();
                    if (cusID.contains(txtSearch.getText().toUpperCase()) || name.contains(txtSearch.getText().toUpperCase()) || cusPhone.contains(txtSearch.getText().toUpperCase())) {
                        arrCus.add(arr.get(i));
                    }
                }
            }
            return arrCus;
        } else if (form.equals("Product")) {
            arr = Dao_Products.searchAll();
            ArrayList<Object> arrPro = new ArrayList<Object>();
            for (int i = 0; i < arr.size(); i++) {
                if (((Product) arr.get(i)).getStatus().equals("Enable")) {
                    String prodID = ((Product) arr.get(i)).getProductID().trim();
                    String name = ((Product) arr.get(i)).getProductName().toUpperCase().trim();
                    if (prodID.contains(txtSearch.getText().toUpperCase()) || name.contains(txtSearch.getText().toUpperCase())) {
                        arrPro.add(arr.get(i));
                    }
                }
            }
            return arrPro;
        }
        return arr;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSelect;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblSearch;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
