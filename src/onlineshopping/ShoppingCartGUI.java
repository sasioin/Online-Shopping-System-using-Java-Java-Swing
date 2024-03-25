package onlineshopping;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.Map;
import java.util.HashMap;

public class ShoppingCartGUI extends JDialog {
    private ShoppingCart shoppingCart;

    public ShoppingCartGUI(Frame owner, ShoppingCart shoppingCart) {
        super(owner, "Shopping Cart", true);
        this.shoppingCart = shoppingCart;
        initializeComponents();
        pack();
        setLocationRelativeTo(owner);
    }

    private void initializeComponents() {
        setLayout(new BorderLayout());
        String[] columnNames = {"Product Details", "Quantity", "Price(£)"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        double subtotal = 0.0;
        Map<String, Integer> categoryCounts = new HashMap<>(); // To count items in each category
        for (Map.Entry<Product, Integer> entry : shoppingCart.getProductsInCart().entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            double price = product.getPrice() * quantity;
            subtotal += price;

            String productDetails = getProductDetails(product);
            tableModel.addRow(new Object[]{productDetails, quantity, String.format("%.2f", price)});


            categoryCounts.merge(product.getCategory(), quantity, Integer::sum);
        }

        JTable cartTable = new JTable(tableModel);
        cartTable.getColumnModel().getColumn(0).setCellRenderer(new TextAreaRenderer());
        add(new JScrollPane(cartTable), BorderLayout.CENTER);


        double firstPurchaseDiscount = subtotal * 0.10; // 10% discount


        double categoryDiscount = 0.0;
        for (Integer count : categoryCounts.values()) {
            if (count >= 3) {
                categoryDiscount = subtotal * 0.20; // 20% discount
                break;
            }
        }

        double finalTotal = subtotal - firstPurchaseDiscount - categoryDiscount;

        JPanel totalPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER; // end row
        gbc.weightx = 1; // distribute space
        gbc.anchor = GridBagConstraints.LINE_END; // right align
        gbc.insets = new Insets(5, 0, 5, 10); // top, left, bottom, right padding


        JLabel subtotalLabel = new JLabel("Total: £" + String.format("%.2f", subtotal));
        JLabel firstPurchaseDiscountLabel = new JLabel("First Purchase Discount (10%): -£" + String.format("%.2f", firstPurchaseDiscount));
        JLabel categoryDiscountLabel = new JLabel("Three Items in Same Category Discount (20%): -£" + String.format("%.2f", categoryDiscount));
        JLabel finalTotalLabel = new JLabel("Final Total: £" + String.format("%.2f", finalTotal));


        totalPanel.add(subtotalLabel, gbc);
        totalPanel.add(firstPurchaseDiscountLabel, gbc);
        totalPanel.add(categoryDiscountLabel, gbc);
        totalPanel.add(finalTotalLabel, gbc);


        add(totalPanel, BorderLayout.SOUTH);
    }

    private String getProductDetails(Product product) {
    if (product instanceof Electronics) {
        Electronics elec = (Electronics) product;
        return String.format("%s\n%s\n%s, %d Weeks Warranty",
                             elec.getProductID(), elec.getProductName(), elec.getBrand(), elec.getWarrantyPeriod());
    } else if (product instanceof Clothing) {
        Clothing cloth = (Clothing) product;
        return String.format("%s\n%s\n%s, %s",
                             cloth.getProductID(), cloth.getProductName(), cloth.getSize(), cloth.getColor());
    }
    return "Unknown Product Type";
}


    static class TextAreaRenderer extends JTextArea implements TableCellRenderer {
        public TextAreaRenderer() {
            setLineWrap(true);
            setWrapStyleWord(true);
            setEditable(false);
            setBorder(null);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            setSize(table.getColumnModel().getColumn(column).getWidth(), getPreferredSize().height);
            if (table.getRowHeight(row) < getPreferredSize().height) {
                table.setRowHeight(row, getPreferredSize().height);
            }
            return this;
        }
    }
}
