package ecomet;

import java.sql.*;
import java.util.List;

@SuppressWarnings("unused")
public class OrderDAO {

    public void createOrder(int userId, Cart cart) throws AppException {
        String insertOrder = "INSERT INTO orders(user_id, total) VALUES (?, ?)";
        String insertItem = "INSERT INTO order_items(order_id, product_id, qty, price) VALUES (?,?,?,?)";
        String updateStock = "UPDATE products SET stock = stock - ? WHERE id = ?";

        Connection c = null;
        try {
            c = DBConnection.getConnection();
            c.setAutoCommit(false);

            double total = cart.calculateTotal();
            try (PreparedStatement psOrder = c.prepareStatement(insertOrder, Statement.RETURN_GENERATED_KEYS)) {
                psOrder.setInt(1, userId);
                psOrder.setDouble(2, total);
                psOrder.executeUpdate();
                ResultSet g = psOrder.getGeneratedKeys();
                if (!g.next()) throw new AppException("Order creation failed (no ID).");
                int orderId = g.getInt(1);

                try (PreparedStatement psItem = c.prepareStatement(insertItem);
                     PreparedStatement psStock = c.prepareStatement(updateStock)) {

                    for (CartItem it : cart.getItems()) {
                        psItem.setInt(1, orderId);
                        psItem.setInt(2, it.getProduct().getId());
                        psItem.setInt(3, it.getQty());
                        psItem.setDouble(4, it.getProduct().getPrice());
                        psItem.addBatch();

                        psStock.setInt(1, it.getQty());
                        psStock.setInt(2, it.getProduct().getId());
                        psStock.addBatch();
                    }
                    psItem.executeBatch();
                    psStock.executeBatch();
                }
            }
            c.commit();
        } catch (SQLException e) {
            try { if (c != null) c.rollback(); } catch (SQLException ex) {}
            throw new AppException("Failed to create order: " + e.getMessage(), e);
        } finally {
            try { if (c != null) c.setAutoCommit(true); } catch (SQLException ignored) {}
            DBConnection.close(c);
        }
    }
}
