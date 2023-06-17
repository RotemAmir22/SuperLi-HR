package GUI_HR;

import Service_HR.SManageBranches;
import Service_HR.SManageEmployees;

import javax.swing.*;
import java.awt.*;

public class NewBranch extends JFrame {

    private JTextField name;
    private JTextField address;
    private JTextField phone;
    private JTextField openHours;
    private JLabel areaLabel;
    private JComboBox<String> areaComboBox;

    private JButton submitButton;

    JPanel panelB;
    public NewBranch(){
        setTitle("New Branch");
    }

    public void getData(){
        int option = JOptionPane.showConfirmDialog(null, panelB, "New Branch store", JOptionPane.OK_CANCEL_OPTION);

        // Check user's choice
        if (option == JOptionPane.OK_OPTION) {
            // User clicked "OK"
            SManageBranches SMB = new SManageBranches();
            int res = SMB.insertNewBranch(name.getText(), address.getText(), (String) areaComboBox.getSelectedItem(), phone.getText(), openHours.getText());
            if (res == -1) {
                JOptionPane.showMessageDialog(null, "Invalid input!\nPhone should be only numbers", "Error", JOptionPane.ERROR_MESSAGE);
                this.setup();
            }
            else {
                JOptionPane.showMessageDialog(this, "Process completed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                this.setVisible(false);
                new WeeklyShiftTable(res, "--- Select branches' open hours ---", SMB.getOpenhours(res));
            }
        }
        else{
            this.setVisible(false);
        }
    }

    public void setup(){

        panelB = new JPanel();
        panelB.setBackground(Color.WHITE);
        panelB.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Create the labels
        JLabel nameLabel = new JLabel("Name:");
        JLabel addressLabel = new JLabel("Address:");
        JLabel phoneLabel = new JLabel("Phone number:");
        JLabel openHoursLabel = new JLabel("Open hours:");
        JLabel areaLabel = new JLabel("Area:");

        // Create the text fields
        name = new JTextField(20);
        address = new JTextField(20);
        phone = new JTextField(20);
        openHours = new JTextField(20);

        // Create the area combo box
        areaComboBox = new JComboBox<>(new String[]{"North", "South", "East", "West", "Center"});

        // Add the components to the panel using GridBagConstraints
        panelB.add(nameLabel, gbc);
        gbc.gridx++;
        panelB.add(name, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelB.add(addressLabel, gbc);
        gbc.gridx++;
        panelB.add(address, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelB.add(phoneLabel, gbc);
        gbc.gridx++;
        panelB.add(phone, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelB.add(openHoursLabel, gbc);
        gbc.gridx++;
        panelB.add(openHours, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelB.add(areaLabel, gbc);
        gbc.gridx++;
        panelB.add(areaComboBox, gbc);

        pack();
        setLocationRelativeTo(null);

        getData();

    }



}
