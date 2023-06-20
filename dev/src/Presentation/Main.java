package Presentation;

import CLI_Layer.PresentationCli;
import GUI_Layer.PresentationGui;
import Presentation.CLI.HR_Main;
import Presentation.GUI_HR.HR_Module;

import javax.swing.*;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Scanner;

public class Main {//GUIMAIN
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        if(Objects.equals(args[0], "CLI"))
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
                            PresentationCli.transitSystemCli();
                            break;
                        case "3":
                            return;
                        default:
                            System.out.println("Invalid choice");
                            break;
                    }
                }
            }
            if(Objects.equals(args[1], "HRManager"))
                HR_Main.system();

            if(Objects.equals(args[1], "LogisticManager"))
                PresentationCli.transitSystemCli();
        }
        else if (Objects.equals(args[0], "GUI"))
        {
            if(Objects.equals(args[1], "StoreManager"))
            {
                //Main Gui :)))
            }
            if(Objects.equals(args[1], "HRManager"))
            {
                HR_Module frame = new HR_Module();
                frame.start();
            }

            if(Objects.equals(args[1], "LogisticManager"))
            {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        PresentationGui presentationGui = new PresentationGui();
                    }
                });
            }

        }
    }
}
