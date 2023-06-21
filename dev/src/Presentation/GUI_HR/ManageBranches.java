package Presentation.GUI_HR;

import BussinesLogic.BranchStore;
import BussinesLogic.Employee;
import BussinesLogic.Role;
import DomainLayer.OrderDocument;
import DomainLayer.Product;
import DomainLayer.Transit;
import Service_HR.SManageBranches;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Map;

public class ManageBranches extends JFrame {
    private JButton newButton;
    private JPanel panel1;
    private JButton associateEmployeeButton;
    private JButton updateButton;
    private JButton viewTransitButton;
    private JButton showAllButton;
    private JButton backButton;
    private HR_Manager HRM;

    public ManageBranches (HR_Manager HRM){
        this.HRM = HRM;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);

        // Specify the path to your image file
        String imagePath = "branches.jpg";

        // Create and set the custom panel as the content pane
        BackgroundImage backgroundPanel = new BackgroundImage(imagePath);
        setContentPane(backgroundPanel);

        backgroundPanel.add(newButton);
        backgroundPanel.add(updateButton);
        backgroundPanel.add(associateEmployeeButton);
        backgroundPanel.add(viewTransitButton);
        backgroundPanel.add(showAllButton);
        backgroundPanel.add(backButton);

        ButtonStyle.set(newButton);
        ButtonStyle.set(updateButton);
        ButtonStyle.set(associateEmployeeButton);
        ButtonStyle.set(viewTransitButton);
        ButtonStyle.set(showAllButton);
        ButtonStyle.setExit(backButton);

        newButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open new form here
                // Create and show the ManageEmployees frame
                NewBranch NB = new NewBranch();
                NB.setVisible(true);
                NB.setup();

            }
        });

        associateEmployeeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                SManageBranches manageBranches = new SManageBranches();
                String[] options = {"Add", "Remove"};
                int choice = JOptionPane.showOptionDialog(panel1, "Select an option:", "Employee", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if (choice == 0) { // Add
                    do {
                        String input = JOptionPane.showInputDialog(panel1, "Enter the branch id: ");
                        if(input != null) {
                            if (!manageBranches.isInteger(input))
                                JOptionPane.showMessageDialog(panel1, "Invalid type of id!\nOnly integer please.", "Error", JOptionPane.ERROR_MESSAGE);
                            else if (!manageBranches.searchBranch(input))
                                JOptionPane.showMessageDialog(panel1, "There isn't branch with this id.", "Error", JOptionPane.ERROR_MESSAGE);
                            else {
                                int id = Integer.parseInt(input);
                                input = JOptionPane.showInputDialog(panel1, "Enter the employee id: ");
                                int res = manageBranches.addEmployee(id, input);
                                if (res == -1)
                                    JOptionPane.showMessageDialog(panel1, "You cannot add a driver to a branch", "Error", JOptionPane.ERROR_MESSAGE);
                                else if (res == 1)
                                    JOptionPane.showMessageDialog(panel1, "Employee: " + input + " is already work in branch: " + id, "Error", JOptionPane.ERROR_MESSAGE);
                                else if (res == 2)
                                    JOptionPane.showMessageDialog(panel1, "Employee: " + input + " is not exist", "Error", JOptionPane.ERROR_MESSAGE);
                                else {
                                    JOptionPane.showMessageDialog(panel1, "Process completed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                                    break;
                                }
                            }
                        }
                        else{
                            JOptionPane.showMessageDialog(panel1, "Operation canceled.", "Canceled", JOptionPane.INFORMATION_MESSAGE);
                            break;
                        }
                    } while (true);

                }
                else if (choice == 1) { // Remove
                    do {
                        String input = JOptionPane.showInputDialog(panel1, "Enter the branch id: ");
                        if (input != null) {
                            if (!manageBranches.isInteger(input))
                                JOptionPane.showMessageDialog(panel1, "Invalid type of id!\nOnly integer please.", "Error", JOptionPane.ERROR_MESSAGE);
                            else if (!manageBranches.searchBranch(input))
                                JOptionPane.showMessageDialog(panel1, "There isn't branch with this id.", "Error", JOptionPane.ERROR_MESSAGE);
                            else {
                                int id = Integer.parseInt(input);
                                input = JOptionPane.showInputDialog(panel1, "Enter the employee id: ");
                                int res = manageBranches.removeEmployee(id, input);
                                if (res == -1)
                                    JOptionPane.showMessageDialog(panel1, "Employee: " + input + " is not exist", "Error", JOptionPane.ERROR_MESSAGE);
                                else if (res == 1)
                                    JOptionPane.showMessageDialog(panel1, "Employee: " + input + " isn't work in branch: " + id, "Error", JOptionPane.ERROR_MESSAGE);
                                else if (res == 0) {
                                    JOptionPane.showMessageDialog(panel1, "Process completed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                                    break;
                                }

                            }
                        }
                        else {
                            JOptionPane.showMessageDialog(panel1, "Operation canceled.", "Canceled", JOptionPane.INFORMATION_MESSAGE);
                            break;
                        }
                    }while (true);
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open new form here
                // Create and show the ManageEmployees frame
                UpdateBranch UPB = new UpdateBranch();
                UPB.setVisible(true);
            }
        });
        viewTransitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SManageBranches manageBranches = new SManageBranches();
                String input = JOptionPane.showInputDialog(panel1, "Enter the branch id: ");
                if (input != null) {
                    if (!manageBranches.isInteger(input))
                        JOptionPane.showMessageDialog(panel1, "Invalid type of id!\nOnly integer please.", "Error", JOptionPane.ERROR_MESSAGE);
                    else if (!manageBranches.searchBranch(input))
                        JOptionPane.showMessageDialog(panel1, "There isn't branch with this id.", "Error", JOptionPane.ERROR_MESSAGE);
                    else {
                        List<Transit> transits = manageBranches.getAllTransits(Integer.parseInt(input));
                        // Create an array of transits id
                        String[] transitsID = new String[transits.size()];
                        for (int i = 0; i < transits.size(); i++) {
                            Transit transit = transits.get(i);
                            transitsID[i] = "Transit ID " + transit.getTransitId();
                        }

                        // Create a JList with the transits id
                        JList<String> transitList = new JList<>(transitsID);

                        // Attach a MouseAdapter to the JList to handle mouse events
                        transitList.addMouseListener(new MouseAdapter() {
                            public void mouseClicked(MouseEvent e) {
                                JPanel panel1 = new JPanel();
                                // Check if it's a double click event
                                if (e.getClickCount() == 2) {
                                    // Get the selected employee index
                                    int selectedIndex = transitList.locationToIndex(e.getPoint());
                                    if (selectedIndex >= 0) {
                                        Transit selectedTransit = transits.get(selectedIndex);
                                        // Show more information about the selected employee
                                        showTransitDetails(selectedTransit, panel1);
                                    }
                                }
                            }
                        });

                        // Create a scroll pane for the transits id
                        JScrollPane scrollPane = new JScrollPane(transitList);

                        // Show the employee list in a dialog box
                        JPanel panel1 = new JPanel();
                        JOptionPane.showOptionDialog(panel1, scrollPane, "Transits List of branch " + input, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
                    }
                }
            }
        });

        showAllButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SManageBranches manageBranches = new SManageBranches();
                List<BranchStore> branchStores = manageBranches.getAllBranches();
                // Create an array of employee names
                String[] branches = new String[branchStores.size()];
                for (int i = 0; i < branchStores.size(); i++) {
                    BranchStore b = branchStores.get(i);
                    branches[i] = b.getName() +" "+ b.getBranchID();
                }

                // Create a JList with the employee names
                JList<String> branchesList = new JList<>(branches);

                // Attach a MouseAdapter to the JList to handle mouse events
                branchesList.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        JPanel panel1 = new JPanel();
                        // Check if it's a double-click event
                        if (e.getClickCount() == 2) {
                            // Get the selected employee index
                            int selectedIndex = branchesList.locationToIndex(e.getPoint());
                            if (selectedIndex >= 0) {
                                BranchStore selectedBranch = branchStores.get(selectedIndex);
                                // Show more information about the selected employee
                                showAll(selectedBranch, panel1, manageBranches);
                            }
                        }
                    }
                });

                // Create a scroll pane for the employee list
                JScrollPane scrollPane = new JScrollPane(branchesList);

                // Show the employee list in a dialog box
                JPanel panel1 = new JPanel();
                JOptionPane.showOptionDialog(panel1, scrollPane, "Branch stores List", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
            }
        });
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open new form here
                // Create and show the ManageEmployees frame
                HRM.setVisible(true);
                setVisible(false);
            }
        });
    }

    private void showTransitDetails(Transit t, JPanel panel1) {
        StringBuilder orderDocsBuilder = new StringBuilder();
        for (OrderDocument orderDoc : t.getOrdersDocs())
            for(Map.Entry<Product,Double> product : orderDoc.getProductsList().entrySet())
            {
                String name = product.getKey().getProductName();
                orderDocsBuilder.append(name).append(", ");
            }
        if (orderDocsBuilder.length() > 0) {
            orderDocsBuilder.setLength(orderDocsBuilder.length() - 2); // Remove the trailing comma and space
        }
        String orderDocs = orderDocsBuilder.toString();

        String detailsBuilder = "Date: " + t.getTransitDate() + "\n" +
                "Driver ID: " + t.getDriver().getId() + "\n" +
                "Products: " + orderDocs + "\n";

        JOptionPane.showMessageDialog(panel1, detailsBuilder, "Transit's Details", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showAll(BranchStore b, JPanel panel1, SManageBranches m) {
        StringBuilder details = new StringBuilder();
        details.append("ID: ").append(b.getBranchID()).append("\n");
        details.append("Address: ").append(b.getAddress()).append("\n");
        details.append("Open hours: ").append(b.getOpeningTime()).append("\n");
        details.append("Phone number: ").append(b.getPhoneNum()).append("\n");
        details.append("\nEmployees:");

        for (Employee e : b.getEmployees()) {
            details.append("\n\n")
                    .append(e.getName())
                    .append(":")
                    .append("\n");

            for (Role role : e.getQualifications()) {
                details.append(role.name())
                        .append(", ");
            }

            // Remove the trailing comma and space if there are roles
            if (!e.getQualifications().isEmpty()) {
                details.setLength(details.length() - 2);
            }
        }

        JTextArea textArea = new JTextArea(details.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));

        JOptionPane.showMessageDialog(panel1, scrollPane, "Branch Details", JOptionPane.INFORMATION_MESSAGE);
    }


}