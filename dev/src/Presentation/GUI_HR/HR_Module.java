package Presentation.GUI_HR;

import Service_HR.AValidateInput;
import Service_HR.SManageEmployees;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HR_Module extends JFrame {
    private JButton HR_ManagerButton;
    private JButton employeeButton;
    private JButton exitButton;
    private String ID;

    public HR_Module(){
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 630);

        // Specify the path to your image file
        String imagePath = "C:\\Users\\PC\\Documents\\GitHub\\ADSS_Group_H\\docs\\koopa2.jpeg";

        // Create and set the custom panel as the content pane
        BackgroundImage backgroundPanel = new BackgroundImage(imagePath);
        setContentPane(backgroundPanel);

        ButtonStyle.set(HR_ManagerButton);
        ButtonStyle.set(employeeButton);
        ButtonStyle.setExit(exitButton);

        backgroundPanel.add(HR_ManagerButton);
        backgroundPanel.add(employeeButton);
        backgroundPanel.add(exitButton);
        HR_ManagerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (ID.equals("masterHR")){
                    setVisible(false);
                    new HR_Manager();
                }
                else
                    JOptionPane.showMessageDialog(null, "You don't have permission of HR-manager", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        employeeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(ID == null || ID.equals("masterHR"))
                    ID = JOptionPane.showInputDialog(null, "Enter an employee ID:");
                new EmployeePage(ID);
                setVisible(false);
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private void start(){
        SManageEmployees SME = new SManageEmployees();
        do {
            ID = JOptionPane.showInputDialog(this, "Enter your ID:");
            if (!ID.equals("masterHR")){
                if(!SME.isIdType(ID))
                    JOptionPane.showMessageDialog(null, "Invalid input!\nId should be include 6-10 digits", "Error", JOptionPane.ERROR_MESSAGE);
                else if (!SME.searchEmployee(ID))
                    JOptionPane.showMessageDialog(null, "Invalid input!\nEmployee not exist", "Error", JOptionPane.ERROR_MESSAGE);
                else
                    break;
            }
            else{
                break;
            }
        } while(true);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HR_Module frame = new HR_Module();
            frame.start();
        });

    }
}
