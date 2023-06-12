package GUI_HR;

import Service_HR.ManageEmployees;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewEmployee extends JFrame {
    private JTextField startDate;
    private JTextField firstName;
    private JTextField lastName;
    private JTextField id;
    private JTextField bankAccount;
    private JTextField salary;
    private JTextField terms;
    private JPanel panelNewE;
    private JCheckBox driver;

    public NewEmployee() {
        setTitle("New Employee");
    }

    public void getData(){
        int option = JOptionPane.showConfirmDialog(null, panelNewE, "New Employee", JOptionPane.OK_CANCEL_OPTION);

        // Check user's choice
        if (option == JOptionPane.OK_OPTION) {
            // User clicked "OK"
            ManageEmployees manageEmployees = new ManageEmployees();
            boolean res = manageEmployees.insertNewEmployee(firstName.getText(), lastName.getText(), id.getText(), bankAccount.getText(), salary.getText(), terms.getText(), startDate.getText(), driver.isSelected());
            if (!res) {
                JOptionPane.showMessageDialog(null, "Invalid input!\nDate's format (YYY-MM-DD)\nSalary should be a number", "Error", JOptionPane.ERROR_MESSAGE);
                this.setup();
            }
            else {
                JOptionPane.showMessageDialog(this, "Process completed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                this.setVisible(false);
            }

        }
        else{
            this.setVisible(false);
        }
    }

    public void setup(){
        // Create the FN_ panel
        panelNewE = new JPanel();
        panelNewE.setBackground(Color.WHITE);
        panelNewE.setLayout(new GridLayout(8, 2, 12, 12));

        // Create the FN label
        JLabel SD = new JLabel("Start date (YYY-MM-DD):");
        JLabel FN = new JLabel("First Name:");
        JLabel LN = new JLabel("Last Name:");
        JLabel ID = new JLabel("ID:");
        JLabel BA = new JLabel("Bank account:");
        JLabel SA = new JLabel("Salary (a number):");
        JLabel TE = new JLabel("Terms of employment:");

        // Create the FirstName text field
        startDate = new JTextField(20);
        firstName = new JTextField(20);
        lastName = new JTextField(20);
        id = new JTextField(20);
        bankAccount = new JTextField(20);
        salary = new JTextField(20);
        terms = new JTextField(20);

        // Add the labels and text fields to the FN_ panel
        panelNewE.add(SD);
        panelNewE.add(startDate);
        panelNewE.add(FN);
        panelNewE.add(firstName);
        panelNewE.add(LN);
        panelNewE.add(lastName);
        panelNewE.add(ID);
        panelNewE.add(id);
        panelNewE.add(BA);
        panelNewE.add(bankAccount);
        panelNewE.add(SA);
        panelNewE.add(salary);
        panelNewE.add(TE);
        panelNewE.add(terms);
        panelNewE.add(driver);

        // Add the FN_ panel to the frame's content pane
        getContentPane().add(panelNewE);

        pack();
        setLocationRelativeTo(null);
        getData();
    }

}

