package Presentation.GUI_HR;

import BussinesLogic.BranchStore;
import BussinesLogic.DailyShift;
import Service_HR.SManageBranches;
import Service_HR.SManageShifts;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;

public class ManageShifts extends JFrame{
    private JButton planShiftsButton;
    private JButton updateShiftButton;
    private JButton backButton;
    private JButton showShiftButton;
    private HR_Manager HRM;
    private ManageShifts MS;

    int index;
    ShiftScheduling shiftScheduling;
    public ManageShifts(HR_Manager HR){
        this.HRM = HR;
        this.MS = this;
        setSize(1054, 592);

        // Specify the path to your image file
        String imagePath = "shifts.jpg";

        // Create and set the custom panel as the content pane
        BackgroundImage backgroundPanel = new BackgroundImage(imagePath);
        setContentPane(backgroundPanel);

        backgroundPanel.add(planShiftsButton);
        backgroundPanel.add(updateShiftButton);
        backgroundPanel.add(showShiftButton);
        backgroundPanel.add(backButton);


        ButtonStyle.set(showShiftButton);
        ButtonStyle.set(planShiftsButton);
        ButtonStyle.set(updateShiftButton);
        ButtonStyle.setExit(backButton);

        showShiftButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SManageBranches manageBranches = new SManageBranches();
                String input = JOptionPane.showInputDialog(null, "Enter shift's date: ");
                if(manageBranches.isDate(input)){
                    String branchID = JOptionPane.showInputDialog(null, "Enter branch id: ");
                    if(manageBranches.isInteger(branchID)){
                        if(manageBranches.searchBranch(branchID)) {
                            try {
                                JOptionPane.showMessageDialog(null,manageBranches.get(Integer.parseInt(branchID)).getShiftByDate(input).showMeSchedualing());
                            } catch (SQLException | ClassNotFoundException ex) {
                                JOptionPane.showMessageDialog(null, "Invalid id!\nThis branch is not exist.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }else
                        JOptionPane.showMessageDialog(null, "Invalid id!\nPlease enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                }else
                    JOptionPane.showMessageDialog(null, "Invalid date!\nPlease enter a valid format (YYYY-MM-DD).", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        planShiftsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SManageBranches manageBranches = new SManageBranches();
                LocalDate date = LocalDate.now().plusDays(1);
                index=0;
                int id = manageBranches.getAllBranches().get(index).getBranchID();
                ShiftsTable frame = new ShiftsTable(MS, date, id);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                shiftScheduling = new ShiftScheduling(id, date,frame );
                //setVisible(false);
            }
        });

        updateShiftButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SManageShifts SMS = new SManageShifts();
                SManageBranches SMB = new SManageBranches();
                boolean loop = true;
                do {
                    String input = JOptionPane.showInputDialog(null, "Enter branch id: ");
                    if (SMS.isInteger(input))
                    {
                        if(SMB.searchBranch(input)) {
                            do {
                                String date = JOptionPane.showInputDialog(null, "Enter the shift's date: ");
                                if (SMS.isDate(date)) {
                                    if(SMS.isShift(Integer.parseInt(input), LocalDate.parse(date))){
                                        DailyShift currentShift = SMS.get(Integer.parseInt(input), LocalDate.parse(date));
                                        BranchStore branchStore = SMB.get(Integer.parseInt(input));
                                        UpdateShift updateShift = new UpdateShift(currentShift, branchStore);
                                        updateShift.setVisible(true);
                                        loop = false;
                                    }
                                    else
                                        JOptionPane.showMessageDialog(null, "Shift: " + date + " in Branch: " + input + " is not exist.", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                                else
                                    JOptionPane.showMessageDialog(null, "Invalid date!\nPlease enter a valid format (YYYY-MM-DD).", "Error", JOptionPane.ERROR_MESSAGE);
                            } while (loop);
                        }
                        else
                            JOptionPane.showMessageDialog(null, "Branch: " + input + " is not exist.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    else
                        JOptionPane.showMessageDialog(null, "Invalid type of id!\nOnly integer please.", "Error", JOptionPane.ERROR_MESSAGE);
                }while(loop);
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
                ShiftsTable frame = new ShiftsTable(MS, shiftScheduling.date, id);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                shiftScheduling = new ShiftScheduling(id, shiftScheduling.date,frame );
            }
        }
    }

}
