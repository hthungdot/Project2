package com.dao;

import java.util.regex.Pattern;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Common {

    public static void notify(String content, String title, int icon) {
        JOptionPane.showMessageDialog(new JFrame(), content,
                title, icon);
    }
    
    public static boolean checkNotEmpty(JTextField txt) {
        boolean test = true;
        if (txt.getText().trim().equals("")) {
            test = false;
        }
        return test;
    }

    public static boolean checkFormat(String pattern, JTextField txt) {
        boolean test = true;
        if (!Pattern.matches(pattern, txt.getText().trim())) {
            test = false;
        }
        return test;
    }
}
