
package com.view;

import com.model.DanhMucBean;
import com.controller.ChuyenMangHinhController;
import com.model.Customer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;


public class MainAdmin extends javax.swing.JFrame {

    
    public MainAdmin() {
        initComponents();

        ChuyenMangHinhController controller = new ChuyenMangHinhController(jpnView);
        controller.setViewAdmin(jpnHome, jlbHome);
        List<DanhMucBean> listItem = new ArrayList<>();
        listItem.add(new DanhMucBean("HomePageAdmin", jpnHome, jlbHome));
        listItem.add(new DanhMucBean("Products", jpnProduct, jlbProduct));
        listItem.add(new DanhMucBean("Staff", jPnStaff, jlbStaff));
        listItem.add(new DanhMucBean("Categories", jpnCategory, jlbCategory));
        listItem.add(new DanhMucBean("Suppliers", jpnSupplier, jlbSupplier));
        listItem.add(new DanhMucBean("Promotion", jpnPromotion, jlbPromotion));
        listItem.add(new DanhMucBean("Shippers", jpnShipper, jlbShipper));
        listItem.add(new DanhMucBean("Reports", jpnReport, jlbReport));
        controller.setEvent(listItem);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpnRoot = new javax.swing.JPanel();
        jpnMenu = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jpnHome = new javax.swing.JPanel();
        jlbHome = new javax.swing.JLabel();
        jpnProduct = new javax.swing.JPanel();
        jlbProduct = new javax.swing.JLabel();
        jPnStaff = new javax.swing.JPanel();
        jlbStaff = new javax.swing.JLabel();
        jpnCategory = new javax.swing.JPanel();
        jlbCategory = new javax.swing.JLabel();
        jpnSupplier = new javax.swing.JPanel();
        jlbSupplier = new javax.swing.JLabel();
        jpnPromotion = new javax.swing.JPanel();
        jlbPromotion = new javax.swing.JLabel();
        jpnShipper = new javax.swing.JPanel();
        jlbShipper = new javax.swing.JLabel();
        jpnReport = new javax.swing.JPanel();
        jlbReport = new javax.swing.JLabel();
        jpnView = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        jpnMenu.setBackground(new java.awt.Color(195, 213, 213));

        jPanel3.setBackground(new java.awt.Color(73, 105, 105));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(240, 245, 245));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/img/Quanly.png"))); // NOI18N
        jLabel1.setText("Admin");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jpnHome.setBackground(new java.awt.Color(133, 173, 173));

        jlbHome.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jlbHome.setForeground(new java.awt.Color(240, 245, 245));
        jlbHome.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jlbHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/img/Home24.png"))); // NOI18N
        jlbHome.setText("Home Page");
        jlbHome.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jlbHome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlbHomeMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jpnHomeLayout = new javax.swing.GroupLayout(jpnHome);
        jpnHome.setLayout(jpnHomeLayout);
        jpnHomeLayout.setHorizontalGroup(
            jpnHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnHomeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlbHome, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpnHomeLayout.setVerticalGroup(
            jpnHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jlbHome, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
        );

        jpnProduct.setBackground(new java.awt.Color(133, 173, 173));

        jlbProduct.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jlbProduct.setForeground(new java.awt.Color(240, 245, 245));
        jlbProduct.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jlbProduct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/img/Sanpham.png"))); // NOI18N
        jlbProduct.setText("Product");
        jlbProduct.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jlbProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlbProductMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jpnProductLayout = new javax.swing.GroupLayout(jpnProduct);
        jpnProduct.setLayout(jpnProductLayout);
        jpnProductLayout.setHorizontalGroup(
            jpnProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnProductLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlbProduct, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpnProductLayout.setVerticalGroup(
            jpnProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jlbProduct, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
        );

        jPnStaff.setBackground(new java.awt.Color(133, 173, 173));

        jlbStaff.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jlbStaff.setForeground(new java.awt.Color(240, 245, 245));
        jlbStaff.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jlbStaff.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/img/Staff.png"))); // NOI18N
        jlbStaff.setText("Staff");
        jlbStaff.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jlbStaff.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlbStaffMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPnStaffLayout = new javax.swing.GroupLayout(jPnStaff);
        jPnStaff.setLayout(jPnStaffLayout);
        jPnStaffLayout.setHorizontalGroup(
            jPnStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPnStaffLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlbStaff, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPnStaffLayout.setVerticalGroup(
            jPnStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jlbStaff, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
        );

        jpnCategory.setBackground(new java.awt.Color(133, 173, 173));

        jlbCategory.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jlbCategory.setForeground(new java.awt.Color(240, 245, 245));
        jlbCategory.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jlbCategory.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/img/Category.png"))); // NOI18N
        jlbCategory.setText("Category");
        jlbCategory.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jlbCategory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlbCategoryMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jpnCategoryLayout = new javax.swing.GroupLayout(jpnCategory);
        jpnCategory.setLayout(jpnCategoryLayout);
        jpnCategoryLayout.setHorizontalGroup(
            jpnCategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnCategoryLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlbCategory, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpnCategoryLayout.setVerticalGroup(
            jpnCategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnCategoryLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jlbCategory, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        jpnSupplier.setBackground(new java.awt.Color(133, 173, 173));

        jlbSupplier.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jlbSupplier.setForeground(new java.awt.Color(240, 245, 245));
        jlbSupplier.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jlbSupplier.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/img/Supplier.png"))); // NOI18N
        jlbSupplier.setText("Supplier");
        jlbSupplier.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jlbSupplier.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlbSupplierMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jpnSupplierLayout = new javax.swing.GroupLayout(jpnSupplier);
        jpnSupplier.setLayout(jpnSupplierLayout);
        jpnSupplierLayout.setHorizontalGroup(
            jpnSupplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnSupplierLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlbSupplier, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpnSupplierLayout.setVerticalGroup(
            jpnSupplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnSupplierLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jlbSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jpnPromotion.setBackground(new java.awt.Color(133, 173, 173));

        jlbPromotion.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jlbPromotion.setForeground(new java.awt.Color(240, 245, 245));
        jlbPromotion.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jlbPromotion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/img/Promotion.png"))); // NOI18N
        jlbPromotion.setText("Promotion");
        jlbPromotion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jlbPromotion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlbPromotionMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jpnPromotionLayout = new javax.swing.GroupLayout(jpnPromotion);
        jpnPromotion.setLayout(jpnPromotionLayout);
        jpnPromotionLayout.setHorizontalGroup(
            jpnPromotionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnPromotionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlbPromotion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpnPromotionLayout.setVerticalGroup(
            jpnPromotionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnPromotionLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jlbPromotion, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jpnShipper.setBackground(new java.awt.Color(133, 173, 173));

        jlbShipper.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jlbShipper.setForeground(new java.awt.Color(240, 245, 245));
        jlbShipper.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jlbShipper.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/img/shipper.png"))); // NOI18N
        jlbShipper.setText("Shipper");
        jlbShipper.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jlbShipper.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlbShipperMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jpnShipperLayout = new javax.swing.GroupLayout(jpnShipper);
        jpnShipper.setLayout(jpnShipperLayout);
        jpnShipperLayout.setHorizontalGroup(
            jpnShipperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnShipperLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlbShipper, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpnShipperLayout.setVerticalGroup(
            jpnShipperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnShipperLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jlbShipper, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jpnReport.setBackground(new java.awt.Color(133, 173, 173));

        jlbReport.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jlbReport.setForeground(new java.awt.Color(240, 245, 245));
        jlbReport.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jlbReport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/img/Report.png"))); // NOI18N
        jlbReport.setText("Report");
        jlbReport.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jlbReport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlbReportMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jpnReportLayout = new javax.swing.GroupLayout(jpnReport);
        jpnReport.setLayout(jpnReportLayout);
        jpnReportLayout.setHorizontalGroup(
            jpnReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnReportLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlbReport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpnReportLayout.setVerticalGroup(
            jpnReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnReportLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jlbReport, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jpnMenuLayout = new javax.swing.GroupLayout(jpnMenu);
        jpnMenu.setLayout(jpnMenuLayout);
        jpnMenuLayout.setHorizontalGroup(
            jpnMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jpnPromotion, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpnSupplier, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpnCategory, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPnStaff, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpnShipper, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpnHome, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpnProduct, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpnReport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpnMenuLayout.setVerticalGroup(
            jpnMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnMenuLayout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpnHome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpnProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jPnStaff, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpnCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpnSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpnPromotion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpnShipper, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpnReport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(118, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jpnViewLayout = new javax.swing.GroupLayout(jpnView);
        jpnView.setLayout(jpnViewLayout);
        jpnViewLayout.setHorizontalGroup(
            jpnViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1082, Short.MAX_VALUE)
        );
        jpnViewLayout.setVerticalGroup(
            jpnViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jpnRootLayout = new javax.swing.GroupLayout(jpnRoot);
        jpnRoot.setLayout(jpnRootLayout);
        jpnRootLayout.setHorizontalGroup(
            jpnRootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnRootLayout.createSequentialGroup()
                .addComponent(jpnMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpnView, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpnRootLayout.setVerticalGroup(
            jpnRootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpnMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jpnView, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpnRoot, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpnRoot, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jlbHomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbHomeMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jlbHomeMouseClicked

    private void jlbProductMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbProductMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jlbProductMouseClicked

    private void jlbStaffMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbStaffMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jlbStaffMouseClicked

    private void jlbCategoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbCategoryMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jlbCategoryMouseClicked

    private void jlbSupplierMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbSupplierMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jlbSupplierMouseClicked

    private void jlbPromotionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbPromotionMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jlbPromotionMouseClicked

    private void jlbShipperMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbShipperMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jlbShipperMouseClicked

    private void jlbReportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbReportMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jlbReportMouseClicked

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown

    }//GEN-LAST:event_formComponentShown

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPnStaff;
    private javax.swing.JLabel jlbCategory;
    private javax.swing.JLabel jlbHome;
    private javax.swing.JLabel jlbProduct;
    private javax.swing.JLabel jlbPromotion;
    private javax.swing.JLabel jlbReport;
    private javax.swing.JLabel jlbShipper;
    private javax.swing.JLabel jlbStaff;
    private javax.swing.JLabel jlbSupplier;
    private javax.swing.JPanel jpnCategory;
    private javax.swing.JPanel jpnHome;
    private javax.swing.JPanel jpnMenu;
    private javax.swing.JPanel jpnProduct;
    private javax.swing.JPanel jpnPromotion;
    private javax.swing.JPanel jpnReport;
    private javax.swing.JPanel jpnRoot;
    private javax.swing.JPanel jpnShipper;
    private javax.swing.JPanel jpnSupplier;
    private javax.swing.JPanel jpnView;
    // End of variables declaration//GEN-END:variables
}
