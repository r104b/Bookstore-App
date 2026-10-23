package bookstoreapp;

import javax.swing.*;
import java.awt.*;

/**
 * Owner start screen: [Books], [Customers], [Logout] buttons.
 */
public class OwnerStartScreen extends JPanel {

    public OwnerStartScreen() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        JButton booksBtn = new JButton("Books");
        JButton customersBtn = new JButton("Customers");
        JButton logoutBtn = new JButton("Logout");

        gbc.gridy = 0; add(booksBtn, gbc);
        gbc.gridy = 1; add(customersBtn, gbc);
        gbc.gridy = 2; add(logoutBtn, gbc);

        booksBtn.addActionListener(e ->
            MainFrame.getInstance().showScreen(new OwnerBooksScreen()));

        customersBtn.addActionListener(e ->
            MainFrame.getInstance().showScreen(new OwnerCustomersScreen()));

        logoutBtn.addActionListener(e ->
            MainFrame.getInstance().showScreen(new LoginScreen()));
    }
}
