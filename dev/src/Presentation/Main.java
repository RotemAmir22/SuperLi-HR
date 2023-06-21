package Presentation;

import CLI_Layer.PresentationCli;
import GUI_Layer.PresentationGui;
import Presentation.CLI.HR_Main;
import Presentation.GUI_HR.HR_Module;

import javax.swing.*;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.Objects;
import java.util.Scanner;

public class Main {//GUIMAIN
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        if (args.length<2)// no user entered
        {
            System.out.println("not enough arguments has been entered");
            return;
        }
        if(args[0].equalsIgnoreCase("CLI"))
        {
            if(Objects.equals(args[1], "StoreManager"))
            {
                Scanner scanner = new Scanner(System.in);
                String choice = "";
                while (choice != "3") {
                    System.out.println("-----Main Menu-----");
                    System.out.println("1. HR Menu");
                    System.out.println("2. Transits Menu");
                    System.out.println("3. Exit");
                    choice = scanner.nextLine();
                    switch (choice) {
                        case "1":
                            HR_Main.system();
                            break;
                        case "2":
                            if (args[1].equalsIgnoreCase("LogisticManager") || args[1].equalsIgnoreCase("StoreManager"))
                            {
                                PresentationCli.transitSystemCli();
                            }
                            else
                            {
                                System.out.println("User does not have credentials to enter this menu. ");
                            }
                            break;
                        case "3":
                            return;
                        default:
                            System.out.println("Invalid choice");
                            break;
                    }
                }
            }
            else if(Objects.equals(args[1], "HRManager"))
                HR_Main.system();

            else if(Objects.equals(args[1], "LogisticManager"))
                PresentationCli.transitSystemCli();
        }
        else if (Objects.equals(args[0], "GUI"))
        {
            if(Objects.equals(args[1], "StoreManager"))
            {
                MainGui mainGui = new MainGui(args[1]);
                mainGui.run();
            }
            else if(Objects.equals(args[1], "HRManager"))
            {
                SwingUtilities.invokeLater(() -> {
                    HR_Module frame = new HR_Module();
                    frame.start("HRManager");
                });
            }
            else if(Objects.equals(args[1], "Employee"))
            {
                SwingUtilities.invokeLater(() -> {
                    HR_Module frame = new HR_Module();
                    frame.start("Employee");
                });
            }

            else if(Objects.equals(args[1], "LogisticManager"))
            {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        PresentationGui presentationGui = new PresentationGui();
                    }
                });
            }
        }
        else {
            System.out.println("not a valid argument, please re-enter");
        }
    }
}
