package Presentation.GUI_HR;

import Service_HR.SManageEmployees;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HR_Module extends JFrame {
    private JButton HR_ManagerButton;
    private JButton employeeButton;
    private JButton exitButton;
    private String ID;
    private boolean isMaster = false;

    public HR_Module(){
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 630);

        // Specify the path to your image file
        String imagePath = "koopa2.jpeg";

        // Create and set the custom panel as the content pane
        BackgroundImage backgroundPanel = new BackgroundImage(imagePath);
        setContentPane(backgroundPanel);

        ButtonStyle.set(HR_ManagerButton);
        ButtonStyle.set(employeeButton);
        ButtonStyle.setExit(exitButton);

        backgroundPanel.add(HR_ManagerButton);
        backgroundPanel.add(employeeButton);
        backgroundPanel.add(exitButton);

        HR_Module HR = this;
        HR_ManagerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (isMaster || ID.equals("masterHR")){
                    setVisible(false);
                    new HR_Manager(HR);
                }
                else
                    JOptionPane.showMessageDialog(null, "You don't have permission of HR-manager", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        employeeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SManageEmployees SME = new SManageEmployees();
                if(ID == null || ID.equals("masterHR") || isMaster) {
                    ID = JOptionPane.showInputDialog(null, "Enter an employee ID:");
                    if(ID != null && !SME.isIdType(ID))
                        JOptionPane.showMessageDialog(null, "Invalid input!\nId should be include 6-10 digits", "Error", JOptionPane.ERROR_MESSAGE);
                    else if (!SME.searchEmployee(ID))
                        JOptionPane.showMessageDialog(null, "Invalid input!\nEmployee not exist", "Error", JOptionPane.ERROR_MESSAGE);
                    else
                        new EmployeePage(ID, HR);
                }
                else
                    new EmployeePage(ID, HR);
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    public void start(String input){
        SManageEmployees SME = new SManageEmployees();

        if(input.equalsIgnoreCase("Employee")) {
            do {
                ID = JOptionPane.showInputDialog(this, "Enter your ID:");
                if (!SME.isIdType(ID))
                    JOptionPane.showMessageDialog(null, "Invalid input!\nId should be include 6-10 digits", "Error", JOptionPane.ERROR_MESSAGE);
                else if (!SME.searchEmployee(ID))
                    JOptionPane.showMessageDialog(null, "Invalid input!\nEmployee not exist", "Error", JOptionPane.ERROR_MESSAGE);
                else
                    break;
            }while(true);
        }
        else{
            do {
                ID = JOptionPane.showInputDialog(this, "Enter your ID:");
                if (ID != null && !ID.equals("masterHR")){
                    if(!SME.isIdType(ID))
                        JOptionPane.showMessageDialog(null, "Invalid input!\nId should be include 6-10 digits", "Error", JOptionPane.ERROR_MESSAGE);
                    else if (!SME.searchEmployee(ID))
                        JOptionPane.showMessageDialog(null, "Invalid input!\nEmployee not exist", "Error", JOptionPane.ERROR_MESSAGE);
                    else
                        break;
                }
                else {
                    assert ID != null;
                    isMaster = true;
                    break;
                }
            } while(true);
        }
    }
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            HR_Module frame = new HR_Module();
//            frame.start();
//        });
//
//    }
}
