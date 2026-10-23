package bookstoreapp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Owner customers screen: table of customers, add/delete functionality.
 */
public class OwnerCustomersScreen extends JPanel {

    private DefaultTableModel tableModel;
    private JTable table;

    public OwnerCustomersScreen() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- TOP: Table ---
        String[] columns = {"Username", "Password", "Points"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        loadTableFromDataStore();

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // --- MIDDLE: Add fields ---
        JPanel middlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        middlePanel.add(new JLabel("Username:"));
        JTextField usernameField = new JTextField(10);
        middlePanel.add(usernameField);
        middlePanel.add(new JLabel("Password:"));
        JTextField passwordField = new JTextField(10);
        middlePanel.add(passwordField);
        JButton addBtn = new JButton("Add");
        middlePanel.add(addBtn);

        // --- BOTTOM: Delete and Back ---
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        JButton deleteBtn = new JButton("Delete");
        JButton backBtn = new JButton("Back");
        bottomPanel.add(deleteBtn);
        bottomPanel.add(backBtn);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(middlePanel, BorderLayout.NORTH);
        southPanel.add(bottomPanel, BorderLayout.SOUTH);
        add(southPanel, BorderLayout.SOUTH);

        // --- Actions ---
        addBtn.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both a username and a password.");
                return;
            }
            // Check duplicate
            List<Customer> customers = DataStore.getInstance().getCustomers();
            for (Customer c : customers) {
                if (c.getUsername().equalsIgnoreCase(username)) {
                    JOptionPane.showMessageDialog(this, "A customer with this username already exists.");
                    return;
                }
            }
            Customer newCustomer = new Customer(username, password, 0);
            DataStore.getInstance().addCustomer(newCustomer);
            tableModel.addRow(new Object[]{username, password, 0});
            usernameField.setText("");
            passwordField.setText("");
        });

        deleteBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a row to delete.");
                return;
            }
            String username = (String) tableModel.getValueAt(selectedRow, 0);
            List<Customer> customers = DataStore.getInstance().getCustomers();
            Customer toRemove = null;
            for (Customer c : customers) {
                if (c.getUsername().equals(username)) { toRemove = c; break; }
            }
            if (toRemove != null) {
                DataStore.getInstance().removeCustomer(toRemove);
            }
            tableModel.removeRow(selectedRow);
        });

        backBtn.addActionListener(e ->
            MainFrame.getInstance().showScreen(new OwnerStartScreen()));
    }

    private void loadTableFromDataStore() {
        tableModel.setRowCount(0);
        for (Customer c : DataStore.getInstance().getCustomers()) {
            tableModel.addRow(new Object[]{c.getUsername(), c.getPassword(), c.getPoints()});
        }
    }
}
