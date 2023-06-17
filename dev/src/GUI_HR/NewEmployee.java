package GUI_HR;

import BussinesLogic.Employee;
import BussinesLogic.License;
import Service_HR.SManageEmployees;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

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
            SManageEmployees SManageEmployees = new SManageEmployees();
            List<License> licenses = new ArrayList<>();
            boolean res;
            if(driver.isSelected()) {
                licenses = chooseLicenses(id.getText());
            }
            res = SManageEmployees.insertNewEmployee(firstName.getText(), lastName.getText(), id.getText(), bankAccount.getText(), salary.getText(), terms.getText(), startDate.getText(), driver.isSelected(), licenses);
            if (!res) {
                JOptionPane.showMessageDialog(null, "Invalid input!\nDate's format (YYYY-MM-DD)\nSalary should be a number", "Error", JOptionPane.ERROR_MESSAGE);
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

    private List<License> chooseLicenses(String ID){
        List<License> toReturn = new ArrayList<>();
        // Create an array of employee names
        License[] licenses = License.values();
        String[] licenseNames = new String[licenses.length];
        for (int i = 0; i < licenses.length; i++)
            licenseNames[i] = String.valueOf(licenses[i]);
        // Create a JList with the employee names
        JList<String> LicenseList = new JList<>(licenseNames);

        // Attach a MouseAdapter to the JList to handle mouse events
        LicenseList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                JPanel panel1 = new JPanel();

                // Check if it's a double-click event
                if (e.getClickCount() == 2) {
                    // Get the selected employee index
                    int selectedIndex = LicenseList.locationToIndex(e.getPoint());
                    toReturn.add(licenses[selectedIndex]);
                    // Notify the user
                    String message = "License " + licenses[selectedIndex] + " inserted successfully.";
                    JOptionPane.showMessageDialog(panel1, message, "Driver Insertion", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        // Create a scroll pane for the employee list
        JScrollPane scrollPane = new JScrollPane(LicenseList);

        // Show the employee list in a dialog box
        JPanel panel1 = new JPanel();
        JOptionPane.showOptionDialog(panel1, scrollPane, "Licenses List", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
        return toReturn;
    }

}

