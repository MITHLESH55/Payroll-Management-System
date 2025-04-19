Payroll Management System

Overview

The Payroll Management System is a Java-based application that provides a user-friendly interface for managing employee payroll data. Built using Swing for the graphical user interface (GUI) and MySQL for the backend, this system allows users to add, view, and update employee records, calculate payrolls, and generate reports.

Features
Employee Management: Add, edit, and view employee details including personal and job-related information.
Payroll Calculation: Automatically calculate the salary based on employee details, including basic salary, deductions, and allowances.
Data Persistence: Store employee information in a MySQL database for easy retrieval and management.
Multi-step Employee Entry: Simplified multi-step forms to add employees with all necessary details such as name, department, designation, and salary information.
Employee Search: Search for employees based on various criteria.
Reports Generation: Generate employee payroll reports in a structured format.
Photo Management: Upload and manage employee photos as part of the profile.
Technologies Used
Java: Core programming language used for building the application.
Swing: For creating the GUI (Graphical User Interface).
MySQL: Database for storing employee and payroll data.
JDBC: Java Database Connectivity for integrating MySQL with the Java application.
Installation
Clone the Repository:

git clone https://github.com/MITHLESH55/Payroll-Management-System
Set up MySQL Database:

Create a MySQL database.
Import the SQL schema from the database/ folder into your MySQL instance.
Configure Database Connection:

Update the database connection details in the config/DatabaseConfig.java file:
public static final String DB_URL = "jdbc:mysql://localhost:3306/payroll_system";
public static final String USER = "your-username";
public static final String PASSWORD = "your-password";
Compile and Run:

Use your preferred Java IDE (IntelliJ IDEA, Eclipse, etc.) or compile from the command line:
javac -d bin src/*.java
java -cp bin Main
Usage
Start the Application: Launch the application from your IDE or terminal.

Login: The system prompts for authentication (admin credentials to access the payroll management system).

Manage Employees: Use the system to add, update, or delete employee records. Employee information can include:

Name
Department
Job title
Salary details
Contact information
Payroll Calculation: After entering employee details, the system will automatically calculate the salary, considering deductions and allowances.

Reports: Generate employee payroll reports in CSV format or other customizable formats.

Database Schema
employees table:
Column Name	Type	Description
first_name	VARCHAR(50)	Employee's first name
last_name	VARCHAR(50)	Employee's last name
date_of_birth	DATE	Employee's date of birth
gender	VARCHAR(10)	Employee's gender
department	VARCHAR(50)	Employee's department
designation	VARCHAR(50)	Employee's designation
job_title	VARCHAR(50)	Employee's job title
status	VARCHAR(20)	Employment status (active, inactive)
date_hired	DATE	Date of joining
basic_salary	DECIMAL(10, 2)	Basic salary of the employee
email	VARCHAR(100)	Employee's email address
contact	VARCHAR(15)	Employee's contact number
address_line1	VARCHAR(255)	Employee's address (line 1)
address_line2	VARCHAR(255)	Employee's address (line 2)
postal_code	VARCHAR(10)	Postal code of the address
photo	LONGBLOB	Employee's photo
Contributing
Contributions are welcome! If you have any suggestions or improvements, feel free to fork the repository and submit a pull request.
