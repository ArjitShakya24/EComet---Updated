package ecomet;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ECometGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private final ProductDAO productDAO = new ProductDAO();
    private final OrderDAO orderDAO = new OrderDAO();
    private final Cart cart = Cart.getInstance();
    private final User currentUser;

    private JTextArea cartArea;
    private JPanel gridPanel;

    public ECometGUI(User user) {
        super("E-Comet | Product Catalog");
        this.currentUser = user;
        setSize(1100, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void init() throws AppException {

        // 🌈 Modern UI Look
        UIManager.put("Button.arc", 20);
        UIManager.put("Component.arc", 20);
        UIManager.put("TextComponent.arc", 15);
        UIManager.put("Panel.background", Color.decode("#F5F5F5"));
        UIManager.put("Button.background", Color.decode("#4CAF50"));
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Label.font", new Font("Segoe UI", Font.PLAIN, 14));

        setLayout(new BorderLayout());

        // 🔍 Search Panel
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField search = new JTextField(20);
        JButton searchBtn = new JButton("Search");
        JPopupMenu suggestionMenu = new JPopupMenu();
        topPanel.add(new JLabel("Search:"));
        topPanel.add(search);
        topPanel.add(searchBtn);
        add(topPanel, BorderLayout.NORTH);

        // 🔎 Live Suggestions While Typing
        search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) {
                suggestionMenu.removeAll();
                String text = search.getText().toLowerCase();

                if(text.isEmpty()){ suggestionMenu.setVisible(false); return; }

                try {
                    for(Product p : productDAO.findAll()){
                        if(p.getName().toLowerCase().contains(text)){
                            JMenuItem item = new JMenuItem(p.getName());
                            item.addActionListener(ev -> {
                                search.setText(p.getName());
                                updateGrid(List.of(p));
                                suggestionMenu.setVisible(false);
                            });
                            suggestionMenu.add(item);
                        }
                    }
                    suggestionMenu.show(search, 0, search.getHeight());
                } catch(Exception ex){ ex.printStackTrace(); }
            }
        });

        // 🛒 Right Cart Panel
        JPanel right = new JPanel(new BorderLayout());
        cartArea = new JTextArea(14, 25);
        cartArea.setEditable(false);

        JLabel userLabel = new JLabel("User: " + (currentUser != null ? currentUser.getUsername() : "Guest"));
        right.add(userLabel, BorderLayout.NORTH);
        right.add(new JScrollPane(cartArea), BorderLayout.CENTER);

        // Buttons
        JButton checkoutBtn = new JButton("Checkout");
        JButton refreshBtn = new JButton("Refresh");
        JButton increaseBtn = new JButton("+");
        JButton decreaseBtn = new JButton("-");

        JPanel actions = new JPanel();
        actions.add(increaseBtn);
        actions.add(decreaseBtn);
        actions.add(checkoutBtn);
        actions.add(refreshBtn);
        right.add(actions, BorderLayout.SOUTH);

        add(right, BorderLayout.EAST);

        // ⭐ PRODUCT GRID
        gridPanel = new JPanel(new GridLayout(0, 3, 15, 15));
        JScrollPane gridScroll = new JScrollPane(gridPanel);
        add(gridScroll, BorderLayout.CENTER);

        updateGrid(productDAO.findAll());

        // 🔍 Search button action
        searchBtn.addActionListener(e -> {
            try {
                updateGrid(SearchService.search(productDAO.findAll(), search.getText()));
            } catch(Exception ex){ ex.printStackTrace(); }
        });

        // 🧾 Checkout (NO COUPON)
        checkoutBtn.addActionListener(e -> {
            if(cart.isEmpty()){
                JOptionPane.showMessageDialog(this,"Cart is empty");
                return;
            }
            double total = cart.getTotalAmount();
            JOptionPane.showMessageDialog(this, "Final Amount to Pay: ₹" + total);
        });

        // ➕ Increase quantity
        increaseBtn.addActionListener(e -> {
            String id = JOptionPane.showInputDialog("Enter Product ID to increase qty:");
            if(id!=null){
                cart.increaseQty(Integer.parseInt(id.trim()));
                refreshCartArea();
            }
        });

        // ➖ Decrease quantity
        decreaseBtn.addActionListener(e -> {
            String id = JOptionPane.showInputDialog("Enter Product ID to decrease qty:");
            if(id!=null){
                cart.decreaseQty(Integer.parseInt(id.trim()));
                refreshCartArea();
            }
        });

        // ♻ Refresh
        refreshBtn.addActionListener(e -> {
            try { updateGrid(productDAO.findAll()); }
            catch(Exception ex){ ex.printStackTrace(); }
        });

        // Auto-update UI
        new Timer(500, e -> refreshCartArea()).start();

        setVisible(true);
    }

    // 🟢 PRODUCT GRID CARDS
    private void updateGrid(List<Product> products) {
        gridPanel.removeAll();

        for(Product p : products){
            JPanel card = new JPanel(){
                /**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				protected void paintComponent(Graphics g){
                    super.paintComponent(g);
                    g.setColor(Color.WHITE);
                    g.fillRoundRect(0,0,getWidth(),getHeight(),20,20);
                }
            };
            card.setLayout(new BorderLayout());
            card.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

            // 🖼 Image
            try{
                ImageIcon icon = new ImageIcon("images/" + p.getImage());
                Image img = icon.getImage().getScaledInstance(120,120,Image.SCALE_SMOOTH);
                card.add(new JLabel(new ImageIcon(img), JLabel.CENTER), BorderLayout.NORTH);
            } catch(Exception ignored){}

            // Name & Price
            JPanel infoPanel = new JPanel();
            infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
            infoPanel.setOpaque(false);

            JLabel name = new JLabel(p.getName(), JLabel.CENTER);
            name.setFont(new Font("Segoe UI", Font.BOLD, 14));
            name.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel price = new JLabel("₹ " + p.getPrice(), JLabel.CENTER);
            price.setForeground(Color.decode("#2E7D32"));
            price.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            price.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel idLabel = new JLabel("ID: " + p.getId(), JLabel.CENTER);
            idLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            idLabel.setForeground(Color.GRAY);
            idLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            infoPanel.add(name);
            infoPanel.add(price);
            infoPanel.add(idLabel);  // 👈 NEW LINE HERE


            card.add(infoPanel, BorderLayout.CENTER);

            JButton addBtn = new JButton("Add to Cart");
            addBtn.addActionListener(e -> {
                cart.addToCart(new CartItem(p, 1));
                refreshCartArea();
                JOptionPane.showMessageDialog(this,p.getName()+" added to cart");
            });
            card.add(addBtn, BorderLayout.SOUTH);

            gridPanel.add(card);
        }

        gridPanel.revalidate();
        gridPanel.repaint();
    }

    // 🛒 Update Cart Text Display
    private void refreshCartArea() {
        StringBuilder sb = new StringBuilder();

        for (CartItem it : cart.getItems()) {
            sb.append(it.getProduct().getName())
              .append(" x").append(it.getQty())
              .append(" = ₹").append(it.getProduct().getPrice() * it.getQty())
              .append("\n");
        }

        sb.append("\nTotal: ₹").append(cart.getTotalAmount());
        cartArea.setText(sb.toString());
    }

	public OrderDAO getOrderDAO() {
		return orderDAO;
	}
}
