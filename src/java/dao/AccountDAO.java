package dao;

import entities.Account;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import utils.DBUtils;
import utils.enums.AccountRole;
import auth.dtos.RegisterDTO;
import auth.dtos.LoginDTO;

public class AccountDAO {

    public Account getUserInfo(String username, String password) {
        Account a = null;
        String sql = "SELECT id, username, password, role FROM Account WHERE username = ? AND password = ?";

        try (Connection con = DBUtils.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    a = new Account();
                    a.setId(rs.getInt("id"));
                    a.setUsername(rs.getString("username"));
                    a.setPassword(rs.getString("password"));
                    a.setRole(rs.getInt("role") == 1 ? AccountRole.STAFF : AccountRole.CUSTOMER);
                    return a;
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error in getUserInfo: " + ex.getMessage());
        }
        return null;
    }

    public boolean register(RegisterDTO dto) {
        // First check if username exists
        if (checkUsername(dto.getUsername()) != null) {
            return false;
        }

        String sql = "INSERT INTO Account (username, password, role) VALUES (?, ?, ?)";

        try (Connection con = DBUtils.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, dto.getUsername());
            ps.setString(2, dto.getPassword());
            ps.setInt(3, dto.getRole()); // Should always be 0 (CUSTOMER) from AuthController

            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println("Error in register: " + ex.getMessage());
            return false;
        }
    }

    private Account checkUsername(String username) {
        String sql = "SELECT id, username, role FROM Account WHERE username = ?";

        try (Connection con = DBUtils.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Account account = new Account();
                    account.setId(rs.getInt("id"));
                    account.setUsername(rs.getString("username"));
                    account.setRole(rs.getInt("role") == 1 ? AccountRole.STAFF : AccountRole.CUSTOMER);
                    return account;
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error in checkUsername: " + ex.getMessage());
        }
        return null;
    }
}