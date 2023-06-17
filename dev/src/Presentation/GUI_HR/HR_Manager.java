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

    public HR_Manager(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 630);

        // Specify the path to your image file
        String imagePath = "C:\\Users\\PC\\Documents\\GitHub\\ADSS_Group_H\\docs\\koopa2.jpeg";

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


        manageEmloyeesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open new form here
                // Create and show the ManageEmployees frame
                ManageEmployees MEF = new ManageEmployees();
                MEF.setVisible(true);

                // Hide the HR_Manager frame
                setVisible(false);
            }
        });

        manageBranchesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open new form here
                // Create and show the ManageEmployees frame
                ManageBranches MBF = new ManageBranches();
                MBF.setVisible(true);

                // Hide the HR_Manager frame
                setVisible(false);
            }
        });

        manageShiftsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open new form here
                // Create and show the ManageEmployees frame
                ManageShifts MSF = new ManageShifts();
                MSF.setVisible(true);

                // Hide the HR_Manager frame
                setVisible(false);
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HR_Manager frame = new HR_Manager();
            frame.setVisible(true);
        });

    }

}
