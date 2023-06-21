package Presentation;

import CLI_Layer.PresentationCli;
import GUI_Layer.PresentationGui;
import Presentation.CLI.HR_Main;
import Presentation.GUI_HR.HR_Module;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.*;

public class MainGui extends JFrame {
    private String managerName;
    private JButton hrMenuButton;
    private JButton transitsMenuButton;
    private JButton exitButton;

    public MainGui(String managerName) {
        this.managerName = managerName;

    }

    public void run() {
        SwingUtilities.invokeLater(() -> {
            initializeComponents();
            setVisible(true);
        });
    }

    public void initializeComponents() {
        JPanel panel = new JPanel();
        getContentPane().add(panel);
        panel.setLayout(null);

        JLabel label = new JLabel("Welcome, " + managerName + "!");
        label.setBounds(100, 20, 200, 20);
        panel.add(label);

        hrMenuButton = new JButton("HR Menu");
        hrMenuButton.setBounds(100, 50, 150, 30);
        panel.add(hrMenuButton);

        transitsMenuButton = new JButton("Transits Menu");
        transitsMenuButton.setBounds(100, 90, 150, 30);
        panel.add(transitsMenuButton);

        exitButton = new JButton("Exit");
        exitButton.setBounds(100, 130, 150, 30);
        panel.add(exitButton);

        hrMenuButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (managerName.equalsIgnoreCase("HRManager")
                        || managerName.equalsIgnoreCase("Employee")
                        || managerName.equalsIgnoreCase("StoreManager")) {
                    SwingUtilities.invokeLater(() -> {
                        HR_Module frame = new HR_Module();
                        frame.start(managerName);
                    });
                }
                else {
                    JOptionPane.showMessageDialog(MainGui.this, "The user does not have credentials to enter this section.", "Access Denied", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        });

        transitsMenuButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (managerName.equalsIgnoreCase("LogisticManager")
                        || managerName.equalsIgnoreCase("StoreManager")) {
                    PresentationGui presentationGui = new PresentationGui();
                }
                else {
                    JOptionPane.showMessageDialog(MainGui.this, "The user does not have credentials to enter this section.", "Access Denied", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        });


        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        setSize(350, 250);
        setTitle("Main Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }
}
