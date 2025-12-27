package ecomet;

import java.awt.Color;
import java.awt.Font;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {
    public static void main(String[] args) {
    	UIManager.put("Button.arc", 20);
    	UIManager.put("Component.arc", 20);
    	UIManager.put("TextComponent.arc", 15);

    	UIManager.put("Panel.background", Color.decode("#F5F5F5"));
    	UIManager.put("Button.background", Color.decode("#4CAF50"));
    	UIManager.put("Button.foreground", Color.WHITE);
    	UIManager.put("Button.font", new Font("Segoe UI", Font.BOLD, 14));

    	UIManager.put("Label.font", new Font("Segoe UI", Font.PLAIN, 14));
    	UIManager.put("Table.font", new Font("Segoe UI", Font.PLAIN, 13));
    	UIManager.put("Table.rowHeight", 28);

        SwingUtilities.invokeLater(() -> {
            LoginGUI login = new LoginGUI();
            login.setVisible(true);
        });
    }
}
