package PayrollManagementsystem;

import java.sql.Connection;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.Date;
import java.text.SimpleDateFormat;

public class PaymentReceiptForm extends JFrame {
    private JTextField searchField;
    private JButton searchButton;
    private JTextField employeeIdField;
    private JTextField nameField;
    private JTextField departmentField;
    private JTextField designationField;
    private JTextField basicSalaryField;
    private JTextField allowancesField;
    private JTextField taxField;
    private JTextField deductionsField;
    private JTextField netSalaryField;
    private JTextField payPeriodField;
    private JTextField absencesField;
    private JButton generateReceiptButton;
    private JButton printButton;
    private JTextArea receiptArea;

    private DecimalFormat df = new DecimalFormat("#,##0.00");

    public PaymentReceiptForm() {
        setTitle("Generate Employee Payment Receipt");
        setSize(900, 700);
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
        searchButton = new JButton("Search");

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // Employee and salary details panel
        JPanel detailsPanel = new JPanel(new GridBagLayout());
        detailsPanel.setBackground(Color.WHITE);
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Fields
        String[] labels = {"Employee ID:", "Employee Name:", "Department:", "Designation:", "Pay Period:", "Absences (Days):"};
        JTextField[] fields = {
                employeeIdField = new JTextField(10),
                nameField = new JTextField(20),
                departmentField = new JTextField(20),
                designationField = new JTextField(20),
                payPeriodField = new JTextField(20),
                absencesField = new JTextField(5)
        };
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy");
        payPeriodField.setText(sdf.format(new Date()));
        absencesField.setText("0");

        for (int i = 0; i < labels.length; i++) {
            fields[i].setEditable(i != 4 && i != 5 ? false : true);
            gbc.gridx = 0;
            gbc.gridy = i;
            detailsPanel.add(new JLabel(labels[i]), gbc);

            gbc.gridx = 1;
            detailsPanel.add(fields[i], gbc);
        }

        // Salary fields
        String[] salaryLabels = {"Basic Salary:", "Allowances:", "Tax (%):", "Deductions:", "Net Salary:"};
        JTextField[] salaryFields = {
                basicSalaryField = new JTextField(15),
                allowancesField = new JTextField(15),
                taxField = new JTextField(15),
                deductionsField = new JTextField(15),
                netSalaryField = new JTextField(15)
        };

        allowancesField.setText("0");
        taxField.setText("10");
        deductionsField.setEditable(false);
        netSalaryField.setEditable(false);
        basicSalaryField.setEditable(false);

        for (int i = 0; i < salaryLabels.length; i++) {
            gbc.gridx = 2;
            gbc.gridy = i;
            detailsPanel.add(new JLabel(salaryLabels[i]), gbc);

            gbc.gridx = 3;
            detailsPanel.add(salaryFields[i], gbc);
        }

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);

        generateReceiptButton = new JButton("Generate Receipt");
        printButton = new JButton("Print Receipt");
        printButton.setEnabled(false);

        buttonPanel.add(generateReceiptButton);
        buttonPanel.add(printButton);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(20, 5, 5, 5);
        detailsPanel.add(buttonPanel, gbc);

        // Receipt area
        receiptArea = new JTextArea(20, 60);
        receiptArea.setEditable(false);
        receiptArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(receiptArea);

        // Add components to main panel
        mainPanel.add(searchPanel, BorderLayout.NORTH);
        mainPanel.add(detailsPanel, BorderLayout.CENTER);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);

        // Action listeners
        searchButton.addActionListener(e -> searchEmployee());
        generateReceiptButton.addActionListener(e -> generateReceipt());
        printButton.addActionListener(e -> {
            try {
                receiptArea.print();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error printing receipt: " + ex.getMessage());
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
            String query = "SELECT e.*, SUM(d.amount) as total_deductions " +
                    "FROM employees e LEFT JOIN deductions d ON e.employee_id = d.employee_id " +
                    "WHERE e.employee_id = ? GROUP BY e.employee_id";

            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, Integer.parseInt(employeeId));
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                employeeIdField.setText(rs.getString("employee_id"));
                nameField.setText(rs.getString("first_name") + " " + rs.getString("last_name"));
                departmentField.setText(rs.getString("department"));
                designationField.setText(rs.getString("designation"));
                basicSalaryField.setText("Rs" + df.format(rs.getDouble("basic_salary")));

                double deductions = rs.getDouble("total_deductions");
                deductionsField.setText("Rs" + df.format(deductions));

                String allowanceQuery = "SELECT SUM(amount) as total_allowances FROM allowances WHERE employee_id = ?";
                PreparedStatement allowancePst = conn.prepareStatement(allowanceQuery);
                allowancePst.setInt(1, Integer.parseInt(employeeId));
                ResultSet allowanceRs = allowancePst.executeQuery();

                double allowances = 0;
                if (allowanceRs.next() && allowanceRs.getObject(1) != null) {
                    allowances = allowanceRs.getDouble("total_allowances");
                }
                allowancesField.setText("Rs" + df.format(allowances));

                calculateNetSalary();
            } else {
                JOptionPane.showMessageDialog(this, "Employee not found");
                clearFields();
            }

            rs.close();
            pst.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void calculateNetSalary() {
        try {
            double salary = Double.parseDouble(basicSalaryField.getText().replace(",", "").replace("Rs", ""));
            double allowances = Double.parseDouble(allowancesField.getText().replace(",", "").replace("Rs", ""));
            double deductions = Double.parseDouble(deductionsField.getText().replace(",", "").replace("Rs", ""));
            double tax = Double.parseDouble(taxField.getText());
            int absences = Integer.parseInt(absencesField.getText());

            double dailyRate = salary / 22;
            double absenceDeduction = dailyRate * absences;
            double grossSalary = salary + allowances;
            double taxAmount = grossSalary * (tax / 100);
            double netSalary = grossSalary - taxAmount - deductions - absenceDeduction;

            netSalaryField.setText("Rs" + df.format(netSalary));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid number format");
        }
    }

    private void generateReceipt() {
        if (employeeIdField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please search for an employee first");
            return;
        }

        try {
            calculateNetSalary();

            double salary = Double.parseDouble(basicSalaryField.getText().replace(",", "").replace("Rs", ""));
            double allowances = Double.parseDouble(allowancesField.getText().replace(",", "").replace("Rs", ""));
            double deductions = Double.parseDouble(deductionsField.getText().replace(",", "").replace("Rs", ""));
            double tax = Double.parseDouble(taxField.getText());
            int absences = Integer.parseInt(absencesField.getText());

            double dailyRate = salary / 22;
            double absenceDeduction = dailyRate * absences;
            double grossSalary = salary + allowances;
            double taxAmount = grossSalary * (tax / 100);
            double netSalary = grossSalary - taxAmount - deductions - absenceDeduction;

            StringBuilder receipt = new StringBuilder();
            String date = new SimpleDateFormat("MMMM dd, yyyy").format(new Date());

            receipt.append("=====================================================\n");
            receipt.append("               EMPLOYEE PAYMENT RECEIPT              \n");
            receipt.append("=====================================================\n\n");
            receipt.append("Date: ").append(date).append("\n");
            receipt.append("Pay Period: ").append(payPeriodField.getText()).append("\n\n");
            receipt.append("Employee ID: ").append(employeeIdField.getText()).append("\n");
            receipt.append("Employee Name: ").append(nameField.getText()).append("\n");
            receipt.append("Department: ").append(departmentField.getText()).append("\n");
            receipt.append("Designation: ").append(designationField.getText()).append("\n\n");
            receipt.append("-----------------------------------------------------\n");
            receipt.append("EARNINGS                                    AMOUNT   \n");
            receipt.append("-----------------------------------------------------\n");
            receipt.append(String.format("Basic Salary                           Rs%10.2f\n", salary));
            receipt.append(String.format("Allowances                             Rs%10.2f\n", allowances));
            receipt.append(String.format("Gross Salary                           Rs%10.2f\n\n", grossSalary));
            receipt.append("-----------------------------------------------------\n");
            receipt.append("DEDUCTIONS                                  AMOUNT   \n");
            receipt.append("-----------------------------------------------------\n");
            receipt.append(String.format("Tax (%.2f%%)                             Rs%10.2f\n", tax, taxAmount));
            receipt.append(String.format("Other Deductions                       Rs%10.2f\n", deductions));
            receipt.append(String.format("Absences (%d days)                     Rs%10.2f\n", absences, absenceDeduction));
            receipt.append(String.format("Total Deductions                       Rs%10.2f\n\n", (taxAmount + deductions + absenceDeduction)));
            receipt.append("-----------------------------------------------------\n");
            receipt.append(String.format("NET SALARY                             Rs%10.2f\n", netSalary));
            receipt.append("=====================================================\n\n");
            receipt.append("This is a computer-generated receipt.\n");
            receipt.append("No signature required.\n");

            receiptArea.setText(receipt.toString());
            printButton.setEnabled(true);

            savePayrollRecord(salary, allowances, deductions, taxAmount, netSalary);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid number format");
        }
    }

    private void savePayrollRecord(double salary, double allowances, double deductions,
                                   double tax, double netSalary) {
        try {
            Connection conn = DBConnection.getConnection();
            String query = "INSERT INTO payroll (employee_id, pay_period, basic_salary, total_allowances, " +
                    "total_deductions, tax_amount, net_salary, payment_date) VALUES (?, ?, ?, ?, ?, ?, ?, CURDATE())";

            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, Integer.parseInt(employeeIdField.getText()));
            pst.setString(2, payPeriodField.getText());
            pst.setDouble(3, salary);
            pst.setDouble(4, allowances);
            pst.setDouble(5, deductions);
            pst.setDouble(6, tax);
            pst.setDouble(7, netSalary);

            pst.executeUpdate();
            pst.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving payroll record: " + ex.getMessage());
        }
    }

    private void clearFields() {
        employeeIdField.setText("");
        nameField.setText("");
        departmentField.setText("");
        designationField.setText("");
        basicSalaryField.setText("");
        allowancesField.setText("0");
        taxField.setText("10");
        deductionsField.setText("Rs0.00");
        absencesField.setText("0");
        netSalaryField.setText("Rs0.00");
        receiptArea.setText("");
        printButton.setEnabled(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PaymentReceiptForm::new);
    }
}
