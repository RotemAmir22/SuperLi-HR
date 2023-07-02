package Presentation.GUI_HR;

import BussinesLogic.Cancellation;
import Service_HR.SManageBranches;
import Service_HR.SManageEmployees;
import Service_HR.SManageShifts;

import javax.swing.*;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.SQLException;
import java.time.LocalDate;

public class EmployeePage extends JFrame {

    private HR_Module HR;
    private String ID;
    private JButton backButton;
    private JButton manageShiftButton;

    public EmployeePage(String ID, HR_Module HR){
        this.HR = HR;
        this.ID = ID;
        setVisible(true);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        SManageEmployees SME = new SManageEmployees();
        // Specify the path to your image file
        String imagePath = "employeePage.jpg";

        // Create and set the custom panel as the content pane
        BackgroundImage backgroundPanel = new BackgroundImage(imagePath);
        setContentPane(backgroundPanel);

        backgroundPanel.add(backButton);
        backgroundPanel.add(manageShiftButton);
        ButtonStyle.set(manageShiftButton);
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

        manageShiftButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SManageBranches manageBranches = new SManageBranches();
                SManageShifts SMS = new SManageShifts();
                LocalDate date = null;
                do {
                    String input = JOptionPane.showInputDialog(null, "Enter a shift's date: ");
                    if (input != null) {
                        if (SMS.isDate(input)) {
                            date = LocalDate.parse(input);
                            String id = JOptionPane.showInputDialog(null, "Enter a branch's id: ");
                            if (manageBranches.isInteger(id))
                                if (manageBranches.searchBranch(id)) {
                                    try {
                                        if (manageBranches.get(Integer.parseInt(id)).getShiftByDate(input).findEmployeeInShiftManager(ID) != null)
                                            createAndShowGUI(date, Integer.parseInt(id), SMS, ID);
                                        else
                                            JOptionPane.showMessageDialog(null, "Error\nYou aren't a shift manager in this shift.", "Error", JOptionPane.ERROR_MESSAGE);

                                    } catch (SQLException | ClassNotFoundException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                    break;
                                } else
                                    JOptionPane.showMessageDialog(null, "Branch " + id + " is not exist.", "Error", JOptionPane.ERROR_MESSAGE);
                            else
                                JOptionPane.showMessageDialog(null, "Invalid input!\nPlease enter a valid id (only number).", "Error", JOptionPane.ERROR_MESSAGE);
                        } else
                            JOptionPane.showMessageDialog(null, "Invalid date!\nPlease enter a valid format (YYYY-MM-DD).", "Error", JOptionPane.ERROR_MESSAGE);
                    }else
                        break;
                }
                    while (true);
            }
        });
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                HR.setVisible(true);
                setVisible(false);
            }
        });
    }

    private static void createAndShowGUI(LocalDate date, int bid, SManageShifts SM, String ID) {
        SManageEmployees SME = new SManageEmployees();
        JFrame frame = new JFrame("Manage Shift: " + date.toString() + " in Branch: " + bid);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Create a panel for the image
        JPanel imagePanel = new JPanel();
        ImageIcon imageIcon = new ImageIcon("dev/src/Presentation/GUI_HR/manageShift.jpg");
        JLabel imageLabel = new JLabel(imageIcon);
        imagePanel.add(imageLabel);
        frame.add(imagePanel, BorderLayout.CENTER);

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton cancelButton = new JButton("Cancel Item");
        JButton uploadReportButton = new JButton("Upload End of Day Report");
        JButton getCancellationButton = new JButton("Get Cancellation");

        ButtonStyle.set(cancelButton);
        ButtonStyle.set(uploadReportButton);
        ButtonStyle.set(getCancellationButton);

        // Add action listeners to the buttons
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle cancel item button action here
                String name = JOptionPane.showInputDialog(null, "Enter item name: ");
                if (name != null) {
                    do {
                        String amount = JOptionPane.showInputDialog(null, "Enter item amount: ");
                        int res;
                        if (SM.isString(name) && SM.isInteger(amount)) {
                            res = SM.cancelItem(date, bid, name, Integer.parseInt(amount), ID);
                            if (res > 0) {
                                JOptionPane.showMessageDialog(null, "Cancellation id: " + res);
                                break;
                            }
                            else {
                                JOptionPane.showMessageDialog(null, "Error.", "Error", JOptionPane.ERROR_MESSAGE);
                                break;
                            }
                        } else
                            JOptionPane.showMessageDialog(null, "Invalid input!\nPlease enter a valid amount number.", "Error", JOptionPane.ERROR_MESSAGE);
                    }while(true);
                }

            }
        });

        uploadReportButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle upload end of day report button action here
                String file = JOptionPane.showInputDialog(null, "Enter comments to file: ");
                File report = new File(file);
                if(SM.uploadEndOfDayReport(report, date, bid))
                    JOptionPane.showMessageDialog(frame, "Upload End of Day Report done!");
                else
                    JOptionPane.showMessageDialog(null, "Failed insert report!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        getCancellationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle get cancellation button action here
                String cancelID = JOptionPane.showInputDialog(null, "Enter cancellation id: ");
                Cancellation cancellation = null;
                if(SM.isInteger(cancelID))
                    cancellation = SM.getC(bid, date, ID, Integer.parseInt(cancelID));
                if(cancellation != null)
                    JOptionPane.showMessageDialog(frame, "Cancellation: " + cancellation.getCancelID() +"\nProducts: "+cancellation.getItem() +"\nAmount: "+cancellation.getAmount());
                else
                    JOptionPane.showMessageDialog(null, "Cancellation not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Add the buttons to the button panel
        buttonPanel.add(cancelButton);
        buttonPanel.add(uploadReportButton);
        buttonPanel.add(getCancellationButton);

        // Add the button panel to the frame
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setSize(500, 400);
        frame.setVisible(true);
    }



}
