package ecomet;

import javax.swing.*;
import java.awt.*;

public class DashboardGUI extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DashboardGUI(User user){
        super("E-Comet | Dashboard");
        setSize(900,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        NavigationPanel side = new NavigationPanel(user);
        add(side, BorderLayout.WEST);

        JLabel title = new JLabel("Welcome to E-Comet Dashboard", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        add(title, BorderLayout.CENTER);

        setVisible(true);
    }
}
