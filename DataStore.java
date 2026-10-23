package bookstoreapp;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Singleton DataStore that holds all books and customers,
 * and handles loading/saving to books.txt and customers.txt.
 */
public class DataStore {

    private static DataStore instance;
    private List<Book> books;
    private List<Customer> customers;

    private static final String BOOKS_FILE = "books.txt";
    private static final String CUSTOMERS_FILE = "customers.txt";

    private DataStore() {
        books = new ArrayList<>();
        customers = new ArrayList<>();
        loadData();   
    }

    public static DataStore getInstance() {
        if (instance == null) {
            instance = new DataStore();
        }
        return instance;
    }

    public List<Book> getBooks() { return books; }
    public List<Customer> getCustomers() { return customers; }

    public void addBook(Book b) { books.add(b); }
    public void removeBook(Book b) { books.remove(b); }

    public void addCustomer(Customer c) { customers.add(c); }
    public void removeCustomer(Customer c) { customers.remove(c); }

    /**
     * Returns the Customer with matching username & password, or null.
     */
    public Customer authenticate(String username, String password) {
        for (Customer c : customers) {
            if (c.getUsername().equals(username) && c.getPassword().equals(password)) {
                return c;
            }
        }
        return null;
    }

    /**
     * Load data from books.txt and customers.txt on startup.
     */
    public void loadData() {
        books.clear();
        customers.clear();

        // --- LOAD BOOKS ---
        File booksFile = new File(BOOKS_FILE);
        if (booksFile.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(booksFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    line = line.trim();
                    if (!line.isEmpty()) {
                        String[] parts = line.split(",");
                        if (parts.length == 2) {
                            try {
                                String name = parts[0].trim();
                                double price = Double.parseDouble(parts[1].trim());
                                books.add(new Book(name, price));
                            } catch (NumberFormatException ignored) {}
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println("Could not read " + BOOKS_FILE);
            }
        }

        // --- LOAD CUSTOMERS ---
        File customersFile = new File(CUSTOMERS_FILE);
        if (customersFile.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(customersFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    line = line.trim();
                    if (!line.isEmpty()) {
                        String[] parts = line.split(",");
                        if (parts.length == 3) {
                            try {
                                String uname = parts[0].trim();
                                String pwd = parts[1].trim();
                                int pts = Integer.parseInt(parts[2].trim());
                                customers.add(new Customer(uname, pwd, pts));
                            } catch (NumberFormatException ignored) {}
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println("Could not read " + CUSTOMERS_FILE);
            }
        }
    }

    /**
     * Save all data to books.txt and customers.txt (overwrite existing).
     */
    public void saveData() {

        // --- SAVE BOOKS ---
        try (PrintWriter pw = new PrintWriter(new FileWriter(BOOKS_FILE))) {
            for (Book b : books) {
                pw.println(b.getName() + "," + b.getPrice()); // 🔥 safer than toString()
            }
        } catch (IOException e) {
            System.err.println("Could not write " + BOOKS_FILE);
        }

        // --- SAVE CUSTOMERS ---
        try (PrintWriter pw = new PrintWriter(new FileWriter(CUSTOMERS_FILE))) {
            for (Customer c : customers) {
                pw.println(c.getUsername() + "," + c.getPassword() + "," + c.getPoints());
            }
        } catch (IOException e) {
            System.err.println("Could not write " + CUSTOMERS_FILE);
        }
    }
}