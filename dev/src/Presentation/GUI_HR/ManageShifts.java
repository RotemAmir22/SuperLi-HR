package Presentation.GUI_HR;

import Service_HR.SManageBranches;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class ManageShifts extends JFrame{
    private JButton planShiftsButton;
    private JButton addPermissionsButton;
    private JButton updateShiftButton;
    private JButton backButton;
    private HR_Manager HRM;
    private ManageShifts MS;

    int index;
    ShiftScheduling shiftScheduling;
    public ManageShifts(HR_Manager HR){
        this.HRM = HR;
        this.MS = this;
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

        planShiftsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SManageBranches manageBranches = new SManageBranches();
                LocalDate date = LocalDate.now().plusDays(1);
                index=0;
                int id = manageBranches.getAllBranches().get(index).getBranchID();
                shiftScheduling = new ShiftScheduling(id, date, new ShiftsTable(MS, date, id));
                setVisible(false);
            }
        });
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open new form here
                // Create and show the ManageEmployees frame
                HRM.setVisible(true);
                // Hide the HR_Manager frame
                setVisible(false);
            }
        });
    }
    public void submit(){
        SManageBranches manageBranches = new SManageBranches();
        if(shiftScheduling!=null) {
            shiftScheduling.submit();
            if(index + 1 < manageBranches.getAllBranches().size()) {
                int id = manageBranches.getAllBranches().get(++index).getBranchID();
                shiftScheduling = new ShiftScheduling(id, shiftScheduling.date, new ShiftsTable(MS, shiftScheduling.date, id));
            }
        }
    }


}
