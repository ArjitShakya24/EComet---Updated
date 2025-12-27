package ecomet;

public class InventoryManager {

    public static boolean isLowStock(Product p){
        return p.getStock() < 5;
    }

    public static void showWarning(Product p){
        if(isLowStock(p)){
            System.out.println("⚠ LOW STOCK: " + p.getName());
        }
    }
}
