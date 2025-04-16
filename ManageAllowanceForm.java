package PayrollManagementsystem;

import java.sql.Connection;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.Date;
import java.text.SimpleDateFormat;

public class ManageAllowanceForm extends JFrame {
    private JTextField searchField;
    private JButton searchButton;
    private JTextField employeeIdField;
    private JTextField nameField;
    private JTextField departmentField;
    private JTextField designationField;
    private JTextField currentSalaryField;
    private JComboBox<String> allowanceTypeCombo;
    private JTextField amountField;
    private JTable allowanceTable;
    private JButton addAllowanceButton;
    private JButton removeAllowanceButton;
    private JLabel totalAllowanceLabel;
    private JButton clearButton;

    private DefaultTableModel tableModel;
    private DecimalFormat df = new DecimalFormat("#,##0.00");

    public ManageAllowanceForm() {
        setTitle("MANAGE EMPLOYEE ALLOWANCES | STEP 2 of 3");
        setSize(800, 650);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel searchLabel = new JLabel("Search Employee ID:");
        searchField = new JTextField(10);
        searchButton = new JButton("Search");  // Changed from icon to text

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // Employee details panel
        JPanel employeePanel = new JPanel(new GridBagLayout());
        employeePanel.setBackground(Color.WHITE);
        employeePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Employee ID
        JLabel idLabel = new JLabel("Employee ID:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        employeePanel.add(idLabel, gbc);

        employeeIdField = new JTextField(10);
        employeeIdField.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 0;
        employeePanel.add(employeeIdField, gbc);

        // Name
        JLabel nameLabel = new JLabel("Employee Name:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        employeePanel.add(nameLabel, gbc);

        nameField = new JTextField(20);
        nameField.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 1;
        employeePanel.add(nameField, gbc);

        // Department
        JLabel departmentLabel = new JLabel("Department:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        employeePanel.add(departmentLabel, gbc);

        departmentField = new JTextField(20);
        departmentField.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 2;
        employeePanel.add(departmentField, gbc);

        // Designation
        JLabel designationLabel = new JLabel("Designation:");
        gbc.gridx = 2;
        gbc.gridy = 0;
        employeePanel.add(designationLabel, gbc);

        designationField = new JTextField(20);
        designationField.setEditable(false);
        gbc.gridx = 3;
        gbc.gridy = 0;
        employeePanel.add(designationField, gbc);

        // Current Salary
        JLabel currentSalaryLabel = new JLabel("Current Salary:");
        gbc.gridx = 2;
        gbc.gridy = 1;
        employeePanel.add(currentSalaryLabel, gbc);

        currentSalaryField = new JTextField(15);
        currentSalaryField.setEditable(false);
        gbc.gridx = 3;
        gbc.gridy = 1;
        employeePanel.add(currentSalaryField, gbc);

        // Allowance panel
        JPanel allowancePanel = new JPanel(new GridBagLayout());
        allowancePanel.setBackground(Color.WHITE);
        allowancePanel.setBorder(BorderFactory.createTitledBorder("Add Allowance"));

        // Allowance type
        JLabel typeLabel = new JLabel("Allowance Type:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        allowancePanel.add(typeLabel, gbc);

        String[] allowanceTypes = {"Transport Allowance", "Housing Allowance", "Medical Allowance",
                "Food Allowance", "Phone Allowance", "Other"};
        allowanceTypeCombo = new JComboBox<>(allowanceTypes);
        gbc.gridx = 1;
        gbc.gridy = 0;
        allowancePanel.add(allowanceTypeCombo, gbc);

        // Amount
        JLabel amountLabel = new JLabel("Amount:");
        gbc.gridx = 2;
        gbc.gridy = 0;
        allowancePanel.add(amountLabel, gbc);

        amountField = new JTextField(10);
        gbc.gridx = 3;
        gbc.gridy = 0;
        allowancePanel.add(amountField, gbc);

        // Add button
        addAllowanceButton = new JButton("Add Allowance");
        gbc.gridx = 4;
        gbc.gridy = 0;
        allowancePanel.add(addAllowanceButton, gbc);

        // Allowance table panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createTitledBorder("Allowance History"));

        // Create table model with columns
        String[] columns = {"ID", "ALLOWANCE TYPE", "AMOUNT", "DATE ADDED"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;  // Make all cells non-editable
            }
        };

        allowanceTable = new JTable(tableModel);
        allowanceTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        allowanceTable.getTableHeader().setReorderingAllowed(false);
        allowanceTable.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(allowanceTable);
        scrollPane.setPreferredSize(new Dimension(700, 200));
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Button and summary panel
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.WHITE);

        // Total allowance panel
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        totalPanel.setBackground(Color.WHITE);

        JLabel totalLabel = new JLabel("Total Allowances:");
        totalAllowanceLabel = new JLabel("Rs 0.00");  // Changed from ₱ to Rs
        totalAllowanceLabel.setFont(new Font("Arial", Font.BOLD, 14));
        totalPanel.add(totalLabel);
        totalPanel.add(totalAllowanceLabel);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);

        removeAllowanceButton = new JButton("Remove Allowance");
        clearButton = new JButton("Clear");

        buttonPanel.add(removeAllowanceButton);
        buttonPanel.add(clearButton);

        bottomPanel.add(totalPanel, BorderLayout.WEST);
        bottomPanel.add(buttonPanel, BorderLayout.EAST);

        tablePanel.add(bottomPanel, BorderLayout.SOUTH);

        // Add panels to main panel
        mainPanel.add(searchPanel, BorderLayout.NORTH);
        mainPanel.add(employeePanel, BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.setBackground(Color.WHITE);
        southPanel.add(allowancePanel, BorderLayout.NORTH);
        southPanel.add(tablePanel, BorderLayout.CENTER);

        mainPanel.add(southPanel, BorderLayout.SOUTH);

        // Add action listeners
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchEmployee();
            }
        });

        addAllowanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addAllowance();
            }
        });

        removeAllowanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeAllowance();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });

        add(mainPanel);
        setVisible(true);
    }

    private void searchEmployee() {
        String employeeId = searchField.getText().trim();

        if (employeeId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an employee ID");
            return;
        }

        try {
            Connection conn = DBConnection.getConnection();
            String query = "SELECT * FROM employees WHERE employee_id = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, Integer.parseInt(employeeId));

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                employeeIdField.setText(rs.getString("employee_id"));
                String fullName = rs.getString("first_name") + " " + rs.getString("last_name");
                nameField.setText(fullName);
                departmentField.setText(rs.getString("department"));
                designationField.setText(rs.getString("designation"));

                double salary = rs.getDouble("basic_salary");
                currentSalaryField.setText("Rs " + df.format(salary));  // Changed from ₱ to Rs

                // Load allowances for this employee
                loadAllowances(Integer.parseInt(employeeId));
            } else {
                JOptionPane.showMessageDialog(this, "Employee not found");
                clearFields();
            }

            rs.close();
            pst.close();
        } catch (SQLException | NumberFormatException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error searching employee: " + ex.getMessage());
        }
    }

    private void loadAllowances(int employeeId) {
        // Clear existing data
        tableModel.setRowCount(0);

        try {
            Connection conn = DBConnection.getConnection();
            String query = "SELECT * FROM allowances WHERE employee_id = ? ORDER BY allowance_id DESC";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, employeeId);

            ResultSet rs = pst.executeQuery();

            double totalAllowance = 0;

            while (rs.next()) {
                int id = rs.getInt("allowance_id");
                String type = rs.getString("allowance_type");
                double amount = rs.getDouble("amount");
                totalAllowance += amount;

                // Get the current date since we don't have a date field in allowances table
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String currentDate = sdf.format(new Date());

                Object[] row = {id, type, "Rs " + df.format(amount), currentDate};  // Changed from ₱ to Rs
                tableModel.addRow(row);
            }

            totalAllowanceLabel.setText("Rs " + df.format(totalAllowance));  // Changed from ₱ to Rs

            rs.close();
            pst.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading allowances: " + ex.getMessage());
        }
    }

    private void addAllowance() {
        if (employeeIdField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please search for an employee first");
            return;
        }

        String amountText = amountField.getText().trim();
        if (amountText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an allowance amount");
            return;
        }

        try {
            double amount = Double.parseDouble(amountText);
            if (amount <= 0) {
                JOptionPane.showMessageDialog(this, "Allowance amount must be greater than zero");
                return;
            }

            String allowanceType = (String) allowanceTypeCombo.getSelectedItem();
            int employeeId = Integer.parseInt(employeeIdField.getText());

            Connection conn = DBConnection.getConnection();
            String query = "INSERT INTO allowances (employee_id, allowance_type, amount) VALUES (?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, employeeId);
            pst.setString(2, allowanceType);
            pst.setDouble(3, amount);

            int result = pst.executeUpdate();

            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Allowance added successfully");
                amountField.setText("");
                loadAllowances(employeeId);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add allowance");
            }

            pst.close();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid amount");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding allowance: " + ex.getMessage());
        }
    }

    private void removeAllowance() {
        int selectedRow = allowanceTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an allowance to remove");
            return;
        }

        int allowanceId = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
        String allowanceType = tableModel.getValueAt(selectedRow, 1).toString();

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to remove this " + allowanceType + "?",
                "Confirm Remove",
                JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            try {
                Connection conn = DBConnection.getConnection();
                String query = "DELETE FROM allowances WHERE allowance_id = ?";
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setInt(1, allowanceId);

                int result = pst.executeUpdate();

                if (result > 0) {
                    JOptionPane.showMessageDialog(this, "Allowance removed successfully");
                    loadAllowances(Integer.parseInt(employeeIdField.getText()));
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to remove allowance");
                }

                pst.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error removing allowance: " + ex.getMessage());
            }
        }
    }

    private void clearFields() {
        employeeIdField.setText("");
        nameField.setText("");
        departmentField.setText("");
        designationField.setText("");
        currentSalaryField.setText("");
        amountField.setText("");
        tableModel.setRowCount(0);
        totalAllowanceLabel.setText("Rs 0.00");
    }

    // Added main method to make the class runnable
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ManageAllowanceForm();
            }
        });
    }
}
