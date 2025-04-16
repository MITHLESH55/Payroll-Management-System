package PayrollManagementsystem;

import java.sql.Connection;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.DecimalFormat;

public class DeductionForm extends JFrame {
    private JTextField searchField;
    private JTextField employeeIdField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField dateOfBirthField;
    private JTextField departmentField;
    private JTextField designationField;
    private JTextField statusField;
    private JTextField dateHiredField;
    private JTextField jobTitleField;
    private JTextField basicSalaryField;
    private JRadioButton percentageRadio;
    private JRadioButton amountRadio;
    private JTextField percentageField;
    private JTextField amountField;
    private JTextField totalDeductionField;
    private JTextField salaryAfterDeductionField;
    private JTextField reasonField;
    private JButton calculateButton;
    private JButton saveButton;
    private JButton clearButton;
    private JTable deductionHistoryTable;

    private DecimalFormat df = new DecimalFormat("#,##0.00");

    public DeductionForm() {
        setTitle("MANAGE EMPLOYEE DEDUCTION | STEP 3 of 3");
        setSize(800, 700);
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
        JButton searchButton = new JButton("Search");  // Plain button

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

        // Employee info fields (same as before) ...
        JLabel idLabel = new JLabel("Employee id:");
        gbc.gridx = 0; gbc.gridy = 0;
        employeePanel.add(idLabel, gbc);

        employeeIdField = new JTextField(10);
        employeeIdField.setEditable(false);
        gbc.gridx = 1;
        employeePanel.add(employeeIdField, gbc);

        JLabel designationLabel = new JLabel("Designation:");
        gbc.gridx = 2;
        employeePanel.add(designationLabel, gbc);

        designationField = new JTextField(15);
        designationField.setEditable(false);
        gbc.gridx = 3;
        employeePanel.add(designationField, gbc);

        JLabel firstNameLabel = new JLabel("First name:");
        gbc.gridx = 0; gbc.gridy = 1;
        employeePanel.add(firstNameLabel, gbc);

        firstNameField = new JTextField(10);
        firstNameField.setEditable(false);
        gbc.gridx = 1;
        employeePanel.add(firstNameField, gbc);

        JLabel statusLabel = new JLabel("Status:");
        gbc.gridx = 2;
        employeePanel.add(statusLabel, gbc);

        statusField = new JTextField(15);
        statusField.setEditable(false);
        gbc.gridx = 3;
        employeePanel.add(statusField, gbc);

        JLabel lastNameLabel = new JLabel("Surname:");
        gbc.gridx = 0; gbc.gridy = 2;
        employeePanel.add(lastNameLabel, gbc);

        lastNameField = new JTextField(10);
        lastNameField.setEditable(false);
        gbc.gridx = 1;
        employeePanel.add(lastNameField, gbc);

        JLabel dateHiredLabel = new JLabel("Date Hired:");
        gbc.gridx = 2;
        employeePanel.add(dateHiredLabel, gbc);

        dateHiredField = new JTextField(15);
        dateHiredField.setEditable(false);
        gbc.gridx = 3;
        employeePanel.add(dateHiredField, gbc);

        JLabel dobLabel = new JLabel("Date of Birth:");
        gbc.gridx = 0; gbc.gridy = 3;
        employeePanel.add(dobLabel, gbc);

        dateOfBirthField = new JTextField(10);
        dateOfBirthField.setEditable(false);
        gbc.gridx = 1;
        employeePanel.add(dateOfBirthField, gbc);

        JLabel jobTitleLabel = new JLabel("Job Title:");
        gbc.gridx = 2;
        employeePanel.add(jobTitleLabel, gbc);

        jobTitleField = new JTextField(15);
        jobTitleField.setEditable(false);
        gbc.gridx = 3;
        employeePanel.add(jobTitleField, gbc);

        JLabel departmentLabel = new JLabel("Department:");
        gbc.gridx = 0; gbc.gridy = 4;
        employeePanel.add(departmentLabel, gbc);

        departmentField = new JTextField(10);
        departmentField.setEditable(false);
        gbc.gridx = 1;
        employeePanel.add(departmentField, gbc);

        JLabel salaryLabel = new JLabel("Basic Salary:");
        gbc.gridx = 2;
        employeePanel.add(salaryLabel, gbc);

        basicSalaryField = new JTextField(15);
        basicSalaryField.setEditable(false);
        gbc.gridx = 3;
        employeePanel.add(basicSalaryField, gbc);

        // Deduction panel
        JPanel deductionPanel = new JPanel(new GridBagLayout());
        deductionPanel.setBackground(Color.WHITE);
        deductionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel deductLabel = new JLabel("Deduct Salary by:");
        gbc.gridx = 0; gbc.gridy = 0;
        deductionPanel.add(deductLabel, gbc);

        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        radioPanel.setBackground(Color.WHITE);

        percentageRadio = new JRadioButton("Percentage (%)");
        amountRadio = new JRadioButton("Amount");
        ButtonGroup radioGroup = new ButtonGroup();
        radioGroup.add(percentageRadio);
        radioGroup.add(amountRadio);
        percentageRadio.setSelected(true);

        radioPanel.add(percentageRadio);
        radioPanel.add(amountRadio);

        gbc.gridx = 1; gbc.gridwidth = 3;
        deductionPanel.add(radioPanel, gbc);

        JLabel percentageLabel = new JLabel("Percentage:");
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        deductionPanel.add(percentageLabel, gbc);

        percentageField = new JTextField(5);
        percentageField.setText("0");
        gbc.gridx = 1;
        deductionPanel.add(percentageField, gbc);

        JLabel amountLabel = new JLabel("Amount:");
        gbc.gridx = 2;
        deductionPanel.add(amountLabel, gbc);

        amountField = new JTextField(10);
        amountField.setText("0");
        gbc.gridx = 3;
        deductionPanel.add(amountField, gbc);

        JLabel totalDeductionLabel = new JLabel("Total Deduction:");
        gbc.gridx = 2; gbc.gridy = 2;
        deductionPanel.add(totalDeductionLabel, gbc);

        totalDeductionField = new JTextField(10);
        totalDeductionField.setText("Rs0.00");
        totalDeductionField.setEditable(false);
        gbc.gridx = 3;
        deductionPanel.add(totalDeductionField, gbc);

        JLabel salaryAfterLabel = new JLabel("Salary after deduction:");
        gbc.gridx = 2; gbc.gridy = 3;
        deductionPanel.add(salaryAfterLabel, gbc);

        salaryAfterDeductionField = new JTextField(10);
        salaryAfterDeductionField.setText("Rs0.00");
        salaryAfterDeductionField.setEditable(false);
        gbc.gridx = 3;
        deductionPanel.add(salaryAfterDeductionField, gbc);

        JLabel reasonLabel = new JLabel("Reason:");
        gbc.gridx = 0; gbc.gridy = 4;
        deductionPanel.add(reasonLabel, gbc);

        reasonField = new JTextField(30);
        gbc.gridx = 1; gbc.gridwidth = 3;
        deductionPanel.add(reasonField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);

        calculateButton = new JButton("Calculate");
        saveButton = new JButton("Save");
        clearButton = new JButton("Clear");

        buttonPanel.add(calculateButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(clearButton);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 4;
        deductionPanel.add(buttonPanel, gbc);

        String[] columns = {"EMP ID#", "COUNT", "FIRST NAME", "SURNAME", "BASIC SALARY"};
        Object[][] data = {};
        deductionHistoryTable = new JTable(data, columns);
        JScrollPane tableScrollPane = new JScrollPane(deductionHistoryTable);
        tableScrollPane.setPreferredSize(new Dimension(750, 150));

        gbc.gridx = 0; gbc.gridy = 6;
        deductionPanel.add(tableScrollPane, gbc);

        mainPanel.add(searchPanel, BorderLayout.NORTH);
        mainPanel.add(employeePanel, BorderLayout.CENTER);
        mainPanel.add(deductionPanel, BorderLayout.SOUTH);

        searchButton.addActionListener(e -> searchEmployee());
        calculateButton.addActionListener(e -> calculateDeduction());
        saveButton.addActionListener(e -> saveDeduction());
        clearButton.addActionListener(e -> clearDeductionFields());

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
                firstNameField.setText(rs.getString("first_name"));
                lastNameField.setText(rs.getString("last_name"));
                dateOfBirthField.setText(rs.getString("date_of_birth"));
                departmentField.setText(rs.getString("department"));
                designationField.setText(rs.getString("designation"));
                statusField.setText(rs.getString("status"));
                dateHiredField.setText(rs.getString("date_hired"));
                jobTitleField.setText(rs.getString("job_title"));

                double salary = rs.getDouble("basic_salary");
                basicSalaryField.setText(df.format(salary));
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

    private void calculateDeduction() {
        if (employeeIdField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please search for an employee first");
            return;
        }

        try {
            String salaryText = basicSalaryField.getText().replace(",", "").replace("₱", "");
            double salary = Double.parseDouble(salaryText);
            double deduction = 0;

            if (percentageRadio.isSelected()) {
                double percentage = Double.parseDouble(percentageField.getText());
                deduction = salary * (percentage / 100);
            } else if (amountRadio.isSelected()) {
                deduction = Double.parseDouble(amountField.getText());
            }

            double salaryAfterDeduction = salary - deduction;

            totalDeductionField.setText("RS" + df.format(deduction));
            salaryAfterDeductionField.setText("Rs" + df.format(salaryAfterDeduction));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid number format");
        }
    }

    private void saveDeduction() {
        if (employeeIdField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please search for an employee first");
            return;
        }

        if (reasonField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a reason for the deduction");
            return;
        }

        try {
            Connection conn = DBConnection.getConnection();
            String query = "INSERT INTO deductions (employee_id, percentage, amount, reason, deduction_date) VALUES (?, ?, ?, ?, CURDATE())";

            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, Integer.parseInt(employeeIdField.getText()));

            if (percentageRadio.isSelected()) {
                pst.setDouble(2, Double.parseDouble(percentageField.getText()));
                pst.setDouble(3, 0);
            } else {
                pst.setDouble(2, 0);
                pst.setDouble(3, Double.parseDouble(amountField.getText()));
            }

            pst.setString(4, reasonField.getText());

            int result = pst.executeUpdate();

            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Deduction saved successfully!");
                clearDeductionFields();
                loadDeductionHistory();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to save deduction");
            }

            pst.close();
        } catch (SQLException | NumberFormatException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving deduction: " + ex.getMessage());
        }
    }

    private void loadDeductionHistory() {
        try {
            Connection conn = DBConnection.getConnection();
            String query = "SELECT e.employee_id, COUNT(d.deduction_id) as count, e.first_name, e.last_name, e.basic_salary " +
                    "FROM employees e LEFT JOIN deductions d ON e.employee_id = d.employee_id " +
                    "GROUP BY e.employee_id";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) deductionHistoryTable.getModel();
            model.setRowCount(0);

            while (rs.next()) {
                Object[] row = {
                        rs.getInt("employee_id"),
                        rs.getInt("count"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        "₱" + df.format(rs.getDouble("basic_salary"))
                };
                model.addRow(row);
            }

            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading deduction history: " + ex.getMessage());
        }
    }

    private void clearFields() {
        employeeIdField.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        dateOfBirthField.setText("");
        departmentField.setText("");
        designationField.setText("");
        statusField.setText("");
        dateHiredField.setText("");
        jobTitleField.setText("");
        basicSalaryField.setText("");
        clearDeductionFields();
    }

    private void clearDeductionFields() {
        percentageRadio.setSelected(true);
        percentageField.setText("0");
        amountField.setText("0");
        totalDeductionField.setText("RS0.00");
        salaryAfterDeductionField.setText("Rs0.00");
        reasonField.setText("");
    }

    // ✅ Main method to run the form
    public static void main(String[] args) {
        SwingUtilities.invokeLater(DeductionForm::new);
    }
}
