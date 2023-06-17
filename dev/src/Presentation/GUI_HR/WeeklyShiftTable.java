package Presentation.GUI_HR;

import Service_HR.SManageBranches;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WeeklyShiftTable extends JFrame {

    private JCheckBox[][] checkBoxes; // Store the checkbox references for later use
    boolean ans;
    public WeeklyShiftTable(int id, String message, int[][] avaliablity) {
        // Create the table model with shifts for the week
        String[] columnNames = {"Day", "Morning", "Evening"};
        Object[][] rowData = {
                {"Sunday", new JCheckBox(), new JCheckBox()},
                {"Monday", new JCheckBox(), new JCheckBox()},
                {"Tuesday", new JCheckBox(), new JCheckBox()},
                {"Wednesday", new JCheckBox(), new JCheckBox()},
                {"Thursday", new JCheckBox(), new JCheckBox()},
                {"Friday", new JCheckBox(), new JCheckBox()},
                {"Saturday", new JCheckBox(), new JCheckBox()}
        };

        JPanel panel = new JPanel(new GridLayout(0, columnNames.length));
        panel.setBorder(BorderFactory.createTitledBorder(message));
        checkBoxes = new JCheckBox[7][2];

        for (String columnName : columnNames) {
            panel.add(new JLabel(columnName));
        }

        for (int row = 0; row < 7; row++) {
            panel.add(new JLabel(rowData[row][0].toString())); // Add the day label
            if(avaliablity[row][0] == 0)
                ((JCheckBox) rowData[row][1]).setSelected(true);
            if(avaliablity[row][1] == 0)
                ((JCheckBox) rowData[row][2]).setSelected(true);
            checkBoxes[row][0] = (JCheckBox) rowData[row][1]; // Store the checkbox reference
            checkBoxes[row][1] = (JCheckBox) rowData[row][2]; // Store the checkbox reference
            panel.add(checkBoxes[row][0]); // Add the checkboxes for Morning and Evening shifts
            panel.add(checkBoxes[row][1]); // Add the checkboxes for Morning and Evening shifts
        }

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SManageBranches SMB = new SManageBranches();
                boolean[][] result = collectData();
                setVisible(false);
                int option = JOptionPane.showConfirmDialog(panel, "Do you want to update comments?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    String input = JOptionPane.showInputDialog(panel, "Enter summary for new open hours:");
                    SMB.update(3, String.valueOf(id), input);
                }
                SMB.updateOpenHours(id, result);
                JOptionPane.showMessageDialog(null, "Process completed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);


            }
        });

        panel.add(submitButton);

        getContentPane().add(panel);

        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private boolean[][] collectData() {
        boolean[][] selectedData = new boolean[7][2];
        for (int row = 0; row < 7; row++) {
            selectedData[row][0] = checkBoxes[row][0].isSelected();
            selectedData[row][1] = checkBoxes[row][1].isSelected();
        }
        return selectedData;
    }

    public boolean notifyAns(){ return ans;}
}

