/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Account;
import model.Category;

/**
 *
 * @author Asus
 */
public class LoginDAO extends DBContext {

    public Account login(String user, String pass) {
        String sql = "select * from Account\n"
                + "where [user] = ?\n"
                + "and pass = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, user);
            st.setString(2, pass);
            ResultSet rs = st.executeQuery();
            
            while (rs.next()) {
                return new Account(rs.getInt(1),
                        rs.getString(2), 
                        rs.getString(3), 
                        rs.getInt(4), 
                        rs.getInt(5));
            }   
        } catch (SQLException e) {
            System.out.println(e);
        }
       
        return null;
    }
    public Account checkAccountExist(String user) {
        String sql = "select * from Account\n"
                + "where [user] = ?\n";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, user);
            ResultSet rs = st.executeQuery();
            
            while (rs.next()) {
                return new Account(rs.getInt(1),
                        rs.getString(2), 
                        rs.getString(3), 
                        rs.getInt(4), 
                        rs.getInt(5));
            }   
        } catch (SQLException e) {
            System.out.println(e);
        }
       
        return null;
    }
}
