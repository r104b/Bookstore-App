package bookstoreapp;

import javax.swing.*;
import java.awt.*;

/**
 * Customer cost screen: shows total cost, current points/status, and [Logout].
 */
public class CustomerCostScreen extends JPanel {

    public CustomerCostScreen(Customer customer, double totalCost) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.gridx = 0;

        // Format cost to 2 decimal places
        String costStr = String.format("%.2f", totalCost);

        JLabel costLabel = new JLabel("Total Cost: " + costStr);
        costLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        gbc.gridy = 0;
        add(costLabel, gbc);

        JLabel pointsLabel = new JLabel(
            "Points: " + customer.getPoints() + ", Status: " + customer.getStatus());
        pointsLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        gbc.gridy = 1;
        add(pointsLabel, gbc);

        JButton logoutBtn = new JButton("Logout");
        gbc.gridy = 2;
        add(logoutBtn, gbc);

        logoutBtn.addActionListener(e ->
            MainFrame.getInstance().showScreen(new LoginScreen()));
    }
}
