package bookstoreapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * The single application window. All screens are shown inside this one JFrame
 * by swapping the content pane.
 */
public class MainFrame extends JFrame {

    private static MainFrame instance;

    private MainFrame() {
        setTitle("Bookstore App");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        // Save data when user clicks [x]
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                DataStore.getInstance().saveData();
                System.exit(0);
            }
        });
    }

    public static MainFrame getInstance() {
        if (instance == null) {
            instance = new MainFrame();
        }
        return instance;
    }

    /**
     * Replaces the current screen with a new panel.
     */
    public void showScreen(JPanel panel) {
        setContentPane(panel);
        revalidate();
        repaint();
    }
}
