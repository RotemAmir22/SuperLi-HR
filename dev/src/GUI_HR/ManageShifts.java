package GUI_HR;

import javax.swing.*;

public class ManageShifts extends JFrame{
    private JButton planShiftsButton;
    private JButton addPermissionsButton;
    private JButton updateShiftButton;

    public ManageShifts(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1054, 592);

        // Specify the path to your image file
        String imagePath = "C:\\Users\\PC\\Documents\\GitHub\\ADSS_Group_H\\docs\\shifts.jpg";

        // Create and set the custom panel as the content pane
        BackgroundImage backgroundPanel = new BackgroundImage(imagePath);
        setContentPane(backgroundPanel);
    }
}
