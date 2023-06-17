package Presentation.GUI_HR;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManageShifts extends JFrame{
    private JButton planShiftsButton;
    private JButton addPermissionsButton;
    private JButton updateShiftButton;
    private JButton backButton;

    public ManageShifts(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1054, 592);

        // Specify the path to your image file
        String imagePath = "C:\\Users\\PC\\Documents\\GitHub\\ADSS_Group_H\\docs\\shifts.jpg";

        // Create and set the custom panel as the content pane
        BackgroundImage backgroundPanel = new BackgroundImage(imagePath);
        setContentPane(backgroundPanel);

        backgroundPanel.add(planShiftsButton);
        backgroundPanel.add(addPermissionsButton);
        backgroundPanel.add(updateShiftButton);
        backgroundPanel.add(backButton);

        ButtonStyle.set(planShiftsButton);
        ButtonStyle.set(addPermissionsButton);
        ButtonStyle.set(updateShiftButton);
        ButtonStyle.setExit(backButton);

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open new form here
                // Create and show the ManageEmployees frame
                HR_Manager HR = new HR_Manager();
                HR.setVisible(true);

                // Hide the HR_Manager frame
                setVisible(false);
            }
        });
    }
}
