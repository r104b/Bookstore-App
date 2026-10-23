package bookstoreapp;

import javax.swing.SwingUtilities;

/**
 * This is the Main entry point for the BookStore Application.
 */
public class BookstoreApp {

    public static void main(String[] args) {
        // This one runs all GUI code on the Swing Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            // Load saved data (books and customers) from files, This ensures data persists when app restarts
            DataStore.getInstance().loadData();

// Gets the single main window (Singleton pattern), Only one JFrame exists in the entire app
// Sets the first screen the user sees → Login screen, This replaces the content inside the same window
            MainFrame frame = MainFrame.getInstance();
            frame.showScreen(new LoginScreen());
            frame.setVisible(true);
        });
    }
}
