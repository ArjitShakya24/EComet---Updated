package ecomet;

import javax.swing.*;
import java.awt.*;

public class NavigationPanel extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NavigationPanel(User user){
        setLayout(new GridLayout(8,1,5,5));
        setBackground(Color.LIGHT_GRAY);

        add(new JButton("Home"));
        add(new JButton("Products"));
        add(new JButton("Cart"));
        add(new JButton("Orders"));
        add(new JButton("Profile"));
        add(new JButton("Logout"));
    }
}
