package bookstoreapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Login screen: username field, password field, [Login] button.
 */
public class LoginScreen extends JPanel {

    public LoginScreen() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);

        JLabel titleLabel = new JLabel("Welcome to the BookStore App");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(titleLabel, gbc);

        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Username:"), gbc);

        JTextField usernameField = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 1;
        add(usernameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Password:"), gbc);

        JPasswordField passwordField = new JPasswordField(15);
        gbc.gridx = 1; gbc.gridy = 2;
        add(passwordField, gbc);

        JButton loginBtn = new JButton("Login");
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(loginBtn, gbc);

        JLabel errorLabel = new JLabel(" ");
        errorLabel.setForeground(Color.RED);
        gbc.gridy = 4;
        add(errorLabel, gbc);

        loginBtn.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            // Check owner
            if (username.equals("admin") && password.equals("admin")) {
                MainFrame.getInstance().showScreen(new OwnerStartScreen());
            } else {
                // Check customer
                Customer customer = DataStore.getInstance().authenticate(username, password);
                if (customer != null) {
                    MainFrame.getInstance().showScreen(new CustomerStartScreen(customer));
                } else {
                    errorLabel.setText("Invalid username or password.");
                    usernameField.setText("");
                    passwordField.setText("");
                }
            }
        });
    }
}
