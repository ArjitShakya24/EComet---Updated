package ecomet;

import java.sql.*;

public class DBConnection {
    // Change these if your DB is different
    private static final String URL = "jdbc:mysql://localhost:3306/ecomet_db?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";    // change to your mysql user
    private static final String PASS = "12345678"; // change to your mysql password

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // MySQL 8+ driver
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws AppException {
        try {
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            throw new AppException("DB connection failed: " + e.getMessage(), e);
        }
    }

    public static void close(AutoCloseable ac) {
        if (ac == null) return;
        try { ac.close(); } catch (Exception ignored) {}
    }
}
