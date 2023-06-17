package Presentation.GUI_HR;

import Service_HR.SManageEmployees;

import javax.swing.*;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployeePage extends JFrame {

    private HR_Module HR;
    private JButton backButton;

    public EmployeePage(String ID, HR_Module HR){
        this.HR = HR;
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(640, 360);
        SManageEmployees SME = new SManageEmployees();
        // Specify the path to your image file
        String imagePath = "C:\\Users\\PC\\Documents\\GitHub\\ADSS_Group_H\\docs\\employeePage.jpg";

        // Create and set the custom panel as the content pane
        BackgroundImage backgroundPanel = new BackgroundImage(imagePath);
        setContentPane(backgroundPanel);
        
        backgroundPanel.add(backButton);
        ButtonStyle.setExit(backButton);

        this.setTitle("Logged in as - "+ID);

        JPanel panel = new JPanel();

        // Create an instance of JFrame1
        WeeklyShiftTable constraints = new WeeklyShiftTable(ID, "--- Update your constraints ---", SME.getConstraints(ID),"E");

        // Get the content pane of JFrame1
        Container contentPane = constraints.getContentPane();

        // Add the content pane of JFrame1 to the panel
        panel.add(contentPane);

        // Add the panel to the second JFrame
        add(panel);

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                HR.setVisible(true);
                setVisible(false);
            }
        });

    }


}
