package ecomet;
import java.util.*;

public class Cart {

    private final List<CartItem> items = new ArrayList<>();
    private static final Cart INSTANCE = new Cart();

    private Cart() {}
    public static Cart getInstance() { return INSTANCE; }

    public synchronized void addToCart(CartItem item) {
        for (CartItem it : items) {
            if (it.getProduct().getId() == item.getProduct().getId()) {
                it.setQty(it.getQty() + item.getQty());
                return;
            }
        }
        items.add(item);
    }

    public synchronized void removeFromCart(int productId) {
        items.removeIf(it -> it.getProduct().getId() == productId);
    }

    public synchronized List<CartItem> getItems() {
        return new ArrayList<>(items);
    }

    public synchronized boolean isEmpty() { return items.isEmpty(); }

    public synchronized void clear() { items.clear(); }

    // ⭐ FINAL & CORRECT TOTAL METHOD ⭐
    public synchronized double getTotalAmount() {
        double total = 0;
        for (CartItem item : items) {
            total += item.getProduct().getPrice() * item.getQty();
        }
        return total;
    }
	public double calculateTotal() {
		// TODO Auto-generated method stub
		return 0;
	}
	// ➕ Increase quantity of a product
	public synchronized void increaseQty(int productId) {
	    for (CartItem item : items) {
	        if (item.getProduct().getId() == productId) {
	            item.setQty(item.getQty() + 1);
	            break;
	        }
	    }
	}

	// ➖ Decrease quantity of a product
	public synchronized void decreaseQty(int productId) {
	    for (CartItem item : items) {
	        if (item.getProduct().getId() == productId) {
	            if(item.getQty() > 1) {
	                item.setQty(item.getQty() - 1);
	            } else {
	                items.remove(item); // remove if 0 or below
	            }
	            break;
	        }
	    }
	}

}
