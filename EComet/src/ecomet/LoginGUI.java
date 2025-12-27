package ecomet;

import javax.swing.*;
import java.awt.*;

public class LoginGUI extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final UserDAO userDAO = new UserDAO();

    public LoginGUI() {
        super("E-Comet - Login");
        setSize(420,260);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        init();
    }

    private void init() {
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8,8,8,8);
        c.gridx = 0; c.gridy = 0; c.anchor = GridBagConstraints.EAST;
        p.add(new JLabel("Username:"), c);
        c.gridy++; p.add(new JLabel("Password:"), c);

        c.gridx = 1; c.gridy = 0; c.anchor = GridBagConstraints.WEST;
        JTextField usernameField = new JTextField(16);
        JPasswordField passwordField = new JPasswordField(16);
        p.add(usernameField, c);
        c.gridy++; p.add(passwordField, c);

        JButton loginBtn = new JButton("Login");
        JButton registerBtn = new JButton("Register");
        JPanel btns = new JPanel();
        btns.add(loginBtn); btns.add(registerBtn);
        c.gridx = 0; c.gridy++; c.gridwidth = 2; c.anchor = GridBagConstraints.CENTER;
        p.add(btns, c);

        add(p);

        loginBtn.addActionListener(e -> {
            String user = usernameField.getText().trim();
            String pass = new String(passwordField.getPassword()).trim();
            if (user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter username and password.");
                return;
            }
            try {
                User u = userDAO.findByUsernameAndPassword(user, pass);
                if (u == null) {
                    JOptionPane.showMessageDialog(this, "Invalid credentials.");
                } else {
                    // success - open main GUI with user
                    SwingUtilities.invokeLater(() -> {
                        dispose();
                        try {
                            ECometGUI gui = new ECometGUI(u);
                            gui.init();
                        } catch (AppException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Unable to start app: " + ex.getMessage());
                        }
                    });
                }
            } catch (AppException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        registerBtn.addActionListener(e -> {
            String user = usernameField.getText().trim();
            String pass = new String(passwordField.getPassword()).trim();
            if (user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Provide username and password to register.");
                return;
            }
            String email = JOptionPane.showInputDialog(this, "Enter email (optional):", "");
            try {
                userDAO.createUser(user, email == null ? "" : email, pass);
                JOptionPane.showMessageDialog(this, "User created. You can login now.");
            } catch (AppException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Registration error: " + ex.getMessage());
            }
        });

        // Enter key to login by default
        getRootPane().setDefaultButton(loginBtn);
    }
}
