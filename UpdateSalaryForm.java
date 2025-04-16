package PayrollManagementsystem;

import java.sql.Connection;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UpdateSalaryForm extends JFrame {
    private JTextField searchField;
    private JButton searchButton;
    private JTextField employeeIdField;
    private JTextField nameField;
    private JTextField departmentField;
    private JTextField designationField;
    private JTextField jobTitleField;
    private JTextField currentSalaryField;
    private JTextField newSalaryField;
    private JTextField percentIncreaseField;
    private JRadioButton percentageRadio;
    private JRadioButton absoluteRadio;
    private JButton calculateButton;
    private JButton updateButton;
    private JButton clearButton;
    private JTable salaryHistoryTable;

    private DefaultTableModel tableModel;
    private DecimalFormat df = new DecimalFormat("#,##0.00");

    public UpdateSalaryForm() {
        setTitle("UPDATE EMPLOYEE SALARY");
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

        // Job Title
        JLabel jobTitleLabel = new JLabel("Job Title:");
        gbc.gridx = 2;
        gbc.gridy = 1;
        employeePanel.add(jobTitleLabel, gbc);

        jobTitleField = new JTextField(20);
        jobTitleField.setEditable(false);
        gbc.gridx = 3;
        gbc.gridy = 1;
        employeePanel.add(jobTitleField, gbc);

        // Current Salary
        JLabel currentSalaryLabel = new JLabel("Current Salary:");
        gbc.gridx = 2;
        gbc.gridy = 2;
        employeePanel.add(currentSalaryLabel, gbc);

        currentSalaryField = new JTextField(15);
        currentSalaryField.setEditable(false);
        gbc.gridx = 3;
        gbc.gridy = 2;
        employeePanel.add(currentSalaryField, gbc);

        // Salary update panel
        JPanel updatePanel = new JPanel(new GridBagLayout());
        updatePanel.setBackground(Color.WHITE);
        updatePanel.setBorder(BorderFactory.createTitledBorder("Update Salary"));

        // Update options
        JLabel updateTypeLabel = new JLabel("Update By:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        updatePanel.add(updateTypeLabel, gbc);

        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        radioPanel.setBackground(Color.WHITE);

        percentageRadio = new JRadioButton("Percentage (%)");
        absoluteRadio = new JRadioButton("Absolute Amount");
        ButtonGroup radioGroup = new ButtonGroup();
        radioGroup.add(percentageRadio);
        radioGroup.add(absoluteRadio);
        percentageRadio.setSelected(true);

        radioPanel.add(percentageRadio);
        radioPanel.add(absoluteRadio);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        updatePanel.add(radioPanel, gbc);

        // Percentage input
        JLabel percentLabel = new JLabel("Percentage Increase:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        updatePanel.add(percentLabel, gbc);

        percentIncreaseField = new JTextField(5);
        percentIncreaseField.setText("0");
        gbc.gridx = 1;
        gbc.gridy = 1;
        updatePanel.add(percentIncreaseField, gbc);

        // New Salary
        JLabel newSalaryLabel = new JLabel("New Salary:");
        gbc.gridx = 2;
        gbc.gridy = 1;
        updatePanel.add(newSalaryLabel, gbc);

        newSalaryField = new JTextField(15);
        newSalaryField.setText("Rs 0.00");  // Changed from ₱ to Rs
        gbc.gridx = 3;
        gbc.gridy = 1;
        updatePanel.add(newSalaryField, gbc);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);

        calculateButton = new JButton("Calculate");
        updateButton = new JButton("Update Salary");
        clearButton = new JButton("Clear");

        buttonPanel.add(calculateButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(clearButton);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 4;
        updatePanel.add(buttonPanel, gbc);

        // Salary history table
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createTitledBorder("Salary History"));

        // Create table model with columns
        String[] columns = {"DATE", "PREVIOUS SALARY", "NEW SALARY", "INCREASE (%)", "UPDATE TYPE"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;  // Make all cells non-editable
            }
        };

        salaryHistoryTable = new JTable(tableModel);
        salaryHistoryTable.getTableHeader().setReorderingAllowed(false);
        salaryHistoryTable.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(salaryHistoryTable);
        scrollPane.setPreferredSize(new Dimension(750, 150));
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Add panels to main panel
        mainPanel.add(searchPanel, BorderLayout.NORTH);
        mainPanel.add(employeePanel, BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.setBackground(Color.WHITE);
        southPanel.add(updatePanel, BorderLayout.NORTH);
        southPanel.add(tablePanel, BorderLayout.CENTER);

        mainPanel.add(southPanel, BorderLayout.SOUTH);

        // Add action listeners
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchEmployee();
            }
        });

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateNewSalary();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateSalary();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearUpdateFields();
            }
        });

        percentageRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                percentIncreaseField.setEnabled(true);
                newSalaryField.setEnabled(false);
            }
        });

        absoluteRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                percentIncreaseField.setEnabled(false);
                newSalaryField.setEnabled(true);
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
                jobTitleField.setText(rs.getString("job_title"));

                double salary = rs.getDouble("basic_salary");
                currentSalaryField.setText("Rs " + df.format(salary));  // Changed from ₱ to Rs

                // Load salary history for this employee
                loadSalaryHistory(Integer.parseInt(employeeId));
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

    private void loadSalaryHistory(int employeeId) {
        // For this example, we'll create a fictional salary history table
        // In a real implementation, you would have a salary_history table in your database
        tableModel.setRowCount(0);

        try {
            Connection conn = DBConnection.getConnection();
            // Query would normally fetch from a salary_history table
            // For now we'll just show the current salary
            String query = "SELECT * FROM employees WHERE employee_id = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, employeeId);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                double salary = rs.getDouble("basic_salary");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String hireDate = rs.getString("date_hired");

                // Just for demo purposes - show initial salary as 10% less than current
                double previousSalary = salary * 0.9;

                Object[] row = {
                        hireDate,
                        "Rs " + df.format(previousSalary),  // Changed from ₱ to Rs
                        "Rs " + df.format(salary),  // Changed from ₱ to Rs
                        "10.0%",
                        "Initial"
                };
                tableModel.addRow(row);
            }

            rs.close();
            pst.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading salary history: " + ex.getMessage());
        }
    }

    private void calculateNewSalary() {
        if (employeeIdField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please search for an employee first");
            return;
        }

        try {
            String salaryText = currentSalaryField.getText().replace("Rs ", "").replace(",", "");  // Changed from ₱ to Rs
            double currentSalary = Double.parseDouble(salaryText);
            double newSalary = 0;

            if (percentageRadio.isSelected()) {
                double percentIncrease = Double.parseDouble(percentIncreaseField.getText());
                newSalary = currentSalary * (1 + percentIncrease / 100);
                newSalaryField.setText("Rs " + df.format(newSalary));  // Changed from ₱ to Rs
            } else {
                String newSalaryText = newSalaryField.getText().replace("Rs ", "").replace(",", "");  // Changed from ₱ to Rs
                if (!newSalaryText.isEmpty()) {
                    newSalary = Double.parseDouble(newSalaryText);
                    double percentIncrease = ((newSalary - currentSalary) / currentSalary) * 100;
                    percentIncreaseField.setText(String.format("%.2f", percentIncrease));
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid number format");
        }
    }

    private void updateSalary() {
        if (employeeIdField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please search for an employee first");
            return;
        }

        try {
            String newSalaryText = newSalaryField.getText().replace("Rs ", "").replace(",", "");  // Changed from ₱ to Rs
            double newSalary = Double.parseDouble(newSalaryText);

            if (newSalary <= 0) {
                JOptionPane.showMessageDialog(this, "New salary must be greater than zero");
                return;
            }

            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to update the salary to Rs " + df.format(newSalary) + "?",  // Changed from ₱ to Rs
                    "Confirm Salary Update",
                    JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {
                Connection conn = DBConnection.getConnection();
                String query = "UPDATE employees SET basic_salary = ? WHERE employee_id = ?";
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setDouble(1, newSalary);
                pst.setInt(2, Integer.parseInt(employeeIdField.getText()));

                int result = pst.executeUpdate();

                if (result > 0) {
                    JOptionPane.showMessageDialog(this, "Salary updated successfully");

                    // Update the current salary field
                    currentSalaryField.setText("Rs " + df.format(newSalary));  // Changed from ₱ to Rs

                    // Add to salary history
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String currentDate = sdf.format(new Date());

                    String currentSalaryText = currentSalaryField.getText().replace("Rs ", "").replace(",", "");  // Changed from ₱ to Rs
                    double oldSalary = Double.parseDouble(currentSalaryText);

                    double percentIncrease = Double.parseDouble(percentIncreaseField.getText());

                    String updateType = percentageRadio.isSelected() ? "Percentage" : "Absolute";

                    Object[] row = {
                            currentDate,
                            "Rs " + df.format(oldSalary),  // Changed from ₱ to Rs
                            "Rs " + df.format(newSalary),  // Changed from ₱ to Rs
                            String.format("%.2f%%", percentIncrease),
                            updateType
                    };
                    tableModel.insertRow(0, row);

                    // Clear update fields
                    clearUpdateFields();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update salary");
                }

                pst.close();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid salary");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating salary: " + ex.getMessage());
        }
    }

    private void clearFields() {
        employeeIdField.setText("");
        nameField.setText("");
        departmentField.setText("");
        designationField.setText("");
        jobTitleField.setText("");
        currentSalaryField.setText("");
        clearUpdateFields();
        tableModel.setRowCount(0);
    }

    private void clearUpdateFields() {
        percentIncreaseField.setText("0");
        newSalaryField.setText("Rs 0.00");  // Changed from ₱ to Rs
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
                new UpdateSalaryForm();
            }
        });
    }
}
