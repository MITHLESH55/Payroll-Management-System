package PayrollManagementsystem;

import java.sql.Connection;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DashboardForm extends JFrame {
    private JButton homeButton;
    private JButton employeeManagerButton;
    private JButton logoutButton;

    private JPanel contentPanel;
    private JLabel userLabel;
    private JLabel dateTimeLabel;

    public DashboardForm() {
        setTitle("EMPLOYEE PAYROLL SYSTEM");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(30, 144, 255));
        headerPanel.setPreferredSize(new Dimension(getWidth(), 50));

        JLabel titleLabel = new JLabel("EMPLOYEE PAYROLL SYSTEM");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.cyan);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        headerPanel.add(titleLabel, BorderLayout.WEST);

        // Navigation panel
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        navPanel.setBackground(new Color(30, 144, 255));

        homeButton = new JButton("HOME");
        homeButton.setFocusPainted(false);

        employeeManagerButton = new JButton("EMPLOYEE MANAGER");
        employeeManagerButton.setFocusPainted(false);

        logoutButton = new JButton("LOG OUT");
        logoutButton.setFocusPainted(false);

        navPanel.add(homeButton);
        navPanel.add(employeeManagerButton);
        navPanel.add(logoutButton);
        headerPanel.add(navPanel, BorderLayout.EAST);

        // Content panel
        contentPanel = new JPanel();
        contentPanel.setLayout(new CardLayout());

        JPanel homePanel = createHomePanel();
        JPanel employeeManagerPanel = createEmployeeManagerPanel();

        contentPanel.add(homePanel, "HOME");
        contentPanel.add(employeeManagerPanel, "EMPLOYEE_MANAGER");

        // Footer panel
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setPreferredSize(new Dimension(getWidth(), 30));
        footerPanel.setBackground(new Color(240, 240, 240));

        JPanel leftFooter = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftFooter.setBackground(new Color(240, 240, 240));
        JLabel notificationLabel = new JLabel("Notifications: 0");
        leftFooter.add(notificationLabel);

        userLabel = new JLabel("Logged in As: Admin1");
        leftFooter.add(userLabel);

        JPanel rightFooter = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightFooter.setBackground(new Color(240, 240, 240));
        dateTimeLabel = new JLabel();
        updateDateTime();
        rightFooter.add(dateTimeLabel);

        footerPanel.add(leftFooter, BorderLayout.WEST);
        footerPanel.add(rightFooter, BorderLayout.EAST);

        // Add components to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        // Button listeners
        homeButton.addActionListener(e -> {
            CardLayout cl = (CardLayout) contentPanel.getLayout();
            cl.show(contentPanel, "HOME");
        });

        employeeManagerButton.addActionListener(e -> {
            CardLayout cl = (CardLayout) contentPanel.getLayout();
            cl.show(contentPanel, "EMPLOYEE_MANAGER");
        });

        logoutButton.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(
                    DashboardForm.this,
                    "Are you sure you want to logout?",
                    "Logout Confirmation",
                    JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {
                dispose();
                new LoginForm();
            }
        });

        // Timer to update time every second
        Timer timer = new Timer(1000, e -> updateDateTime());
        timer.start();

        add(mainPanel);
        setVisible(true);
    }

    private void updateDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMMM dd yyyy hh:mm:ss a");
        dateTimeLabel.setText(sdf.format(new Date()));
    }

    private JPanel createHomePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.lightGray);


        JLabel welcomeLabel = new JLabel("Welcome to Employee Payroll System");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        panel.add(welcomeLabel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createEmployeeManagerPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 3, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setBackground(Color.WHITE);

        panel.add(createPlainButton("ADD NEW EMPLOYEE", new Color(51, 153, 255), () -> new AddEmployeeForm().setVisible(true)));
        panel.add(createPlainButton("SEARCH EMPLOYEE", new Color(0, 204, 102), () -> new SearchEmployeeForm()));
        panel.add(createPlainButton("MANAGE EMPLOYEE ALLOWANCE", new Color(255, 153, 0), () -> new ManageAllowanceForm()));
        panel.add(createPlainButton("UPDATE EMPLOYEE SALARY", new Color(102, 102, 255), () -> new UpdateSalaryForm()));
        panel.add(createPlainButton("EMPLOYEE DEDUCTION", new Color(255, 102, 102), () -> new DeductionForm()));
        panel.add(createPlainButton("PAYMENT RECEIPT", new Color(153, 51, 255), () -> new PaymentReceiptForm()));

        return panel;
    }

    private JPanel createPlainButton(String text, Color color, Runnable action) {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(Color.WHITE);

        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // This action directly opens the form
        button.addActionListener(e -> action.run());

        wrapper.add(button, BorderLayout.CENTER);
        return wrapper;
    }

    public static void main(String[] args) {
        new DashboardForm();
    }
}
