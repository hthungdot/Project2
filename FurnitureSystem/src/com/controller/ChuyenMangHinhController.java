
package com.controller;

import com.view.ChangePassword;
import com.view.Customers;
import com.view.HomePageStaff;
import com.view.Orders;
import com.view.Users;
import com.model.DanhMucBean;
import com.view.Categories;
import com.view.Products;
import com.view.HomePageAdmin;
import com.view.Promotions;
import com.view.Reports;
import com.view.Shippers;
import com.view.Staffs;
import com.view.Suppliers;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ChuyenMangHinhController {

    private JPanel root;
    private String kindSelected = "";
    private List<DanhMucBean> listItem = null;

    public ChuyenMangHinhController(JPanel jpnRoot) {
        this.root = jpnRoot;
    }

    public void setViewAdmin(JPanel jpnItem, JLabel jlbItem) {
        kindSelected = "HomePageAdmin";
        jpnItem.setBackground(new Color(40, 77, 0));
        jlbItem.setBackground(new Color(40, 77, 0));
        JPanel node = new HomePageAdmin();
        root.removeAll();
        root.setLayout(new BorderLayout());
        root.add(new HomePageAdmin());
        root.validate();
        root.repaint();
    }

    public void setViewStaff(JPanel jpnItem, JLabel jlbItem) {
        kindSelected = "HomePageStaff";
        jpnItem.setBackground(new Color(40, 77, 0));
        jlbItem.setBackground(new Color(40, 77, 0));
        JPanel node = new HomePageAdmin();
        root.removeAll();
        root.setLayout(new BorderLayout());
        root.add(new HomePageStaff());
        root.validate();
        root.repaint();
    }

    public void setEvent(List<DanhMucBean> listItem) {
        this.listItem = listItem;
        for (DanhMucBean item : listItem) {
            item.getJlb().addMouseListener(new labelEvent(item.getKind(), item.getJpn(), item.getJlb()));
        }
    }

    class labelEvent implements MouseListener {

        private JPanel node;
        private String kind;
        private JPanel jpnItem;
        private JLabel jlbItem;

        public labelEvent(String kind, JPanel jpnItem, JLabel jlbItem) {
            this.kind = kind;
            this.jpnItem = jpnItem;
            this.jlbItem = jlbItem;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            switch (kind) {
                case "HomePageAdmin":
                    node = new HomePageAdmin();
                    break;
                case "Products":
                    node = new Products();
                    break;
                case "Staff":
                    node = new Staffs();
                    break;
                case "Categories":
                    node = new Categories();
                    break;
                case "Suppliers":
                    node = new Suppliers();
                    break;
                case "Promotion":
                    node = new Promotions();
                    break;
                case "Shippers":
                    node = new Shippers();
                    break;
                case "Reports":
                    node = new Reports();
                    break;
                case "HomePageStaff":
                    node = new HomePageStaff();
                    break;
                case "Personal":
                    node = new Users();
                    break;
                case "Password":
                    node = new ChangePassword();
                    break;
                case "Customer":
                    node = new Customers();
                    break;
                case "Order":
                    node = new Orders();
                    break;
                default:
                    break;
            }
            root.removeAll();;
            root.setLayout(new BorderLayout());
            root.add(node);
            root.validate();
            root.repaint();
            setchangeBackgroud(kind);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            kindSelected = kind;
            jpnItem.setBackground(new Color(40, 77, 0));
            jlbItem.setBackground(new Color(40, 77, 0));
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            jpnItem.setBackground(new Color(40, 77, 0));
            jlbItem.setBackground(new Color(40, 77, 0));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (!kindSelected.equalsIgnoreCase(kind)) {
                jpnItem.setBackground(new Color(133, 173, 173));
                jlbItem.setBackground(new Color(133, 173, 173));
            }
        }

    }

    private void setchangeBackgroud(String kind) {
        for (DanhMucBean item : listItem) {
            if (item.getKind().equalsIgnoreCase(kind)) {
                item.getJpn().setBackground(new Color(40, 77, 0));
                item.getJlb().setBackground(new Color(40, 77, 0));
            } else {
                item.getJpn().setBackground(new Color(133, 173, 173));
                item.getJlb().setBackground(new Color(133, 173, 173));
            }
        }
    }
}
