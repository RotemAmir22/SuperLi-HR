package Presentation.GUI_HR;

import BussinesLogic.BranchStore;
import BussinesLogic.DailyShift;
import BussinesLogic.Employee;
import BussinesLogic.Role;
import Presentation.CLI.ShiftOrganizer;
import Service_HR.SManageEmployees;
import Service_HR.SManageShifts;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class UpdateShift extends JFrame{
    private JButton addEmployeeButton;
    private JButton removeEmployeeButton;
    private JPanel panel1;
    public UpdateShift(DailyShift shift, BranchStore branchStore){
        SManageShifts SMS = new SManageShifts();
        SManageEmployees SME = new SManageEmployees();
        String input;

        ButtonStyle.set(addEmployeeButton);
        ButtonStyle.set(removeEmployeeButton);

        // Initialize the buttons

        panel1.add(addEmployeeButton);
        panel1.add(removeEmployeeButton);
        panel1.setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel1);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);


        // Make the frame visible

        Employee employee;
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
        addEmployeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(shift.isExistMorning(employee) || shift.isExistEvening(employee))
                    JOptionPane.showMessageDialog(null, "Failed to update shift.\nEmployee: " + employee.getId() + " is already work in this shift.", "Error", JOptionPane.ERROR_MESSAGE);
                else {
                    String[] shiftOptions = {"Morning", "Evening"};
                    int selectedShift = (int) JOptionPane.showInputDialog(null, "Select a shift:", "Shift Selection", JOptionPane.QUESTION_MESSAGE, null, shiftOptions, shiftOptions[0]);

                    Role[] availableRoles = Role.values();
                    int totalRoles = availableRoles.length - 1;
                    String[] roleOptions = new String[totalRoles - 1];
                    for (int i = 0; i < totalRoles - 1; i++) {
                        roleOptions[i] = availableRoles[i].toString();
                    }
                    JComboBox<String> roleComboBox = new JComboBox<>(roleOptions);
                    int result = JOptionPane.showConfirmDialog(null, roleComboBox, "Select a role:", JOptionPane.OK_CANCEL_OPTION);
                    if (result == JOptionPane.OK_OPTION) {
                        Role selectedRole = (Role) roleComboBox.getSelectedItem();
                        try {
                            ShiftOrganizer.changeShift(branchStore, shift.getDate(), selectedShift, 0, employee, selectedRole);
                            JOptionPane.showMessageDialog(null, "Employee: " + employee.getId() + " added to shift: " + shift.getDate(), "Success", JOptionPane.INFORMATION_MESSAGE);
                        } catch (SQLException | ClassNotFoundException ex) {
                            JOptionPane.showMessageDialog(null, "Failed to update shift.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else
                        JOptionPane.showMessageDialog(null, "Operation canceled.", "Canceled", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        removeEmployeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!shift.isExistMorning(employee) && !shift.isExistEvening(employee))
                    JOptionPane.showMessageDialog(null, "Failed to update shift.\nEmployee: " + employee.getId() + " isn't work in this shift.", "Error", JOptionPane.ERROR_MESSAGE);
                else {
                    String[] shiftOptions = {"Morning", "Evening"};
                    int selectedShift = (int) JOptionPane.showInputDialog(null, "Select a shift:", "Shift Selection", JOptionPane.QUESTION_MESSAGE, null, shiftOptions, shiftOptions[0]);

                    Role[] availableRoles = Role.values();
                    int totalRoles = availableRoles.length - 1;
                    String[] roleOptions = new String[totalRoles - 1];
                    for (int i = 0; i < totalRoles - 1; i++) {
                        roleOptions[i] = availableRoles[i].toString();
                    }
                    JComboBox<String> roleComboBox = new JComboBox<>(roleOptions);
                    int result = JOptionPane.showConfirmDialog(null, roleComboBox, "Select a role:", JOptionPane.OK_CANCEL_OPTION);
                    if (result == JOptionPane.OK_OPTION) {
                        Role selectedRole = (Role) roleComboBox.getSelectedItem();
                        try {
                            ShiftOrganizer.changeShift(branchStore, shift.getDate(), selectedShift, 1, employee, selectedRole);
                            JOptionPane.showMessageDialog(null, "Employee: " + employee.getId() + " removed from shift " + shift.getDate(), "Success", JOptionPane.INFORMATION_MESSAGE);
                        } catch (SQLException | ClassNotFoundException ex) {
                            JOptionPane.showMessageDialog(null, "Failed to update shift.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else
                        JOptionPane.showMessageDialog(null, "Operation canceled.", "Canceled", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });


    }
}
