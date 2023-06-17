package Presentation.GUI_HR;

import BussinesLogic.License;
import BussinesLogic.Role;
import Service_HR.SManageEmployees;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateEmployee extends JFrame {
    private JButton bankAccountButton;
    private JPanel panel1;
    private JButton driverManagmentButton;
    private JButton salaryButton;
    private JButton employeeTermsButton;
    private JButton addBonusButton;
    private JButton qualificationButton;
    SManageEmployees SManageEmployees;
    String ID;

    public UpdateEmployee() {
        SManageEmployees = new SManageEmployees();
        boolean res = validateID();
        if(!res)
            return;

        panel1.setBorder(BorderFactory.createTitledBorder("Update Employee " + ID));

        ButtonStyle.set(bankAccountButton);
        ButtonStyle.set(driverManagmentButton);
        ButtonStyle.set(salaryButton);
        ButtonStyle.set(employeeTermsButton);
        ButtonStyle.set(addBonusButton);
        ButtonStyle.set(qualificationButton);


        panel1.setLayout(new GridLayout(0, 1));

        bankAccountButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, bankAccountButton.getPreferredSize().height));
        driverManagmentButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, driverManagmentButton.getPreferredSize().height));
        salaryButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, salaryButton.getPreferredSize().height));
        employeeTermsButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, employeeTermsButton.getPreferredSize().height));
        addBonusButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, addBonusButton.getPreferredSize().height));
        qualificationButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, qualificationButton.getPreferredSize().height));

        panel1.add(bankAccountButton);
        panel1.add(salaryButton);
        panel1.add(employeeTermsButton);
        panel1.add(addBonusButton);
        panel1.add(qualificationButton);
        panel1.add(driverManagmentButton);

        panel1.setPreferredSize(new Dimension(200, 200));

        getContentPane().add(panel1);

        pack();
        setLocationRelativeTo(null);
        bankAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog(panel1, "Enter bank account number:");
                if (input != null) {
                    boolean res = SManageEmployees.update(1, ID, input);
                    if (!res)
                        JOptionPane.showMessageDialog(panel1, "Failed to update bank account number.", "Error", JOptionPane.ERROR_MESSAGE);
                    else
                        JOptionPane.showMessageDialog(panel1, "Process completed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
                else
                    JOptionPane.showMessageDialog(panel1, "Operation canceled.", "Canceled", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        salaryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean res;
                String input;
                do {
                    input = JOptionPane.showInputDialog(panel1, "Enter new salary:");
                    if (input != null) {
                        res = SManageEmployees.isDouble(input);
                        if (!res) {
                            JOptionPane.showMessageDialog(panel1, "Invalid input! Please enter a valid salary (a number).", "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            res = SManageEmployees.update(2, ID, input);
                            if (!res) {
                                JOptionPane.showMessageDialog(panel1, "Failed to update salary.", "Error", JOptionPane.ERROR_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(panel1, "Process completed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                                break; // Exit the loop when the process is completed successfully
                            }
                        }
                    } else {
                        // User clicked on "Cancel" or closed the dialog
                        JOptionPane.showMessageDialog(panel1, "Operation canceled.", "Canceled", JOptionPane.INFORMATION_MESSAGE);
                        break; // Exit the loop when the operation is canceled
                    }
                } while (true);
            }
        });

        employeeTermsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog(panel1, "Enter new employee's terms:");
                if (input != null) {
                    boolean res = SManageEmployees.update(3, ID, input);
                    if (!res)
                        JOptionPane.showMessageDialog(panel1, "Failed to update employee's terms.", "Error", JOptionPane.ERROR_MESSAGE);
                    else
                        JOptionPane.showMessageDialog(panel1, "Process completed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        addBonusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean res;
                String input;
                do {
                    input = JOptionPane.showInputDialog(panel1, "Enter bonus' amount:");
                    if (input != null) {
                        res = SManageEmployees.isDouble(input);
                        if (!res) {
                            JOptionPane.showMessageDialog(panel1, "Invalid input! Please enter a valid bonus (a number).", "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            res = SManageEmployees.update(4, ID, input);
                            if (!res) {
                                JOptionPane.showMessageDialog(panel1, "Failed to update bonus.", "Error", JOptionPane.ERROR_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(panel1, "Process completed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                                break; // Exit the loop when the process is completed successfully
                            }
                        }
                    } else {
                        // User clicked on "Cancel" or closed the dialog
                        JOptionPane.showMessageDialog(panel1, "Operation canceled.", "Canceled", JOptionPane.INFORMATION_MESSAGE);
                        break; // Exit the loop when the operation is canceled
                    }
                } while (true);
            }
        });

        qualificationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] options = {"Add", "Remove"};

                int choice = JOptionPane.showOptionDialog(panel1, "Select an option:", "Qualification", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if (choice == 0) {
                    do {
                        String newRole = JOptionPane.showInputDialog(panel1, "Enter the new role:");
                        if (newRole != null) {
                            if (SManageEmployees.validateRole(newRole)) {
                                if (SManageEmployees.existRole(ID, Role.valueOf(newRole)))
                                    JOptionPane.showMessageDialog(panel1, "Invalid input! This employee already has this role.", "Error", JOptionPane.ERROR_MESSAGE);
                                else {
                                    boolean res = SManageEmployees.update(5, ID, newRole);
                                    if (res)
                                        JOptionPane.showMessageDialog(panel1, "Process completed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                                    else
                                        JOptionPane.showMessageDialog(panel1, "Failed to add this role.", "Error", JOptionPane.ERROR_MESSAGE);
                                    break;
                                }
                            }
                            else
                                JOptionPane.showMessageDialog(panel1, "Invalid input! Please enter a valid role.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        else{
                            JOptionPane.showMessageDialog(panel1, "Operation canceled.", "Canceled", JOptionPane.INFORMATION_MESSAGE);
                            break;
                        }
                    } while (true);

                } else if (choice == 1) {
                    do {
                        String newRole = JOptionPane.showInputDialog(panel1, "Enter the exist role:");
                        if (newRole != null) {
                            if(SManageEmployees.validateRole(newRole)) {
                                if(SManageEmployees.existRole(ID, Role.valueOf(newRole)))
                                {
                                    boolean res = SManageEmployees.update(6, ID, newRole);
                                    if(res)
                                        JOptionPane.showMessageDialog(panel1, "Process completed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                                    else
                                        JOptionPane.showMessageDialog(panel1, "Failed to remove this role.", "Error", JOptionPane.ERROR_MESSAGE);
                                    break;
                                }
                                else
                                    JOptionPane.showMessageDialog(panel1, "Invalid input! This employee hasn't this role.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            else
                                JOptionPane.showMessageDialog(panel1, "Invalid input! Please enter a valid role.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        else{
                            JOptionPane.showMessageDialog(panel1, "Operation canceled.", "Canceled", JOptionPane.INFORMATION_MESSAGE);
                            break;
                        }
                    } while (true);
                }
            }
        });
        driverManagmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(SManageEmployees.isDriver(ID)){
                    String[] options = {"Add", "Remove"};
                    int choice = JOptionPane.showOptionDialog(panel1, "Select an option:", "Qualification", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                    if (choice == 0) {
                        do {
                            String newL = JOptionPane.showInputDialog(panel1, "Enter the new license:");
                            if (newL != null) {
                                if(SManageEmployees.validateLicence(newL)) {
                                    if(!SManageEmployees.existLicense(ID, License.valueOf(newL)))
                                    {
                                        boolean res = SManageEmployees.update(7, ID, newL);
                                        if(res)
                                            JOptionPane.showMessageDialog(panel1, "Process completed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                                        else
                                            JOptionPane.showMessageDialog(panel1, "Failed to remove this role.", "Error", JOptionPane.ERROR_MESSAGE);
                                        break;
                                    }
                                    else
                                        JOptionPane.showMessageDialog(panel1, "Invalid input! This employee already has this license.", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                                else
                                    JOptionPane.showMessageDialog(panel1, "Invalid input! Please enter a valid license.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            else{
                                JOptionPane.showMessageDialog(panel1, "Operation canceled.", "Canceled", JOptionPane.INFORMATION_MESSAGE);
                                break;
                            }
                        } while (true);
                    }
                    else if(choice == 1)
                    {
                        do {
                            String newL = JOptionPane.showInputDialog(panel1, "Enter the exist license:");
                            if (newL != null) {
                                if(SManageEmployees.validateLicence(newL)) {
                                    if(SManageEmployees.existLicense(ID, License.valueOf(newL)))
                                    {
                                        boolean res = SManageEmployees.update(8, ID, newL);
                                        if(res)
                                            JOptionPane.showMessageDialog(panel1, "Process completed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                                        else
                                            JOptionPane.showMessageDialog(panel1, "Failed to remove this role.", "Error", JOptionPane.ERROR_MESSAGE);
                                        break;
                                    }
                                    else
                                        JOptionPane.showMessageDialog(panel1, "Invalid input! This employee hasn't this license.", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                                else
                                    JOptionPane.showMessageDialog(panel1, "Invalid input! Please enter a valid license.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            else{
                                JOptionPane.showMessageDialog(panel1, "Operation canceled.", "Canceled", JOptionPane.INFORMATION_MESSAGE);
                                break;
                            }
                        } while (true);
                    }
                }
                else
                    JOptionPane.showMessageDialog(panel1, "This employee isn't a driver", "Error", JOptionPane.ERROR_MESSAGE);



            }
        });
    }

    /**
     * Validate the employee's ID
     * @return true if found, else false.
     */
    public boolean validateID(){
        String input = JOptionPane.showInputDialog(panel1, "Enter employee ID:");
        if (input != null) {
            // check if it's a sequence of 6-10 digits (can be changed)
            boolean res = SManageEmployees.isIdType(input);
            if (res) {
                if (!SManageEmployees.searchEmployee(input)) {
                    // Alert the user that the employee was not found
                    JOptionPane.showMessageDialog(panel1, "Employee not found!", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                else{
                    this.ID = input;
                    return true;
                }
            }
            else {
                // Alert the user that the input is incorrect
                JOptionPane.showMessageDialog(panel1, "Invalid input! Please enter a valid employee ID (6-10 digits).", "Error", JOptionPane.ERROR_MESSAGE);
                validateID();
            }
        }
        else {
            JOptionPane.showMessageDialog(panel1, "Operation canceled.", "Canceled", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        return false;
    }


}
