/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entities.Account;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import utils.DBUtils;
import utils.enums.AccountRole;

/**
 *
 * @author vothimaihoa
 */
public class AccountDAO {

    public Account getById(int id) {
        Account a = null;
        String sql = " SELECT id, username, role "
                + " from Account a "
                + " where id = ?";
        try {
            Connection con = DBUtils.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs != null) {
                if (rs.next()) {
                    a = new Account();
                    a.setId(rs.getInt("id"));
                    a.setUsername(rs.getString("username"));
                    a.setRole((rs.getInt("role") == 1 ? AccountRole.STAFF : AccountRole.CUSTOMER));
                }
            }
            con.close();
        } catch (ClassNotFoundException ex) {
            System.out.println("DBUtils not found.");
        } catch (SQLException ex) {
            System.out.println("SQL Exception in getting product by id. Details: ");
            ex.printStackTrace();
        }
        return a;
    }

    public Account getByUsernamePassword(String username, String password) {
        Account a = null;
        String sql = " SELECT id, username, role "
                + " from Account a "
                + " where username = ? AND password = ?";
        try {
            Connection con = DBUtils.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);

            System.out.println("Attempting login with username: " + username);

            ResultSet rs = ps.executeQuery();
            if (rs != null) {
                if (rs.next()) {
                    a = new Account();
                    a.setId(rs.getInt("id"));
                    a.setUsername(rs.getString("username"));
                    a.setRole((rs.getInt("role") == 1 ? AccountRole.STAFF : AccountRole.CUSTOMER));
                    System.out.println("Login successful for user: " + username);
                } else {
                    System.out.println("No matching user found for username: " + username);
                }
            }
            con.close();
        } catch (ClassNotFoundException ex) {
            System.out.println("DBUtils not found: " + ex.getMessage());
        } catch (SQLException ex) {
            System.out.println("SQL Exception in login. Details: ");
            ex.printStackTrace();
        }
        return a;
    }

    public boolean register(String username, String password, int role) {
        if (isUsernameExists(username)) {
            System.out.println("Username already exists: " + username);
            return false;
        }

        String sql = "INSERT INTO Account (username, password, role) VALUES (?, ?, ?)";
        try {
            Connection con = DBUtils.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setInt(3, role);

            System.out.println("Executing register with username: " + username);

            int rows = ps.executeUpdate();
            con.close();

            System.out.println("Registration result: " + (rows > 0 ? "success" : "failed"));
            return rows > 0;
        } catch (SQLException ex) {
            System.out.println("SQL Error in register: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        } catch (ClassNotFoundException ex) {
            System.out.println("Database connection error: " + ex.getMessage());
            return false;
        }
    }

    private boolean isUsernameExists(String username) {
        String sql = "SELECT COUNT(*) FROM Account WHERE username = ?";
        try {
            Connection con = DBUtils.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println("Username " + username + " exists check: " + (count > 0));
                con.close();
                return count > 0;
            }
            con.close();
        } catch (Exception ex) {
            System.out.println("Error checking username: " + ex.getMessage());
        }
        return false;
    }
}
