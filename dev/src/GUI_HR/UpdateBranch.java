package GUI_HR;

import Service_HR.SManageBranches;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateBranch extends JFrame {
    private JPanel panel1;
    SManageBranches SMB;
    String ID;
    private JButton Name;
    private JButton Phone;
    private JButton OP;

    public UpdateBranch() {
        SMB = new SManageBranches();
        boolean res = validateID();
        if(!res)
            return;

        panel1.setBorder(BorderFactory.createTitledBorder("Update Branch " + ID));

        ButtonStyle.set(Name);
        ButtonStyle.set(Phone);
        ButtonStyle.set(OP);


        panel1.setLayout(new GridLayout(0, 1));

        Name.setMaximumSize(new Dimension(Integer.MAX_VALUE, Name.getPreferredSize().height));
        Phone.setMaximumSize(new Dimension(Integer.MAX_VALUE, Phone.getPreferredSize().height));
        OP.setMaximumSize(new Dimension(Integer.MAX_VALUE, OP.getPreferredSize().height));

        panel1.add(Name);
        panel1.add(Phone);
        panel1.add(OP);

        panel1.setPreferredSize(new Dimension(200, 200));

        getContentPane().add(panel1);

        pack();
        setLocationRelativeTo(null);
        Name.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog(panel1, "Enter the new name:");
                if (input != null) {
                    boolean res = SMB.update(1, ID, input);
                    if (!res)
                        JOptionPane.showMessageDialog(panel1, "Failed to update name.", "Error", JOptionPane.ERROR_MESSAGE);
                    else
                        JOptionPane.showMessageDialog(panel1, "Process completed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
                else
                    JOptionPane.showMessageDialog(panel1, "Operation canceled.", "Canceled", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        Phone.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog(panel1, "Enter the new phone number:");
                if (input != null) {
                    boolean res = SMB.update(2, ID, input);
                    if (!res)
                        JOptionPane.showMessageDialog(panel1, "Failed to update phone number.", "Error", JOptionPane.ERROR_MESSAGE);
                    else
                        JOptionPane.showMessageDialog(panel1, "Process completed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
                else
                    JOptionPane.showMessageDialog(panel1, "Operation canceled.", "Canceled", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        OP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WeeklyShiftTable op = new WeeklyShiftTable(Integer.parseInt(ID));
            }
        });

    }

    /**
     * Validate the employee's ID
     * @return true if found, else false.
     */
    public boolean validateID(){
        String input = JOptionPane.showInputDialog(panel1, "Enter Branch ID:");
        if (input != null) {
            // check if it's a sequence of 6-10 digits (can be changed)
            boolean res = SMB.isInteger(input);
            if (res) {
                if (!SMB.searchBranch(input)) {
                    // Alert the user that the branch was not found
                    JOptionPane.showMessageDialog(panel1, "Branch not found!", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                else{
                    this.ID = input;
                    return true;
                }
            }
            else {
                // Alert the user that the input is incorrect
                JOptionPane.showMessageDialog(panel1, "Invalid input! Please enter a valid Branch ID (an integer).", "Error", JOptionPane.ERROR_MESSAGE);
                validateID();
            }
        }
        else {
            JOptionPane.showMessageDialog(panel1, "Operation canceled.", "Canceled", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        return false;
    }


}
