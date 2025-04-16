package PayrollManagementsystem;


import java.sql.Connection;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.Objects;

public class AddEmployeeForm extends JFrame {
    private JTextField employeeIdField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField dobField;
    private JRadioButton maleRadio;
    private JRadioButton femaleRadio;
    private JTextField emailField;
    private JTextField contactField;
    private JTextField addressLine1Field;
    private JTextField addressLine2Field;
    private JTextField postalCodeField;
    private JTextField departmentField;
    private JTextField designationField;
    private JTextField statusField;
    private JTextField dateHiredField;
    private JTextField basicSalaryField;
    private JTextField jobTitleField;
    private JLabel photoLabel;
    private JButton browseButton;
    private JButton addRecordButton;
    private JButton clearButton;

    private File selectedPhoto;

    public AddEmployeeForm() {
        setTitle("ADD EMPLOYEE | STEP 1 of 3");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Employee ID
        JLabel idLabel = new JLabel("Employee id:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(idLabel, gbc);

        employeeIdField = new JTextField(10);
        employeeIdField.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 0;
        formPanel.add(employeeIdField, gbc);

        // First name
        JLabel firstNameLabel = new JLabel("First name:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(firstNameLabel, gbc);

        firstNameField = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(firstNameField, gbc);

        // Last name (Surname)
        JLabel lastNameLabel = new JLabel("Surname:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(lastNameLabel, gbc);

        lastNameField = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 2;
        formPanel.add(lastNameField, gbc);

        // Date of Birth
        JLabel dobLabel = new JLabel("Date of Birth:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(dobLabel, gbc);

        dobField = new JTextField(10);
        dobField.setText("MM/DD/YY");
        gbc.gridx = 1;
        gbc.gridy = 3;
        formPanel.add(dobField, gbc);

        // Gender
        JLabel genderLabel = new JLabel("Gender:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(genderLabel, gbc);

        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        maleRadio = new JRadioButton("Male");
        femaleRadio = new JRadioButton("Female");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleRadio);
        genderGroup.add(femaleRadio);
        maleRadio.setSelected(true);
        genderPanel.add(maleRadio);
        genderPanel.add(femaleRadio);
        gbc.gridx = 1;
        gbc.gridy = 4;
        formPanel.add(genderPanel, gbc);

        // Email
        JLabel emailLabel = new JLabel("Email:");
        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(emailLabel, gbc);

        emailField = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 5;
        formPanel.add(emailField, gbc);

        // Contact
        JLabel contactLabel = new JLabel("Contact:");
        gbc.gridx = 0;
        gbc.gridy = 6;
        formPanel.add(contactLabel, gbc);

        contactField = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 6;
        formPanel.add(contactField, gbc);

        // Address Line 1
        JLabel addressLine1Label = new JLabel("Address Line 1:");
        gbc.gridx = 0;
        gbc.gridy = 7;
        formPanel.add(addressLine1Label, gbc);

        addressLine1Field = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 7;
        formPanel.add(addressLine1Field, gbc);

        // Address Line 2
        JLabel addressLine2Label = new JLabel("Address Line 2:");
        gbc.gridx = 0;
        gbc.gridy = 8;
        formPanel.add(addressLine2Label, gbc);

        addressLine2Field = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 8;
        formPanel.add(addressLine2Field, gbc);

        // Postal Code
        JLabel postalLabel = new JLabel("Postal Code:");
        gbc.gridx = 0;
        gbc.gridy = 9;
        formPanel.add(postalLabel, gbc);

        postalCodeField = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 9;
        formPanel.add(postalCodeField, gbc);

        // Department
        JLabel departmentLabel = new JLabel("Department:");
        gbc.gridx = 2;
        gbc.gridy = 0;
        formPanel.add(departmentLabel, gbc);

        departmentField = new JTextField(10);
        gbc.gridx = 3;
        gbc.gridy = 0;
        formPanel.add(departmentField, gbc);

        // Designation
        JLabel designationLabel = new JLabel("Designation:");
        gbc.gridx = 2;
        gbc.gridy = 1;
        formPanel.add(designationLabel, gbc);

        designationField = new JTextField(10);
        gbc.gridx = 3;
        gbc.gridy = 1;
        formPanel.add(designationField, gbc);

        // Status
        JLabel statusLabel = new JLabel("Status:");
        gbc.gridx = 2;
        gbc.gridy = 2;
        formPanel.add(statusLabel, gbc);

        statusField = new JTextField(10);
        statusField.setText("Hired");
        gbc.gridx = 3;
        gbc.gridy = 2;
        formPanel.add(statusField, gbc);

        // Date Hired
        JLabel dateHiredLabel = new JLabel("Date Hired:");
        gbc.gridx = 2;
        gbc.gridy = 3;
        formPanel.add(dateHiredLabel, gbc);

        dateHiredField = new JTextField(10);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
        dateHiredField.setText(sdf.format(new Date()));
        gbc.gridx = 3;
        gbc.gridy = 3;
        formPanel.add(dateHiredField, gbc);

        // Basic Salary
        JLabel salaryLabel = new JLabel("Basic Salary:");
        gbc.gridx = 2;
        gbc.gridy = 4;
        formPanel.add(salaryLabel, gbc);

        basicSalaryField = new JTextField(10);
        gbc.gridx = 3;
        gbc.gridy = 4;
        formPanel.add(basicSalaryField, gbc);

        // Job Title
        JLabel jobTitleLabel = new JLabel("Job Title:");
        gbc.gridx = 2;
        gbc.gridy = 5;
        formPanel.add(jobTitleLabel, gbc);

        jobTitleField = new JTextField(10);
        gbc.gridx = 3;
        gbc.gridy = 5;
        formPanel.add(jobTitleField, gbc);

        // Photo panel
        JPanel photoPanel = new JPanel(new BorderLayout());
        photoPanel.setBorder(BorderFactory.createTitledBorder("Profile Photo"));
        photoLabel = new JLabel();
        photoLabel.setPreferredSize(new Dimension(150, 150));
        photoLabel.setHorizontalAlignment(JLabel.CENTER);
        photoLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        photoLabel.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/PayrollManagementsystem/icon/employee.jpeg"))));

        browseButton = new JButton("Insert Picture");

        photoPanel.add(photoLabel, BorderLayout.CENTER);
        photoPanel.add(browseButton, BorderLayout.SOUTH);

        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.gridheight = 6;
        gbc.insets = new Insets(5, 20, 5, 5);
        formPanel.add(photoPanel, gbc);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        addRecordButton = new JButton("Add Record");
        clearButton = new JButton("Clear");

        buttonPanel.add(addRecordButton);
        buttonPanel.add(clearButton);

        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.gridwidth = 5;
        gbc.gridheight = 1;
        gbc.insets = new Insets(20, 5, 5, 5);
        formPanel.add(buttonPanel, gbc);

        // Add action listeners
        addRecordButton.addActionListener(e -> addEmployee());
        clearButton.addActionListener(e -> clearFields());
        browseButton.addActionListener(e -> browsePhoto());

        // Add components to the main panel
        mainPanel.add(formPanel, BorderLayout.CENTER);
        add(mainPanel);

        // Get next employee id on form load
        getNextEmployeeId();
    }

    private void getNextEmployeeId() {
        try {
            Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT MAX(employee_id) FROM employees");

            if (rs.next()) {
                int maxId = rs.getInt(1);
                employeeIdField.setText(String.valueOf(maxId + 1));
            } else {
                employeeIdField.setText("1");
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error fetching employee ID: " + e.getMessage());
        }
    }

    private void addEmployee() {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "INSERT INTO employees (employee_id, first_name, last_name, date_of_birth, gender, email, contact, address_line1, address_line2, postal_code, department, designation, status, date_hired, basic_salary, job_title, photo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, Integer.parseInt(employeeIdField.getText()));
            pstmt.setString(2, firstNameField.getText());
            pstmt.setString(3, lastNameField.getText());
            pstmt.setString(4, dobField.getText());

            String gender = maleRadio.isSelected() ? "Male" : "Female";
            pstmt.setString(5, gender);

            pstmt.setString(6, emailField.getText());
            pstmt.setString(7, contactField.getText());
            pstmt.setString(8, addressLine1Field.getText());
            pstmt.setString(9, addressLine2Field.getText());
            pstmt.setString(10, postalCodeField.getText());
            pstmt.setString(11, departmentField.getText());
            pstmt.setString(12, designationField.getText());
            pstmt.setString(13, statusField.getText());
            pstmt.setString(14, dateHiredField.getText());
            pstmt.setDouble(15, Double.parseDouble(basicSalaryField.getText()));
            pstmt.setString(16, jobTitleField.getText());

            // Set photo blob
            if (selectedPhoto != null) {
                FileInputStream fis = new FileInputStream(selectedPhoto);
                pstmt.setBinaryStream(17, fis, (int) selectedPhoto.length());
            } else {
                pstmt.setNull(17, java.sql.Types.BLOB);
            }

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Employee added successfully!");
                clearFields();
                getNextEmployeeId(); // refresh for next entry
            }

            pstmt.close();
            conn.close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error adding employee: " + ex.getMessage());
        }
    }

    private void clearFields() {
        firstNameField.setText("");
        lastNameField.setText("");
        dobField.setText("MM/DD/YY");
        maleRadio.setSelected(true);
        emailField.setText("");
        contactField.setText("");
        addressLine1Field.setText("");
        addressLine2Field.setText("");
        postalCodeField.setText("");
        departmentField.setText("");
        designationField.setText("");
        statusField.setText("Hired");
        dateHiredField.setText(new SimpleDateFormat("MM/dd/yy").format(new Date()));
        basicSalaryField.setText("");
        jobTitleField.setText("");
        photoLabel.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/PayrollManagementSystem/icon/employee.jpeg"))));
        selectedPhoto = null;
    }

    private void browsePhoto() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Profile Photo");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png"));
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            selectedPhoto = fileChooser.getSelectedFile();
            ImageIcon icon = new ImageIcon(new ImageIcon(selectedPhoto.getAbsolutePath()).getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT));
            photoLabel.setIcon(icon);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AddEmployeeForm().setVisible(true);
        });
    }
}
