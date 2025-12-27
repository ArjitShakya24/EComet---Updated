package ecomet;

import java.sql.*;
import java.util.*;

public class ProductDAO extends AbstractDAO implements DAO<Product> {

    @Override
    public Product findById(int id) throws AppException {
        String sql = "SELECT id,name,price,stock FROM products WHERE id = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new AppException("Error finding product: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Product> findAll() throws AppException {
        List<Product> list = new ArrayList<>();
        try(Connection c = DBConnection.getConnection();
            PreparedStatement ps = c.prepareStatement("SELECT id,name,price,stock,image,tags,category FROM products");
            ResultSet rs = ps.executeQuery()) {

            while(rs.next()) {
                Product p = new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getInt("stock"),
                    rs.getString("image"),
                    rs.getString("tags"),
                    rs.getString("category")
                );
                list.add(p);
            }

        } catch(Exception e){
            throw new AppException("Error loading products: " + e.getMessage());
        }
        return list;
    }


    @Override
    public void save(Product obj) throws AppException {
        String sql = "INSERT INTO products(name,price,stock) VALUES(?,?,?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, obj.getName());
            ps.setDouble(2, obj.getPrice());
            ps.setInt(3, obj.getStock());
            ps.executeUpdate();
            try (ResultSet g = ps.getGeneratedKeys()) {
                if (g.next()) obj.setId(g.getInt(1));
            }
        } catch (SQLException e) {
            throw new AppException("Error saving product: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(Product obj) throws AppException {
        String sql = "UPDATE products SET name=?, price=?, stock=? WHERE id=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, obj.getName());
            ps.setDouble(2, obj.getPrice());
            ps.setInt(3, obj.getStock());
            ps.setInt(4, obj.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new AppException("Error updating product: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(int id) throws AppException {
        String sql = "DELETE FROM products WHERE id = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new AppException("Error deleting product: " + e.getMessage(), e);
        }
    }

    private Product mapRow(ResultSet rs) throws SQLException {
        return new Product(rs.getInt("id"), rs.getString("name"),
                rs.getDouble("price"), rs.getInt("stock"), null, null, null);
    }
}
