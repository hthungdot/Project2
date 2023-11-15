package com.dao;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class Dao_ForgotPass {

    private static final Connection con = Dao_Login.conn;
    private static PreparedStatement pst;
    private static ResultSet rs;
    static final String STR = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static SecureRandom rnd = new SecureRandom();
    private static String role;

    public static String randomPass(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(STR.charAt(rnd.nextInt(STR.length())));
        }
        System.out.println("Random Password");
        return sb.toString();
    }

    public static void sendMail(String emailAddress, String emailMessage) throws MessagingException {

        final String username = "fairdealfurnituresys@gmail.com";
        final String password = "fairdealgr3";

        Properties p = new Properties();
        p.put("mail.smtp.user", "username");
        p.put("mail.smtp.host", "smtp.gmail.com");
        p.put("mail.smtp.port", "465");
        p.put("mail.smtp.starttls.enable", "true");
        p.put("mail.smtp.debug", "true");
        p.put("mail.smtp.EnableSSL.enable", "true");
        p.put("mail.smtp.auth", "true");
        p.setProperty("mail.smtp.socketFactory.port", "465");
        p.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        p.setProperty("mail.smtp.socketFactory.fallback", "false");
        p.setProperty("mail.smtp.port", "465");

        Session s = Session.getInstance(p, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        s.setDebug(true);

        try {
            Message m = new MimeMessage(s);
            m.setFrom(new InternetAddress(username));
            m.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailAddress));
            m.setSubject("The new password from Fair Deal Furniture Shop");
            m.setText("Your new password: " + emailMessage);
            Transport.send(m);
        } catch (AddressException ex) {
            Logger.getLogger(Process.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean checkUser(String username, String email) {
        try {
            String sql = "SELECT * FROM Admin WHERE Username = ? AND Email = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, email);
            ResultSet rs = pst.executeQuery();
            int count = 0;
            while (rs.next()) {
                count++;
            }
            if (count == 1) {
                role = "admin";
                return true;
            } else {
                String sql1 = "SELECT * FROM Staffs WHERE Username = ? AND Email = ? AND Status = ?";
                pst = con.prepareStatement(sql1);
                pst.setString(1, username);
                pst.setString(2, email);
                pst.setString(3, "Enable");
                ResultSet rs1 = pst.executeQuery();
                count = 0;
                while (rs1.next()) {
                    count++;
                }
                if (count == 1) {
                    role = "staff";
                    System.out.println("Staff");
                    return true;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Process.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean updatePassword(String username, String email) {
        if (role.equals("admin")) {
            String sql = "UPDATE Admin SET PassWord = ? WHERE UserName = ? AND Email = ?";
            try {
                pst = con.prepareStatement(sql);
                String password = randomPass(6);
                pst.setString(1, password);
                pst.setString(1, username);
                pst.setString(3, email);
                int row = pst.executeUpdate();
                if (row != 0) {
                    sendMail(email, password);
                }
                return true;
            } catch (SQLException | MessagingException ex) {
                Logger.getLogger(Process.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (role.equals("staff")) {
            String sql = "UPDATE Staffs SET PassWord = ? WHERE UserName = ? AND Email = ?";
            try {
                pst = con.prepareStatement(sql);
                String password = randomPass(6);
                pst.setString(1, password);
                pst.setString(2, username);
                pst.setString(3, email);
                int row = pst.executeUpdate();
                if (row != 0) {
                    sendMail(email, password);
                }
                return true;
            } catch (SQLException | MessagingException ex) {
                Logger.getLogger(Process.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    public static String excuteForgotPassword(String username, String email) {
        if (checkUser(username, email)) {
            return updatePassword(username, email) ? "success" : "error";
        }
        return "fail";
    }
}
