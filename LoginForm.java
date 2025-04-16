package PayrollManagementsystem;

import java.sql.Connection;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LoginForm extends JFrame {
    private JComboBox<String> userLevelCombo;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton signupButton;
    private JButton clearButton;

    public LoginForm() {
        setTitle("Payroll System | LOG IN");
        setSize(450, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Custom colors
        Color mainBgColor = new Color(245, 250, 255);
        Color formBgColor = new Color(230, 240, 250);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(mainBgColor);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        getContentPane().setBackground(mainBgColor);

        // Logo panel
        JPanel logoPanel = new JPanel(new BorderLayout());
        logoPanel.setBackground(mainBgColor);

        try {
            ImageIcon originalIcon = new ImageIcon(getClass().getResource("/PayrollManagementSystem/icon/employee.jpeg"));
            Image scaledImage = originalIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            JLabel logoLabel = new JLabel(scaledIcon);
            logoLabel.setHorizontalAlignment(JLabel.CENTER);
            logoPanel.add(logoLabel, BorderLayout.CENTER);
        } catch (Exception e) {
            System.out.println("Image not found!");
        }

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        formPanel.setBackground(formBgColor);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 5, 10, 5);

        // User level
        JLabel userLevelLabel = new JLabel("User Level");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        formPanel.add(userLevelLabel, gbc);

        userLevelCombo = new JComboBox<>(new String[]{"Admin", "Employee"});
        gbc.gridy = 1;
        formPanel.add(userLevelCombo, gbc);

        // Username
        JLabel usernameLabel = new JLabel("Username");
        gbc.gridy = 2;
        formPanel.add(usernameLabel, gbc);

        usernameField = new JTextField(20);
        gbc.gridy = 3;
        formPanel.add(usernameField, gbc);

        // Password
        JLabel passwordLabel = new JLabel("Password");
        gbc.gridy = 4;
        formPanel.add(passwordLabel, gbc);

        JPanel passwordPanel = new JPanel(new BorderLayout());
        passwordPanel.setBackground(formBgColor);
        passwordField = new JPasswordField(20);
        passwordField.setEchoChar('•');
        passwordPanel.add(passwordField, BorderLayout.CENTER);

        JCheckBox showPasswordCheckBox = new JCheckBox();
        showPasswordCheckBox.setBackground(formBgColor);
        showPasswordCheckBox.addActionListener(e -> {
            passwordField.setEchoChar(showPasswordCheckBox.isSelected() ? (char) 0 : '•');
        });
        passwordPanel.add(showPasswordCheckBox, BorderLayout.EAST);

        gbc.gridy = 5;
        formPanel.add(passwordPanel, gbc);

        // Button styling
        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        Dimension buttonSize = new Dimension(200, 40);

        // Login Button
        loginButton = new JButton("LOG IN");
        loginButton.setBackground(new Color(34, 139, 34));
        loginButton.setForeground(Color.BLACK);
        loginButton.setFont(buttonFont);
        loginButton.setPreferredSize(buttonSize);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createLineBorder(new Color(0, 100, 0), 2));
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridy = 6;
        formPanel.add(loginButton, gbc);

        // Sign Up Button
        signupButton = new JButton("SIGN UP");
        signupButton.setBackground(new Color(255, 140, 0));
        signupButton.setForeground(Color.BLACK);
        signupButton.setFont(buttonFont);
        signupButton.setPreferredSize(buttonSize);
        signupButton.setFocusPainted(false);
        signupButton.setBorder(BorderFactory.createLineBorder(new Color(255, 69, 0), 2));
        signupButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridy = 7;
        formPanel.add(signupButton, gbc);

        // Clear Button
        clearButton = new JButton("CLEAR");
        clearButton.setBackground(Color.WHITE);
        clearButton.setForeground(Color.BLACK);
        clearButton.setFont(buttonFont);
        clearButton.setPreferredSize(buttonSize);
        clearButton.setFocusPainted(false);
        clearButton.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        clearButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridy = 8;
        formPanel.add(clearButton, gbc);

        // Add panels
        mainPanel.add(logoPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Action listeners
        loginButton.addActionListener(e -> login());

        signupButton.addActionListener(e -> {
            this.dispose();
            new SignupForm();
        });

        clearButton.addActionListener(e -> {
            usernameField.setText("");
            passwordField.setText("");
        });

        add(mainPanel);
        setVisible(true);
    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String userLevel = (String) userLevelCombo.getSelectedItem();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter username and password");
            return;
        }

        try {
            Connection conn = DBConnection.getConnection();
            String query = "SELECT * FROM users WHERE username = ? AND password = ? AND user_level = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, username);
            pst.setString(2, password);
            pst.setString(3, userLevel);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                this.dispose();
                new DashboardForm(); //Dashboard added here so  after this dashbaord will open
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password");
            }

            rs.close();
            pst.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(LoginForm::new);
    }
}
