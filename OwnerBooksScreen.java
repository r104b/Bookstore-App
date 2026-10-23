package bookstoreapp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Owner books screen: table of books, add/delete functionality.
 */
public class OwnerBooksScreen extends JPanel {

    private DefaultTableModel tableModel;
    private JTable table;

    public OwnerBooksScreen() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- TOP: Table ---
        String[] columns = {"Book Name", "Book Price"};
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
        middlePanel.add(new JLabel("Name:"));
        JTextField nameField = new JTextField(10);
        middlePanel.add(nameField);
        middlePanel.add(new JLabel("Price:"));
        JTextField priceField = new JTextField(6);
        middlePanel.add(priceField);
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
            String name = nameField.getText().trim();
            String priceText = priceField.getText().trim();
            if (name.isEmpty() || priceText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both a name and a price.");
                return;
            }
            // Check duplicate
            List<Book> books = DataStore.getInstance().getBooks();
            for (Book b : books) {
                if (b.getName().equalsIgnoreCase(name)) {
                    JOptionPane.showMessageDialog(this, "A book with this name already exists.");
                    return;
                }
            }
            try {
                double price = Double.parseDouble(priceText);
                if (price < 0) throw new NumberFormatException();
                Book newBook = new Book(name, price);
                DataStore.getInstance().addBook(newBook);
                tableModel.addRow(new Object[]{name, price});
                nameField.setText("");
                priceField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid positive price.");
            }
        });

        deleteBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a row to delete.");
                return;
            }
            String bookName = (String) tableModel.getValueAt(selectedRow, 0);
            List<Book> books = DataStore.getInstance().getBooks();
            Book toRemove = null;
            for (Book b : books) {
                if (b.getName().equals(bookName)) { toRemove = b; break; }
            }
            if (toRemove != null) {
                DataStore.getInstance().removeBook(toRemove);
            }
            tableModel.removeRow(selectedRow);
        });

        backBtn.addActionListener(e ->
            MainFrame.getInstance().showScreen(new OwnerStartScreen()));
    }

    private void loadTableFromDataStore() {
        tableModel.setRowCount(0);
        for (Book b : DataStore.getInstance().getBooks()) {
            tableModel.addRow(new Object[]{b.getName(), b.getPrice()});
        }
    }
}
