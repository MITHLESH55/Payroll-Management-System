package PayrollManagementsystem;

import java.sql.Connection;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class SignupForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextField fullNameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JComboBox<String> userLevelCombo;
    private JButton registerButton;
    private JButton clearButton;
    private JButton backButton;

    public SignupForm() {
        setTitle("User Signup");
        setSize(500, 550);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(230, 240, 255)); // Soft light blue

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Full Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Full Name:"), gbc);
        fullNameField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(fullNameField, gbc);

        // Username
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Username:"), gbc);
        usernameField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(usernameField, gbc);

        // Email
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Email:"), gbc);
        emailField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(emailField, gbc);

        // Phone Number
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Phone Number:"), gbc);
        phoneField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(phoneField, gbc);

        // User Level
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("User Level:"), gbc);
        userLevelCombo = new JComboBox<>(new String[]{"Employee", "Admin"});
        gbc.gridx = 1;
        panel.add(userLevelCombo, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("Password:"), gbc);
        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        // Confirm Password
        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(new JLabel("Confirm Password:"), gbc);
        confirmPasswordField = new JPasswordField(20);
        gbc.gridx = 1;
        panel.add(confirmPasswordField, gbc);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        buttonPanel.setBackground(new Color(230, 240, 255));
        registerButton = new JButton("Register");
        clearButton = new JButton("Clear");
        backButton = new JButton("Back");
        buttonPanel.add(registerButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(backButton);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        // Credit Label
        JLabel creditLabel = new JLabel("Developed by Adarsh Jha");
        creditLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        creditLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        creditLabel.setForeground(Color.DARK_GRAY);

        // Add panels
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(creditLabel, BorderLayout.SOUTH);

        // Register button action
        registerButton.addActionListener(e -> registerUser());

        // Clear button action
        clearButton.addActionListener(e -> clearFields());

        // Back button action
        backButton.addActionListener(e -> {
            this.dispose(); // Close current signup form
            new LoginForm(); // Open login form
        });

        setVisible(true);
    }

    private void registerUser() {
        String fullName = fullNameField.getText();
        String username = usernameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String userLevel = (String) userLevelCombo.getSelectedItem();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        if (fullName.isEmpty() || username.isEmpty() || email.isEmpty() || phone.isEmpty()
                || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all the fields.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match.");
            return;
        }

        try {
            Connection conn = DBConnection.getConnection();
            String query = "INSERT INTO users (username, password, user_level, full_name, email, phone_number) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, username);
            pst.setString(2, password); // In real apps, hash the password!
            pst.setString(3, userLevel);
            pst.setString(4, fullName);
            pst.setString(5, email);
            pst.setString(6, phone);

            int result = pst.executeUpdate();

            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Registration successful!");
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed!");
            }

            pst.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void clearFields() {
        fullNameField.setText("");
        usernameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");
        userLevelCombo.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        new SignupForm();
    }
}
