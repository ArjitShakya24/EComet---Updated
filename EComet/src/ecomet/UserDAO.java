package ecomet;

import java.sql.*;

public class UserDAO {

    public User findByUsernameAndPassword(String username, String password) throws AppException {
        String sql = "SELECT id,username,email,password FROM users WHERE username = ? AND password = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(rs.getInt("id"), rs.getString("username"),
                            rs.getString("email"), rs.getString("password"));
                }
                return null;
            }
        } catch (SQLException e) {
            throw new AppException("Login failed: " + e.getMessage(), e);
        }
    }

    public User createUser(String username, String email, String password) throws AppException {
        String sql = "INSERT INTO users(username,email,password) VALUES (?,?,?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, username);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.executeUpdate();
            try (ResultSet g = ps.getGeneratedKeys()) {
                if (g.next()) {
                    return new User(g.getInt(1), username, email, password);
                }
            }
            throw new AppException("Could not create user.");
        } catch (SQLException e) {
            throw new AppException("Create user failed: " + e.getMessage(), e);
        }
    }
}
