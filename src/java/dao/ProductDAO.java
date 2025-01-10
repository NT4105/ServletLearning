package dao;

import entities.Category;
import entities.Product;
import exceptions.InvalidDataException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import product.dtos.SearchProductDTO;
import product.dtos.DeleteProductDTO;
import product.dtos.CreateProductDTO;
import product.dtos.UpdateProductDTO;
import utils.DBUtils;

public class ProductDAO {

    private String constructSearchQuery(SearchProductDTO searchDTO) {
        StringBuilder sql = new StringBuilder(
                "SELECT p.id, p.name, p.price, p.product_year, p.image, p.category_id, c.name as category_name " +
                        "FROM Product p JOIN Category c ON p.category_id = c.id WHERE 1=1");

        if (searchDTO.getCategoryId() != null) {
            sql.append(" AND p.category_id = ?");
        }

        if (searchDTO.getProductName() != null && !searchDTO.getProductName().trim().isEmpty()) {
            sql.append(" AND p.name LIKE ?");
        }

        if (searchDTO.getPrice() != null) {
            switch (searchDTO.getFilterBy()) {
                case "equal":
                    sql.append(" AND p.price = ?");
                    break;
                case "greater":
                    sql.append(" AND p.price > ?");
                    break;
                case "less":
                    sql.append(" AND p.price < ?");
                    break;
                default:
                    throw new InvalidDataException("Invalid filter type");
            }
        }

        return sql.toString();
    }

    public List<Product> search(SearchProductDTO searchDTO) {
        List<Product> list = new ArrayList<>();
        String sql = constructSearchQuery(searchDTO);

        try (Connection con = DBUtils.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            int paramIndex = 1;

            if (searchDTO.getCategoryId() != null) {
                ps.setInt(paramIndex++, searchDTO.getCategoryId());
            }

            if (searchDTO.getProductName() != null && !searchDTO.getProductName().trim().isEmpty()) {
                ps.setString(paramIndex++, "%" + searchDTO.getProductName() + "%");
            }

            if (searchDTO.getPrice() != null) {
                ps.setFloat(paramIndex, searchDTO.getPrice());
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Product p = new Product();
                    p.setId(rs.getInt("id"));
                    p.setName(rs.getString("name"));
                    p.setPrice(rs.getFloat("price"));
                    p.setProductYear(rs.getInt("product_year"));
                    p.setImage(rs.getString("image"));

                    Category category = new Category(
                            rs.getInt("category_id"),
                            rs.getString("category_name"));
                    p.setCategory(category);
                    list.add(p);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Error searching products: " + ex.getMessage());
        }

        return list;
    }

    public Product getById(int id) {
        String sql = "SELECT p.id, p.name, p.price, p.product_year, p.image, " +
                "p.category_id, c.name as category_name " +
                "FROM Product p JOIN Category c ON p.category_id = c.id " +
                "WHERE p.id = ?";

        try (Connection con = DBUtils.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Product p = new Product();
                    p.setId(rs.getInt("id"));
                    p.setName(rs.getString("name"));
                    p.setPrice(rs.getFloat("price"));
                    p.setProductYear(rs.getInt("product_year"));
                    p.setImage(rs.getString("image"));

                    Category category = new Category(
                            rs.getInt("category_id"),
                            rs.getString("category_name"));
                    p.setCategory(category);
                    return p;
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Error getting product by ID: " + ex.getMessage());
        }
        return null;
    }

    public boolean create(CreateProductDTO dto) {
        String sql = "INSERT INTO Product(name, price, product_year, image, category_id) " +
                "VALUES(?, ?, ?, ?, ?)";

        try (Connection con = DBUtils.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, dto.getName());
            ps.setFloat(2, dto.getPrice());
            ps.setInt(3, dto.getProductYear());
            ps.setString(4, dto.getImage());
            ps.setInt(5, dto.getCategoryId());

            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new RuntimeException("Error creating product: " + ex.getMessage());
        }
    }

    public boolean update(UpdateProductDTO dto) {
        String sql = "UPDATE Product SET name=?, price=?, product_year=?, " +
                "image=?, category_id=? WHERE id=?";

        try (Connection con = DBUtils.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, dto.getName());
            ps.setFloat(2, dto.getPrice());
            ps.setInt(3, dto.getProductYear());
            ps.setString(4, dto.getImage());
            ps.setInt(5, dto.getCategoryId());
            ps.setInt(6, dto.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new RuntimeException("Error updating product: " + ex.getMessage());
        }
    }

    public boolean delete(DeleteProductDTO dto) {
        String sql = "DELETE FROM Product WHERE id=?";

        try (Connection con = DBUtils.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, dto.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new RuntimeException("Error deleting product: " + ex.getMessage());
        }
    }
}
