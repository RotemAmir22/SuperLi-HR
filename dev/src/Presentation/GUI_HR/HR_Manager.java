package Presentation.GUI_HR;

import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HR_Manager extends JFrame {

    private JButton manageEmloyeesButton;
    private JButton manageShiftsButton;
    private JButton manageBranchesButton;
    private JButton exitButton;
    private HR_Module HR;

    public HR_Manager(HR_Module HR){
        this.HR = HR;
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 630);

        // Specify the path to your image file
        String imagePath = "docs/koopa2.jpeg";

        // Create and set the custom panel as the content pane
        BackgroundImage backgroundPanel = new BackgroundImage(imagePath);
        setContentPane(backgroundPanel);


        backgroundPanel.add(manageEmloyeesButton);
        backgroundPanel.add(manageBranchesButton);
        backgroundPanel.add(manageShiftsButton);
        backgroundPanel.add(exitButton);

        ButtonStyle.set(manageEmloyeesButton);
        ButtonStyle.set(manageBranchesButton);
        ButtonStyle.set(manageShiftsButton);
        ButtonStyle.setExit(exitButton);

        HR_Manager HRM = this;
        manageEmloyeesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open new form here
                // Create and show the ManageEmployees frame
                ManageEmployees MEF = new ManageEmployees(HRM);
                MEF.setVisible(true);

                // Hide the HR_Manager frame
                setVisible(false);
            }
        });

        manageBranchesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open new form here
                // Create and show the ManageEmployees frame
                ManageBranches MBF = new ManageBranches(HRM);
                MBF.setVisible(true);

                // Hide the HR_Manager frame
                setVisible(false);
            }
        });

        manageShiftsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open new form here
                // Create and show the ManageEmployees frame
                ManageShifts MSF = new ManageShifts(HRM);
                MSF.setVisible(true);

                // Hide the HR_Manager frame
                setVisible(false);
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                HR.setVisible(true);
                setVisible(false);
            }
        });

    }



}
