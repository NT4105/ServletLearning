package dao;

import entities.Account;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import utils.DBUtils;
import utils.enums.AccountRole;

public class AccountDAO {

    public Account getUserInfo(String username, String password) {
        Account a = null;
        String sql = "SELECT id, username, password, role FROM Account WHERE username = ?";

        try {
            Connection con = DBUtils.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);

            System.out.println("Checking account for username: " + username);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                // Username exists, check password
                String storedPassword = rs.getString("password");
                if (password.equals(storedPassword)) {
                    // Password matches, create Account object
                    a = new Account();
                    a.setId(rs.getInt("id"));
                    a.setUsername(rs.getString("username"));
                    a.setRole(rs.getInt("role") == 1 ? AccountRole.STAFF : AccountRole.CUSTOMER);
                    System.out.println("Login successful for user: " + username);
                } else {
                    System.out.println("Invalid password for user: " + username);
                }
            } else {
                System.out.println("Username not found: " + username);
            }
            con.close();
        } catch (SQLException ex) {
            System.out.println("SQL Exception in login. Details: ");
            ex.printStackTrace();
        }
        return a;
    }

    public boolean register(String username, String password, int role) {
        // First check if username exists
        Account existingAccount = checkUsername(username);
        if (existingAccount != null) {
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
        }
    }

    // Helper method to check if username exists
    private Account checkUsername(String username) {
        Account account = null;
        String sql = "SELECT id, username, password, role FROM Account WHERE username = ?";
        try {
            Connection con = DBUtils.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                account = new Account();
                account.setId(rs.getInt("id"));
                account.setUsername(rs.getString("username"));
                account.setPassword(rs.getString("password"));
                account.setRole(rs.getInt("role") == 1 ? AccountRole.STAFF : AccountRole.CUSTOMER);
            }
            con.close();
        } catch (SQLException ex) {
            System.out.println("Error checking username: " + ex.getMessage());
        }
        return account;
    }
}