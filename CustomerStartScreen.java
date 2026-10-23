package bookstoreapp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Customer start screen: welcome message, books table with checkboxes, Buy/Redeem/Logout.
 */
public class CustomerStartScreen extends JPanel {

    private Customer customer;
    private DefaultTableModel tableModel;
    private JTable table;
    private JLabel welcomeLabel;

    public CustomerStartScreen(Customer customer) {
        this.customer = customer;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- TOP: Welcome message ---
        welcomeLabel = new JLabel(buildWelcomeMessage());
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        add(welcomeLabel, BorderLayout.NORTH);

        // --- MIDDLE: Books table with checkboxes ---
        String[] columns = {"Book Name", "Book Price", "Select"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public Class<?> getColumnClass(int col) {
                return col == 2 ? Boolean.class : Object.class;
            }
            @Override
            public boolean isCellEditable(int row, int col) {
                return col == 2;
            }
        };
        table = new JTable(tableModel);
        loadBooks();

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // --- BOTTOM: Buttons ---
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        JButton buyBtn = new JButton("Buy");
        JButton redeemBtn = new JButton("Redeem points and Buy");
        JButton logoutBtn = new JButton("Logout");
        bottomPanel.add(buyBtn);
        bottomPanel.add(redeemBtn);
        bottomPanel.add(logoutBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        // --- Actions ---
        buyBtn.addActionListener(e -> handleBuy(false));
        redeemBtn.addActionListener(e -> handleBuy(true));
        logoutBtn.addActionListener(e ->
            MainFrame.getInstance().showScreen(new LoginScreen()));
    }

    private String buildWelcomeMessage() {
        return "Welcome " + customer.getUsername() +
               ". You have " + customer.getPoints() +
               " points. Your status is " + customer.getStatus() + ".";
    }

    private void loadBooks() {
        tableModel.setRowCount(0);
        for (Book b : DataStore.getInstance().getBooks()) {
            tableModel.addRow(new Object[]{b.getName(), b.getPrice(), false});
        }
    }

    private void handleBuy(boolean redeem) {
        List<Book> selected = new ArrayList<>();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            Boolean checked = (Boolean) tableModel.getValueAt(i, 2);
            if (checked != null && checked) {
                String bookName = (String) tableModel.getValueAt(i, 0);
                double price = (double) tableModel.getValueAt(i, 1);
                selected.add(new Book(bookName, price));
            }
        }

        if (selected.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select at least one book.");
            return;
        }

        double totalCost = 0;
        for (Book b : selected) {
            totalCost += b.getPrice();
        }

        double actualCost;
        if (redeem) {
            actualCost = customer.redeemAndBuy(totalCost);
        } else {
            actualCost = totalCost;
            customer.addPoints(totalCost);
        }

        MainFrame.getInstance().showScreen(new CustomerCostScreen(customer, actualCost));
    }
}
