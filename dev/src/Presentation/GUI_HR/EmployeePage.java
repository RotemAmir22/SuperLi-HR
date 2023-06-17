package Presentation.GUI_HR;

import javax.swing.*;

public class EmployeePage extends JFrame {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(1200, 630);

    // Specify the path to your image file
    String imagePath = "C:\\Users\\PC\\Documents\\GitHub\\ADSS_Group_H\\docs\\koopa2.jpeg";

    // Create and set the custom panel as the content pane
    BackgroundImage backgroundPanel = new BackgroundImage(imagePath);
    setContentPane(backgroundPanel);
}
