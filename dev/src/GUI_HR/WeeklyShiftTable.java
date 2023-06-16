package GUI_HR;

import Service_HR.SManageBranches;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WeeklyShiftTable extends JFrame {

    private JCheckBox[][] checkBoxes; // Store the checkbox references for later use

    public WeeklyShiftTable(int id) {
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
        panel.setBorder(BorderFactory.createTitledBorder("--- Mark when Branch " + id + " is open ---"));
        checkBoxes = new JCheckBox[rowData.length][columnNames.length - 1];

        for (String columnName : columnNames) {
            panel.add(new JLabel(columnName));
        }

        for (int row = 0; row < rowData.length; row++) {
            panel.add(new JLabel(rowData[row][0].toString())); // Add the day label

            for (int col = 1; col < columnNames.length; col++) {
                checkBoxes[row][col - 1] = (JCheckBox) rowData[row][col]; // Store the checkbox reference
                panel.add(checkBoxes[row][col - 1]); // Add the checkboxes for Morning and Evening shifts
            }
        }

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SManageBranches SMB = new SManageBranches();
                boolean[][] result = collectData();
                SMB.updateOpenHours(id, result);
                JOptionPane.showMessageDialog(null, "Process completed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                setVisible(false);

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
        boolean[][] selectedData = new boolean[checkBoxes.length][checkBoxes[0].length - 1];
        for (int row = 0; row < checkBoxes.length; row++) {
            for (int col = 1; col < checkBoxes[row].length; col++) {
                boolean isSelected  = checkBoxes[row][col - 1].isSelected();
                selectedData[row][col - 1] = isSelected;
            }
        }

        return selectedData;
    }
}

