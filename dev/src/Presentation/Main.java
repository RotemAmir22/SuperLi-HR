package Presentation;

import CLI_Layer.PresentationCli;
import GUI_Layer.PresentationGui;
import Presentation.CLI.HR_Main;
import Presentation.GUI_HR.HR_Module;

import javax.swing.*;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        if (args[0].equalsIgnoreCase("GUI"))
        {
            MainGui mainGui = new MainGui(args[1]);
            mainGui.run();
        }
        else if(args[0].equalsIgnoreCase("CLI"))
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
                        if (args[1].equalsIgnoreCase("Employee") || args[1].equalsIgnoreCase("StoreManager")
                        || args[1].equalsIgnoreCase("HRManager"))
                        {
                            HR_Main.system();
                        }
                        else
                        {
                            System.out.println("User does not have credentials to enter this menu. ");
                        }

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
//            if(Objects.equals(args[1], "HRManager"))
//                HR_Main.system();
//
//            if(Objects.equals(args[1], "LogisticManager"))
//                PresentationCli.transitSystemCli();
//        }
//        else if (Objects.equals(args[0], "GUI"))
//        {
//            if(Objects.equals(args[1], "StoreManager"))
//            {
//                //Main Gui :)))
//            }
//            if(Objects.equals(args[1], "HRManager"))
//            {
//                SwingUtilities.invokeLater(() -> {
//                    HR_Module frame = new HR_Module();
//                    frame.start();
//                });
//            }
//
//            if(Objects.equals(args[1], "LogisticManager"))
//            {
//                SwingUtilities.invokeLater(new Runnable() {
//                    public void run() {
//                        PresentationGui presentationGui = new PresentationGui();
//                    }
//                });
//            }

        }
        else {
            System.out.println("not a valid argument, please re-enter");
        }
    }
}
