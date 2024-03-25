package onlineshopping;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ShoppingGUI extends JFrame {

    private WestminsterShoppingManager shoppingManager;
    private JComboBox<String> categoryComboBox;
    private JTable productTable;
    private JTextArea productDetailsTextArea;
    private JButton addToCartButton;
    private JButton viewCartButton;
    private JPanel bottomPanel;
    private ShoppingCart shoppingCart;

    public ShoppingGUI(WestminsterShoppingManager manager) {
        this.shoppingManager = manager;
        this.shoppingCart = new ShoppingCart();
        setTitle("Westminster Shopping Centre");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(800, 600);

        initializeComponents();

        JPanel topPanel = setupTopPanel();
        JPanel centerPanel = setupCenterPanel();
        bottomPanel = setupBottomPanel();

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeComponents() {
        categoryComboBox = new JComboBox<>(new String[]{"All", "Electronics", "Clothing"});
        categoryComboBox.setPreferredSize(new Dimension(200, 25));
        categoryComboBox.addActionListener(e -> updateProductTable((String) categoryComboBox.getSelectedItem()));

        String[] columns = new String[]{"Product ID", "Name", "Category", "Price(£)", "Info"};
        productTable = new JTable(new DefaultTableModel(new Object[][]{}, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table cells non-editable
            }
        });
        productTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        productTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateProductDetails();
            }
        });

        productTable.setDefaultRenderer(Object.class, new StockCellRenderer(shoppingManager));

        addToCartButton = new JButton("Add to Shopping Cart");
        addToCartButton.addActionListener(e -> addSelectedProductToCart());

        viewCartButton = new JButton("Shopping Cart");
        viewCartButton.addActionListener(e -> viewShoppingCart());
    }

    private JPanel setupTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel categoryPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 150, 5));
        categoryPanel.add(new JLabel("Selected Product Category"));
        categoryPanel.add(categoryComboBox);

        JPanel cartPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        cartPanel.add(viewCartButton);
        topPanel.add(categoryPanel, BorderLayout.CENTER);
        topPanel.add(cartPanel, BorderLayout.EAST);

        return topPanel;
    }

    private JPanel setupCenterPanel() {
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(new JScrollPane(productTable), BorderLayout.CENTER);
        return centerPanel;
    }

    private JPanel setupBottomPanel() {
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        productDetailsTextArea = new JTextArea(20, 50);
        productDetailsTextArea.setEditable(false);
        bottomPanel.add(new JScrollPane(productDetailsTextArea));

        JPanel addToCartPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addToCartPanel.add(addToCartButton);
        bottomPanel.add(addToCartPanel);

        return bottomPanel;
    }

    private void updateProductTable(String category) {
        DefaultTableModel model = (DefaultTableModel) productTable.getModel();
        model.setRowCount(0);

        for (Product product : shoppingManager.getAllProducts()) {
            if (category.equals("All") || product.getCategory().equalsIgnoreCase(category)) {
                String info = getProductInfo(product);
                model.addRow(new Object[]{
                        product.getProductID(),
                        product.getProductName(),
                        product.getCategory(),
                        String.format("%.2f", product.getPrice()),
                        info
                });
            }
        }
    }

    private String getProductInfo(Product product) {
        if (product instanceof Electronics) {
            Electronics elec = (Electronics) product;
            return elec.getBrand() + ", " + elec.getWarrantyPeriod() + " Weeks Warranty";
        } else if (product instanceof Clothing) {
            Clothing cloth = (Clothing) product;
            return cloth.getSize() + ", " + cloth.getColor();
        }
        return "";
    }

    private void updateProductDetails() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow >= 0) {
            String productID = (String) productTable.getValueAt(selectedRow, 0);
            Product selectedProduct = shoppingManager.getProductById(productID);
            if (selectedProduct != null) {
                productDetailsTextArea.setText(getProductDetails(selectedProduct));
            }
        } else {
            productDetailsTextArea.setText("");
        }
    }

    private String getProductDetails(Product product) {
        if (product instanceof Electronics) {
            Electronics elec = (Electronics) product;
            return String.format("Product ID: %s\nName: %s\nCategory: %s\nBrand: %s\nWarranty: %s Weeks\nPrice: %.2f\nItems In Stock: %d",
                    elec.getProductID(), elec.getProductName(), "Electronics", elec.getBrand(), elec.getWarrantyPeriod(), elec.getPrice(), elec.getInStock());
        } else if (product instanceof Clothing) {
            Clothing cloth = (Clothing) product;
            return String.format("Product ID: %s\nName: %s\nCategory: %s\nSize: %s\nColor: %s\nPrice: %.2f\nItems In Stock: %d",
                    cloth.getProductID(), cloth.getProductName(), "Clothing", cloth.getSize(), cloth.getColor(), cloth.getPrice(), cloth.getInStock());
        }
        return "Product details not available";
    }

    private void addSelectedProductToCart() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow != -1) {
            String productID = (String) productTable.getValueAt(selectedRow, 0);
            Product selectedProduct = shoppingManager.getProductById(productID);
            if (selectedProduct != null) {
                shoppingCart.addProductToCart(selectedProduct);
                JOptionPane.showMessageDialog(this, selectedProduct.getProductName() + " added to cart.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a product.");
        }
    }

    private void viewShoppingCart() {
        ShoppingCartGUI shoppingCartGUI = new ShoppingCartGUI(this, shoppingCart);
        shoppingCartGUI.setVisible(true);
    }

    class StockCellRenderer extends DefaultTableCellRenderer {
        private WestminsterShoppingManager manager;

        public StockCellRenderer(WestminsterShoppingManager manager) {
            this.manager = manager;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            String productID = (String) table.getValueAt(row, 0); // Assuming the first column is Product ID
            Product product = manager.getProductById(productID);
            if (product != null && product.getInStock() < 3) {
                c.setForeground(Color.RED);
            } else {
                c.setForeground(Color.BLACK);
            }
            return c;
        }
    }
}






    

