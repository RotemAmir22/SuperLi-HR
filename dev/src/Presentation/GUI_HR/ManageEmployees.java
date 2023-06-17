package Presentation.GUI_HR;

import BussinesLogic.Employee;
import Service_HR.SManageEmployees;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class ManageEmployees extends JFrame {

    private JButton newButton;
    private JButton updateButton;
    private JButton getInfoButton;
    private JButton calculateSalaryButton;
    private JButton backButton;
    private JButton resetWeeklyLimitButton;

    public ManageEmployees(){

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1054, 592);

        // Specify the path to your image file
        String imagePath = "C:\\Users\\PC\\Documents\\GitHub\\ADSS_Group_H\\docs\\managee.jpg";

        // Create and set the custom panel as the content pane
        BackgroundImage backgroundPanel = new BackgroundImage(imagePath);
        setContentPane(backgroundPanel);

        backgroundPanel.add(newButton);
        backgroundPanel.add(updateButton);
        backgroundPanel.add(getInfoButton);
        backgroundPanel.add(calculateSalaryButton);
        backgroundPanel.add(resetWeeklyLimitButton);
        backgroundPanel.add(backButton);

        ButtonStyle.set(newButton);
        ButtonStyle.set(updateButton);
        ButtonStyle.set(getInfoButton);
        ButtonStyle.set(calculateSalaryButton);
        ButtonStyle.set(resetWeeklyLimitButton);
        ButtonStyle.setExit(backButton);

        newButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open new form here
                // Create and show the ManageEmployees frame
                NewEmployee NEF = new NewEmployee();
                NEF.setVisible(true);
                NEF.setup();


                // Hide the HR_Manager frame
            }
        });

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                UpdateEmployee UPF = new UpdateEmployee();
                UPF.setVisible(true);
            }
        });

        getInfoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SManageEmployees manageEmployees = new SManageEmployees();
                List<Employee> employees = manageEmployees.getAllEmployees();
                // Create an array of employee names
                String[] employeeNames = new String[employees.size()];
                for (int i = 0; i < employees.size(); i++) {
                    Employee employee = employees.get(i);
                    employeeNames[i] = employee.getName();
                }

                // Create a JList with the employee names
                JList<String> employeeList = new JList<>(employeeNames);

                // Attach a MouseAdapter to the JList to handle mouse events
                employeeList.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        JPanel panel1 = new JPanel();
                        // Check if it's a double-click event
                        if (e.getClickCount() == 2) {
                            // Get the selected employee index
                            int selectedIndex = employeeList.locationToIndex(e.getPoint());
                            if (selectedIndex >= 0) {
                                Employee selectedEmployee = employees.get(selectedIndex);
                                // Show more information about the selected employee
                                showEmployeeDetails(selectedEmployee, panel1, manageEmployees);
                            }
                        }
                    }
                });

                // Create a scroll pane for the employee list
                JScrollPane scrollPane = new JScrollPane(employeeList);

                // Show the employee list in a dialog box
                JPanel panel1 = new JPanel();
                JOptionPane.showOptionDialog(panel1, scrollPane, "Employee List", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
            }
        });

        calculateSalaryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StringBuilder details = new StringBuilder();
                JPanel panel1 = new JPanel();
                SManageEmployees manageEmployees = new SManageEmployees();
                for (Employee employee: manageEmployees.getAllEmployees())
                    details.append(employee.getName()).append(": ").append(employee.getCumulativeSalary()).append("\n");

                // Show a dialog box with a Yes/No option
                JOptionPane.showMessageDialog(panel1, details.toString(), "Employee Details", JOptionPane.INFORMATION_MESSAGE);

                int option = JOptionPane.showOptionDialog(panel1, "Do you want to nullify employees cumulative salary?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

                // Handle the user's choice
                if (option == JOptionPane.YES_OPTION) {
                    for (Employee employee : manageEmployees.getAllEmployees()) {
                        manageEmployees.update(9, employee.getId(), "reset");
                    }
                    JOptionPane.showMessageDialog(panel1, "Process completed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }

            }
        });
        resetWeeklyLimitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Display a confirmation dialog
                JPanel panel1 = new JPanel();
                int option = JOptionPane.showConfirmDialog(panel1, "This action will reset all employees limits to 6 - which means it's a new week!\nPress OK to confirm.", "Confirmation", JOptionPane.OK_CANCEL_OPTION);

                // Handle the user's choice
                if (option == JOptionPane.OK_OPTION) {
                    SManageEmployees manageEmployees = new SManageEmployees();
                    for (Employee employee : manageEmployees.getAllEmployees()) {
                        manageEmployees.update(10, employee.getId(), "reset");
                    }
                    JOptionPane.showMessageDialog(panel1, "Process completed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            }

        });
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open new form here
                // Create and show the ManageEmployees frame
                HR_Manager HR = new HR_Manager();
                HR.setVisible(true);

                // Hide the HR_Manager frame
                setVisible(false);
            }
        });


    }
    private void showEmployeeDetails(Employee employee, JPanel panel1, SManageEmployees m) {
        StringBuilder details = new StringBuilder();
        details.append("ID: ").append(employee.getId()).append("\n");
        details.append("Name: ").append(employee.getName()).append("\n");
        details.append("Start Date: ").append(employee.getStartDate()).append("\n");
        details.append("Shift Salary: ").append(employee.getSalary()).append("\n");
        details.append("Bank Account Number: ").append(employee.getBankAccount()).append("\n");
        details.append("Cumulative Salary: ").append(employee.getCumulativeSalary()).append("\n");
        details.append("Qualifications: ").append(employee.getQualifications()).append("\n");
        if(m.isDriver(employee.getId()))
            details.append("Licenses: ").append(m.getDriver(employee.getId()).getLicenses().toString());

        JOptionPane.showMessageDialog(panel1, details.toString(), "Employee Details", JOptionPane.INFORMATION_MESSAGE);
    }

}

