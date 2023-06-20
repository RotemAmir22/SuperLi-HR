package Presentation.GUI_HR;

import BussinesLogic.BranchStore;
import BussinesLogic.DailyShift;
import BussinesLogic.Employee;
import BussinesLogic.Role;
import Presentation.CLI.ShiftOrganizer;
import Service_HR.SManageEmployees;
import Service_HR.SManageShifts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class UpdateShift extends JFrame{
    private JButton addEmployeeButton;
    private JButton removeEmployeeButton;
    private JPanel panel1;
    SManageShifts SMS;
    SManageEmployees SME;

    public UpdateShift(DailyShift shift, BranchStore branchStore){
        SMS = new SManageShifts();
        SME = new SManageEmployees();


        ButtonStyle.set(addEmployeeButton);
        ButtonStyle.set(removeEmployeeButton);

        // Initialize the buttons

        panel1.setLayout(new GridLayout(0, 1));
        panel1.add(addEmployeeButton);
        panel1.add(removeEmployeeButton);
        panel1.setVisible(true);
        panel1.setPreferredSize(new Dimension(200, 200));

        getContentPane().add(panel1);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        addEmployeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Employee employee = getEmployee();
                if(shift.isExistMorning(employee) || shift.isExistEvening(employee))
                    JOptionPane.showMessageDialog(null, "Failed to update shift.\nEmployee: " + employee.getId() + " is already work in this shift.", "Error", JOptionPane.ERROR_MESSAGE);
                else {
                    String[] shiftOptions = {"Morning", "Evening"};
                    String selectedShift = (String) JOptionPane.showInputDialog(null, "Select a shift:", "Shift Selection", JOptionPane.QUESTION_MESSAGE, null, shiftOptions, shiftOptions[0]);
                    int c;
                    if(selectedShift.equals("Morning"))
                        c = 0;
                    else
                        c = 1;
                    Role[] availableRoles = Role.values();
                    int totalRoles = availableRoles.length - 1;
                    String[] roleOptions = new String[totalRoles - 1];
                    for (int i = 0; i < totalRoles - 1; i++) {
                        roleOptions[i] = availableRoles[i].toString();
                    }
                    JComboBox<String> roleComboBox = new JComboBox<>(roleOptions);
                    int result;
                    do {
                        result = JOptionPane.showConfirmDialog(null, roleComboBox, "Select a role:", JOptionPane.OK_CANCEL_OPTION);
                        if (result == JOptionPane.OK_OPTION) {
                            String selectedRole = (String) roleComboBox.getSelectedItem();
                            Role role = Role.valueOf(selectedRole);
                            if(employee.canDoRole(role))
                                break;
                            else
                                JOptionPane.showMessageDialog(null, "This employee isn't a " + role, "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }while (true);

                    String selectedRole = (String) roleComboBox.getSelectedItem();
                    Role role = Role.valueOf(selectedRole);
                    try {
                        StringBuilder res = ShiftOrganizer.changeShift(branchStore, shift.getDate(), c, 0, employee, role);
                        SMS.updateShift(branchStore, 0, shift.getDate(), c, employee.getId(), role.ordinal());
                        JOptionPane.showMessageDialog(null, "Employee: " + employee.getId() + " added to shift: " + shift.getDate()+"\n" +res, "Success", JOptionPane.INFORMATION_MESSAGE);
                    } catch (SQLException | ClassNotFoundException ex) {
                        JOptionPane.showMessageDialog(null, "Failed to update shift.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        removeEmployeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Employee employee = getEmployee();
                if(!shift.isExistMorning(employee) && !shift.isExistEvening(employee))
                    JOptionPane.showMessageDialog(null, "Failed to update shift.\nEmployee: " + employee.getId() + " isn't work in this shift.", "Error", JOptionPane.ERROR_MESSAGE);
                else {
                    String[] shiftOptions = {"Morning", "Evening"};
                    String selectedShift = (String) JOptionPane.showInputDialog(null, "Select a shift:", "Shift Selection", JOptionPane.QUESTION_MESSAGE, null, shiftOptions, shiftOptions[0]);
                    int c;
                    if(selectedShift.equals("Morning"))
                        c = 0;
                    else
                        c = 1;
                    Role[] availableRoles = Role.values();
                    int totalRoles = availableRoles.length - 1;
                    String[] roleOptions = new String[totalRoles - 1];
                    for (int i = 0; i < totalRoles - 1; i++) {
                        roleOptions[i] = availableRoles[i].toString();
                    }
                    JComboBox<String> roleComboBox = new JComboBox<>(roleOptions);
                    int result;
                    do {
                        result = JOptionPane.showConfirmDialog(null, roleComboBox, "Select a role:", JOptionPane.OK_CANCEL_OPTION);
                        if (result == JOptionPane.OK_OPTION) {
                            String selectedRole = (String) roleComboBox.getSelectedItem();
                            Role role = Role.valueOf(selectedRole);
                            if(employee.canDoRole(role) && shift.getEmployeeRoleInShift(employee).equals(role))
                                break;
                            else
                                JOptionPane.showMessageDialog(null, "This employee isn't a " + role + " in this shift", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }while (true);

                    String selectedRole = (String) roleComboBox.getSelectedItem();
                    Role role = Role.valueOf(selectedRole);
                    try {
                        StringBuilder res = ShiftOrganizer.changeShift(branchStore, shift.getDate(), c, 1, employee, role);
                        SMS.updateShift(branchStore, 1, shift.getDate(), c, employee.getId(), role.ordinal());
                        JOptionPane.showMessageDialog(null, "Employee: " + employee.getId() + " removed from shift " + shift.getDate() + "\n" + res, "Success", JOptionPane.INFORMATION_MESSAGE);
                    } catch (SQLException | ClassNotFoundException ex) {
                        JOptionPane.showMessageDialog(null, "Failed to update shift.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }
    public Employee getEmployee()
    {
        Employee employee;
        String input;
        do {
            input = JOptionPane.showInputDialog(null, "Enter employee id: ");
            if(SME.isIdType(input)) {
                if (SME.searchEmployee(input)) {
                    employee = SME.get(input);
                    break;
                }
                else
                    JOptionPane.showMessageDialog(null, "Employee: " + input + " is not exist.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else
                JOptionPane.showMessageDialog(null, "Invalid input!\nPlease enter id type (6-10 digits).", "Error", JOptionPane.ERROR_MESSAGE);
        }while (true);
        return employee;
    }
}
