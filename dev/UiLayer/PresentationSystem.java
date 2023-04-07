package UiLayer;

import ControllerServiceLayer.TruckController;
import DataAccessLayer.TruckRepository;
import DataAccessLayer.TruckRepositoryImpl;
import DomainLayer.OrderDocument;
import ControllerServiceLayer.TruckService;
import ControllerServiceLayer.TruckServiceImpl;

import java.util.*;
import java.util.Scanner;

/**
 * UI
 */
public class PresentationSystem {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        TruckRepository primeTruckRepo = new TruckRepositoryImpl();
        PresentationSystem ps = new PresentationSystem();
        TruckService primeTruckService = new TruckServiceImpl(primeTruckRepo);
        TruckController truckController = new TruckController(primeTruckService);


        ps.switchMenu(truckController);
    }

    public void switchMenu(TruckController truckController){
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("-----Main Menu-----");
            System.out.println("Please select an option: ");
            System.out.println("1. Create new transit ");
            System.out.println("2. Update transit ");
            System.out.println("3. Manage trucks ");
            System.out.println("4. Manage Docs ");
            System.out.println("5. Non functional yet ");
            System.out.println("6. Non functional yet ");
            System.out.println("0. Exit");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("You selected Option 1.");
                    break;
                case 2:
                    System.out.println("You selected Option 2.");
                    break;
                case 3:
                    int ch3;
                do {
                    System.out.println("-----DomainLayer.Truck Manager-----:");
                    System.out.println("1. Add a new truck ");
                    System.out.println("2. Remove a truck ");
                    System.out.println("3. Show all trucks ");
                    System.out.println("0. Back to main menu");
                    ch3 = scanner.nextInt();
                    switch (ch3) {
                        case 1:
                            truckController.createNewTruck(scanner);
                            break;
                        case 2:
                            truckController.removeTruckByPlate(scanner);
                            break;
                        case 3:
                            truckController.printAllTrucks();
                            break;
                        case 0:
                            System.out.println("\nGoing back...");
                            break;
                        default:
                            System.out.println("Invalid input");
                            break;
                    }
                } while (ch3<0 || ch3>3);
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }

        } while (choice != 0);
        scanner.close();
    }

    public void removeProductFromOrder(){};
    public void updateProductAmount(int productId){};
    public void submitOrder(){};
    public void addTransit(){};
    public void updateTransit(){}; //switchCase
    public void addOrderToTransit(){};
    public void printPendingOrders(){};
    public void printByAreaPendingOrders(){};
    public void startTransit(){};
    public void removeOrderFromTransit(){};

}


