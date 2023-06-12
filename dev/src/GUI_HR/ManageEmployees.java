package GUI_HR;

import javax.swing.*;
import java.awt.*;

public class ManageEmployees extends JFrame {

    private JButton newButton;
    private JButton updateButton;
    private JButton getInfoButton;
    private JButton calculateSalaryButton;
    private JButton showAllButton;
    private JButton backButton;

    public ManageEmployees(){

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1054, 592);

        // Specify the path to your image file
        String imagePath = "C:\\Users\\PC\\Documents\\GitHub\\ADSS_Group_H\\docs\\managee.jpg";

        // Create and set the custom panel as the content pane
        BackgroundImage backgroundPanel = new BackgroundImage(imagePath);
        setContentPane(backgroundPanel);
        setLayout(new BorderLayout());

        backgroundPanel.add(newButton);
        backgroundPanel.add(updateButton);
        backgroundPanel.add(getInfoButton);
        backgroundPanel.add(calculateSalaryButton);
        backgroundPanel.add(showAllButton);
        backgroundPanel.add(backButton);

        ButtonStyle.set(newButton);
        ButtonStyle.set(updateButton);
        ButtonStyle.set(getInfoButton);
        ButtonStyle.set(calculateSalaryButton);
        ButtonStyle.set(showAllButton);
        ButtonStyle.setExit(backButton);

    }

}

