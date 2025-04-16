package PayrollManagementsystem;

import java.sql.Connection;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.DecimalFormat;
import javax.swing.border.EmptyBorder;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class SearchEmployeeForm extends JFrame {
    private JTextField searchField;
    private JButton searchButton;
    private JTable employeeTable;
    private JComboBox<String> searchByCombo;
    private JButton viewDetailsButton;
    private JButton refreshButton;
    private JButton printButton;
    private JButton deleteButton;

    private DefaultTableModel tableModel;
    private DecimalFormat df = new DecimalFormat("#,##0.00");

    public SearchEmployeeForm() {
        setTitle("SEARCH EMPLOYEE");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel searchLabel = new JLabel("Search By:");
        String[] searchOptions = {"Employee ID", "First Name", "Last Name", "Department"};
        searchByCombo = new JComboBox<>(searchOptions);
        searchField = new JTextField(20);
        searchButton = new JButton("Search");

        searchPanel.add(searchLabel);
        searchPanel.add(searchByCombo);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // Table panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columns = {"ID", "NAME", "DEPARTMENT", "DESIGNATION", "SALARY", "STATUS"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        employeeTable = new JTable(tableModel);
        employeeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        employeeTable.getTableHeader().setReorderingAllowed(false);
        employeeTable.setRowHeight(25);

        employeeTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        employeeTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        employeeTable.getColumnModel().getColumn(2).setPreferredWidth(150);
        employeeTable.getColumnModel().getColumn(3).setPreferredWidth(150);
        employeeTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        employeeTable.getColumnModel().getColumn(5).setPreferredWidth(100);

        JScrollPane scrollPane = new JScrollPane(employeeTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);

        viewDetailsButton = new JButton("View Details");
        refreshButton = new JButton("Refresh");
        printButton = new JButton("Print");
        deleteButton = new JButton("Delete");

        buttonPanel.add(viewDetailsButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(printButton);
        buttonPanel.add(deleteButton);

        tablePanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(searchPanel, BorderLayout.NORTH);
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        searchButton.addActionListener(e -> searchEmployees());
        refreshButton.addActionListener(e -> loadAllEmployees());
        viewDetailsButton.addActionListener(e -> viewEmployeeDetails());
        deleteButton.addActionListener(e -> deleteEmployee());
        printButton.addActionListener(e -> {
            try {
                employeeTable.print();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(SearchEmployeeForm.this,
                        "Error printing table: " + ex.getMessage());
            }
        });

        loadAllEmployees();

        add(mainPanel);
        setVisible(true);
    }

    private void loadAllEmployees() {
        tableModel.setRowCount(0);
        try {
            Connection conn = DBConnection.getConnection();
            String query = "SELECT employee_id, first_name, last_name, department, designation, basic_salary, status " +
                    "FROM employees ORDER BY employee_id";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt("employee_id");
                String name = rs.getString("first_name") + " " + rs.getString("last_name");
                String department = rs.getString("department");
                String designation = rs.getString("designation");
                double salary = rs.getDouble("basic_salary");
                String status = rs.getString("status");

                Object[] row = {id, name, department, designation, "Rs" + df.format(salary), status};
                tableModel.addRow(row);
            }

            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading employees: " + ex.getMessage());
        }
    }

    private void searchEmployees() {
        tableModel.setRowCount(0);

        String searchTerm = searchField.getText().trim();
        if (searchTerm.isEmpty()) {
            loadAllEmployees();
            return;
        }

        try {
            Connection conn = DBConnection.getConnection();
            String query = "";
            PreparedStatement pst = null;

            String searchBy = (String) searchByCombo.getSelectedItem();

            switch (searchBy) {
                case "Employee ID":
                    query = "SELECT employee_id, first_name, last_name, department, designation, basic_salary, status " +
                            "FROM employees WHERE employee_id = ?";
                    pst = conn.prepareStatement(query);
                    try {
                        pst.setInt(1, Integer.parseInt(searchTerm));
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(this, "Please enter a valid employee ID");
                        return;
                    }
                    break;

                case "First Name":
                    query = "SELECT employee_id, first_name, last_name, department, designation, basic_salary, status " +
                            "FROM employees WHERE LOWER(first_name) LIKE ?";
                    pst = conn.prepareStatement(query);
                    pst.setString(1, "%" + searchTerm.toLowerCase() + "%");
                    break;

                case "Last Name":
                    query = "SELECT employee_id, first_name, last_name, department, designation, basic_salary, status " +
                            "FROM employees WHERE LOWER(last_name) LIKE ?";
                    pst = conn.prepareStatement(query);
                    pst.setString(1, "%" + searchTerm.toLowerCase() + "%");
                    break;

                case "Department":
                    query = "SELECT employee_id, first_name, last_name, department, designation, basic_salary, status " +
                            "FROM employees WHERE LOWER(department) LIKE ?";
                    pst = conn.prepareStatement(query);
                    pst.setString(1, "%" + searchTerm.toLowerCase() + "%");
                    break;
            }

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("employee_id");
                String name = rs.getString("first_name") + " " + rs.getString("last_name");
                String department = rs.getString("department");
                String designation = rs.getString("designation");
                double salary = rs.getDouble("basic_salary");
                String status = rs.getString("status");

                Object[] row = {id, name, department, designation, "₱" + df.format(salary), status};
                tableModel.addRow(row);
            }

            if (tableModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "No employees found matching your search criteria");
            }

            rs.close();
            pst.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error searching employees: " + ex.getMessage());
        }
    }

    private void viewEmployeeDetails() {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an employee to view details");
            return;
        }

        int employeeId = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());

        try {
            Connection conn = DBConnection.getConnection();
            String query = "SELECT * FROM employees WHERE employee_id = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, employeeId);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                JDialog detailsDialog = new JDialog(this, "Employee Details", true);
                detailsDialog.setSize(800, 500);
                detailsDialog.setLocationRelativeTo(this);

                JPanel detailsPanel = new JPanel(new BorderLayout(10, 10));
                detailsPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
                detailsPanel.setBackground(Color.WHITE);

                JPanel leftPanel = new JPanel(new GridLayout(10, 2, 5, 10));
                leftPanel.setBackground(Color.WHITE);

                leftPanel.add(new JLabel("Employee ID:"));
                leftPanel.add(new JLabel(rs.getString("employee_id")));

                leftPanel.add(new JLabel("First Name:"));
                leftPanel.add(new JLabel(rs.getString("first_name")));

                leftPanel.add(new JLabel("Last Name:"));
                leftPanel.add(new JLabel(rs.getString("last_name")));

                leftPanel.add(new JLabel("Date of Birth:"));
                leftPanel.add(new JLabel(rs.getString("date_of_birth")));

                leftPanel.add(new JLabel("Gender:"));
                leftPanel.add(new JLabel(rs.getString("gender")));

                leftPanel.add(new JLabel("Email:"));
                leftPanel.add(new JLabel(rs.getString("email")));

                leftPanel.add(new JLabel("Contact:"));
                leftPanel.add(new JLabel(rs.getString("contact")));

                leftPanel.add(new JLabel("Address:"));
                String address = rs.getString("address_line1");
                if (rs.getString("address_line2") != null && !rs.getString("address_line2").isEmpty()) {
                    address += ", " + rs.getString("address_line2");
                }
                leftPanel.add(new JLabel(address));

                leftPanel.add(new JLabel("Postal Code:"));
                leftPanel.add(new JLabel(rs.getString("postal_code")));

                JPanel rightPanel = new JPanel(new GridLayout(7, 2, 5, 10));
                rightPanel.setBackground(Color.WHITE);

                rightPanel.add(new JLabel("Department:"));
                rightPanel.add(new JLabel(rs.getString("department")));

                rightPanel.add(new JLabel("Designation:"));
                rightPanel.add(new JLabel(rs.getString("designation")));

                rightPanel.add(new JLabel("Job Title:"));
                rightPanel.add(new JLabel(rs.getString("job_title")));

                rightPanel.add(new JLabel("Status:"));
                rightPanel.add(new JLabel(rs.getString("status")));

                rightPanel.add(new JLabel("Date Hired:"));
                rightPanel.add(new JLabel(rs.getString("date_hired")));

                rightPanel.add(new JLabel("Basic Salary:"));
                rightPanel.add(new JLabel("Rs" + df.format(rs.getDouble("basic_salary"))));

                JPanel photoPanel = new JPanel(new BorderLayout());
                photoPanel.setBackground(Color.WHITE);
                photoPanel.setBorder(BorderFactory.createTitledBorder("Employee Photo"));

                JLabel photoLabel = new JLabel();
                photoLabel.setPreferredSize(new Dimension(150, 150));
                photoLabel.setHorizontalAlignment(JLabel.CENTER);

                byte[] photoData = rs.getBytes("photo");
                if (photoData != null) {
                    try {
                        InputStream is = new ByteArrayInputStream(photoData);
                        BufferedImage img = ImageIO.read(is);
                        ImageIcon icon = new ImageIcon(img.getScaledInstance(150, 150, Image.SCALE_SMOOTH));
                        photoLabel.setIcon(icon);
                    } catch (Exception e) {
                        e.printStackTrace();
                        photoLabel.setText("Error loading photo");
                    }
                } else {
                    photoLabel.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/PayrollManagementSystem/icon/employee.jpeg"))));
                }

                photoPanel.add(photoLabel, BorderLayout.CENTER);

                JPanel infoPanel = new JPanel(new GridLayout(1, 2, 20, 0));
                infoPanel.setBackground(Color.WHITE);
                infoPanel.add(leftPanel);
                infoPanel.add(rightPanel);

                detailsPanel.add(photoPanel, BorderLayout.WEST);
                detailsPanel.add(infoPanel, BorderLayout.CENTER);

                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                buttonPanel.setBackground(Color.WHITE);

                JButton editButton = new JButton("Edit");
                JButton closeButton = new JButton("Close");

                buttonPanel.add(editButton);
                buttonPanel.add(closeButton);

                detailsPanel.add(buttonPanel, BorderLayout.SOUTH);

                editButton.addActionListener(e -> {
                    detailsDialog.dispose();
                    JOptionPane.showMessageDialog(this, "Edit functionality would open here");
                });

                closeButton.addActionListener(e -> detailsDialog.dispose());

                detailsDialog.add(detailsPanel);
                detailsDialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Employee details not found");
            }

            rs.close();
            pst.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error viewing employee details: " + ex.getMessage());
        }
    }

    private void deleteEmployee() {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an employee to delete");
            return;
        }

        int employeeId = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
        String employeeName = tableModel.getValueAt(selectedRow, 1).toString();

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete employee: " + employeeName + "?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (choice == JOptionPane.YES_OPTION) {
            try {
                Connection conn = DBConnection.getConnection();

                String deleteDeductions = "DELETE FROM deductions WHERE employee_id = ?";
                PreparedStatement pstDeductions = conn.prepareStatement(deleteDeductions);
                pstDeductions.setInt(1, employeeId);
                pstDeductions.executeUpdate();
                pstDeductions.close();

                String deleteAllowances = "DELETE FROM allowances WHERE employee_id = ?";
                PreparedStatement pstAllowances = conn.prepareStatement(deleteAllowances);
                pstAllowances.setInt(1, employeeId);
                pstAllowances.executeUpdate();
                pstAllowances.close();

                String deletePayroll = "DELETE FROM payroll WHERE employee_id = ?";
                PreparedStatement pstPayroll = conn.prepareStatement(deletePayroll);
                pstPayroll.setInt(1, employeeId);
                pstPayroll.executeUpdate();
                pstPayroll.close();

                String deleteEmployee = "DELETE FROM employees WHERE employee_id = ?";
                PreparedStatement pstEmployee = conn.prepareStatement(deleteEmployee);
                pstEmployee.setInt(1, employeeId);
                int result = pstEmployee.executeUpdate();
                pstEmployee.close();

                if (result > 0) {
                    JOptionPane.showMessageDialog(this, "Employee deleted successfully");
                    loadAllEmployees();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete employee");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error deleting employee: " + ex.getMessage());
            }
        }
    }

    // MAIN METHOD TO RUN THE APPLICATION
    public static void main(String[] args) {
        SwingUtilities.invokeLater(SearchEmployeeForm::new);
    }
}
