package Presentation.GUI_HR;

import Service_HR.SManageEmployees;

import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.*;

public class EmployeePage extends JFrame {

    public EmployeePage(String ID){
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(640, 360);
        SManageEmployees SME = new SManageEmployees();
        // Specify the path to your image file
        String imagePath = "C:\\Users\\PC\\Documents\\GitHub\\ADSS_Group_H\\docs\\employeePage.jpg";

        // Create and set the custom panel as the content pane
        BackgroundImage backgroundPanel = new BackgroundImage(imagePath);
        setContentPane(backgroundPanel);

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

    }


}
