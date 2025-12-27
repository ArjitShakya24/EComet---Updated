package ecomet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class CartAutoSaver implements Runnable {
    private final Cart cart;
    private final ProductDAO productDAO = new ProductDAO();

    public CartAutoSaver(Cart cart) {
        this.cart = cart;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(10_000); // every 10s - adjust as needed
                saveSnapshot();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            } catch (AppException e) {
                e.printStackTrace();
            }
        }
    }

    // This method demonstrates a safe read of the cart via its synchronized API,
    // and then performs a simple "snapshot" DB insert (non-essential demo).
    private void saveSnapshot() throws AppException {
        List<CartItem> snapshot = cart.getItems(); // getItems returns defensive copy
        if (snapshot.isEmpty()) return;

        // For demo: just update stock estimates in DB if needed or log. We'll do nothing heavy.
        // (Real apps should have a snapshots table). We'll update product stock optimistically
        Connection c = null;
        try {
            c = DBConnection.getConnection();
            c.setAutoCommit(false);
            String sql = "UPDATE products SET stock = ? WHERE id = ?";
            try (PreparedStatement ps = c.prepareStatement(sql)) {
                for (CartItem it : snapshot) {
                    int remaining = it.getProduct().getStock(); // this is only demo data
                    // do not set negative stock in demo
                    if (remaining >= 0) {
                        ps.setInt(1, remaining);
                        ps.setInt(2, it.getProduct().getId());
                        ps.addBatch();
                    }
                }
                ps.executeBatch();
            }
            c.commit();
        } catch (SQLException e) {
            try { if (c != null) c.rollback(); } catch (SQLException ignored) {}
            throw new AppException("Auto-save failed: " + e.getMessage(), e);
        } finally {
            try { if (c != null) c.setAutoCommit(true); } catch (SQLException ignored) {}
            DBConnection.close(c);
        }
    }

	public ProductDAO getProductDAO() {
		return productDAO;
	}
}
