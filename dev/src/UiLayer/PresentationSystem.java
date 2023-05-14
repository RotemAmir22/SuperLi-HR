package UiLayer;
import BussinesLogic.TransitCoordinator;
import ControllerLayer.*;
import DataAccess.DAO_BranchStore;
import DataAccess.DAO_Generator;
import DataAccessLayer.*;
import java.sql.SQLException;
import java.util.*;

public class PresentationSystem {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        PresentationSystem ps = new PresentationSystem();
        Scanner scanner = new Scanner(System.in);


//        DriverDAO primeDriverDAO = new DriverDAOImpl();
//        DriverController primeDriverController = new DriverControllerImpl(primeDriverDAO);
        TruckDAO primeTruckDAO = new TruckDAOImpl();
        TruckController primeTruckController = new TruckControllermpl(primeTruckDAO);
        TruckPresentation truckPresentation = new TruckPresentation(primeTruckController);

        TransitCoordinator primeTransitCoordinator = new TransitCoordinator();

        SupplierDAO primeSupplierDAO = new SupplierDAOImpl();
        SupplierController primeSupplierController = new SupplierControllerImpl(primeSupplierDAO);

        ProductDAO primeProductDAO = new ProductDAOImpl();
        ProductController primeProductController = new ProductControllerImpl(primeProductDAO);
        DAO_BranchStore primeBranchStoreDAO = DAO_Generator.getBranchStoreDAO();
        OrderDocumentDAO primeOrderDocDAO = new OrderDocumentDAOImpl(primeSupplierDAO,primeBranchStoreDAO,primeProductDAO);
        OrderDocumentController primeOrderDocController = new OrderDocumentControllerImpl(primeOrderDocDAO,
                primeSupplierController,primeTransitCoordinator,primeProductController);
        OrderDocumentPresentation orderDocumentPresentation = new OrderDocumentPresentation(primeOrderDocController,
                primeProductController);


        TransitRecordDAO primeTransitRecordDAO = new TransitRecordsDAOImpl();
        TransitRecordController primeTransitRecordController = new TransitRecordControllerImpl(primeTransitRecordDAO);

        TransitDAO primeTransitDAO = new TransitDAOImpl();
        TransitController primeTransitController = new TransitControllerImpl(primeTransitDAO, primeTruckController,
                primeTransitCoordinator, primeOrderDocController, primeTransitRecordController);
        TransitPresentation transitPresentation = new TransitPresentation(primeTransitController, primeTruckController, primeTransitCoordinator);


        ps.switchMenu(scanner, truckPresentation,orderDocumentPresentation, transitPresentation);
    }

    public void switchMenu(Scanner scanner, TruckPresentation truckP, OrderDocumentPresentation orderDocP, TransitPresentation transitP){
//        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            displayMainMenu();
            choice = scanner.nextInt();
            if (scanner.hasNextLine()) scanner.nextLine();
            switch (choice) {
                case 1: // create new transit
                    transitP.createNewTransit(scanner);
                    break;
                case 2: // open update transit menu
                    int ch2;
                    do{
                        displayUpdateTransitMenu();
                        ch2 = scanner.nextInt();
                        if (scanner.hasNextLine()) scanner.nextLine();
                        handleUpdateTransitMenu(ch2, scanner, transitP, orderDocP);
                    } while (ch2<0 || ch2>6);
                    break;
                case 3: // open manage truck menu
                    int ch3;
                do {
                    displayTruckManagerMenu();
                    ch3 = scanner.nextInt();
                    if (scanner.hasNextLine()) scanner.nextLine();
                    handleTruckManagerMenu(ch3, scanner, truckP);
                } while (ch3<0 || ch3>3);
                    break;
                case 4: //manage documents
                    int ch4;
                    do{
                        displayDocumentManagerMenu();
                        ch4 = scanner.nextInt();
                        if (scanner.hasNextLine()) scanner.nextLine();
                        handleDocumentManagerMenu(ch4, transitP, orderDocP);
                    } while (ch4<0 || ch4>3);
                    break;
                case 5: //manage orders
                    int ch5;
                    do {
                        displayOrderManagerMenu();
                        ch5 = scanner.nextInt();
                        if (scanner.hasNextLine()) scanner.nextLine();
                        handleOrderManagerMenu(scanner, ch5, orderDocP);
                    } while (ch5<0 || ch5>3);
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid input");
                    break;
            }
        } while (choice != 0);
        scanner.close();
    }
    public void displayMainMenu(){
        System.out.println("-----Main Menu-----");
        System.out.println("Please select an option: ");
        System.out.println("1. Create new transit ");
        System.out.println("2. Update transit ");
        System.out.println("3. Manage trucks ");
        System.out.println("4. Manage documents ");
        System.out.println("5. Manage orders ");
        System.out.println("0. Exit");
    }
    public void displayTruckManagerMenu(){
        System.out.println("-----Truck Manager-----");
        System.out.println("1. Add a new truck ");
        System.out.println("2. Remove truck ");
        System.out.println("3. Show all trucks ");
        System.out.println("0. Back to main menu ");
    }
    public void displayOrderManagerMenu(){
        System.out.println("-----Manage Orders-----");
        System.out.println("1. Create a new Order ");
        System.out.println("2. Show pending orders (by area) ");
        System.out.println("3. Edit order ");
        System.out.println("0. Back to main menu");
    }
    public void displayUpdateTransitMenu(){
        System.out.println("-----Update Transit-----");
        System.out.println("1. print transit details ");
        System.out.println("2. print pending orders ");
        System.out.println("3. Add order to transit ");
        System.out.println("4. Remove order from transit ");
        System.out.println("5. Replace truck ");
        System.out.println("6. Start transit ");
        System.out.println("0. Back to main menu ");
    }
    public void displayEditOrderMenu(){
        System.out.println("-----Edit Order-----");
        System.out.println("1. Add products to an order ");
        System.out.println("2. Change the amount of a product ");
        System.out.println("3. Remove products ");
        System.out.println("0. Back to main menu");
    }
    public void displayDocumentManagerMenu(){
        System.out.println("-----Manage Documents-----");
        System.out.println("1. Show pending orders (by area) ");
        System.out.println("2. Show completed orders ");
        System.out.println("3. Show Transit records ");
        System.out.println("0. Back to main menu");
    }
    public void handleDocumentManagerMenu(int ch4, TransitPresentation transitP, OrderDocumentPresentation orderDocP){
        switch (ch4) {
            case 1:
                orderDocP.printPendingOrderDocs();
                break;
            case 2:
                orderDocP.printCompletedOrderDocs();
                break;
            case 3:
                transitP.printAllTransitRecords();
                break;
            case 0:
                System.out.println("Going back...");
                break;
            default:
                System.out.println("Invalid input");
                break;
        }
    }
    public void handleTruckManagerMenu(int ch3, Scanner scanner, TruckPresentation truckP){
        switch (ch3) {
            case 1:
                truckP.createNewTruck(scanner);
                break;
            case 2:
                truckP.removeTruckByPlate(scanner);
                break;
            case 3:
                truckP.printAllTrucks();
                break;
            case 0:
                System.out.println("Going back...");
                break;
            default:
                System.out.println("Invalid input");
                break;
        }
    }
    public void handleUpdateTransitMenu(int ch2, Scanner scanner, TransitPresentation transitP, OrderDocumentPresentation orderDocP){
        switch (ch2){
            case 1:
                transitP.printTransitDetails(scanner);
                break;
            case 2:
                orderDocP.printPendingOrderDocs();
                break;
            case 3: transitP.addOrderToTransit(scanner);
                break;
            case 4:
                transitP.removeOrderFromTransit(scanner);
                break;
            case 5:
                transitP.replaceTransitTruck(scanner);
            case 6:
                transitP.beginTransit(scanner);
                break;
            case 0:
                System.out.println("Going back...");
                break;
            default:
                System.out.println("Invalid input");
                break;
        }
    }
    public void handleOrderManagerMenu(Scanner scanner, int ch5, OrderDocumentPresentation orderDocP){
        switch (ch5) {
            case 1: // create new order
                orderDocP.createNewOrderDocument(scanner);
                break;
            case 2: //prints all orders
                orderDocP.printPendingOrderDocs();
                break;
            case 3: //open edit order menu
                int ch53;
                do {
                    displayEditOrderMenu();
                    ch53 = scanner.nextInt();
                    if (scanner.hasNextLine()) scanner.nextLine();
                    handleEditOrderMenu(scanner, ch53, orderDocP);
                } while (ch53<0 || ch53>3);
                break;
            case 0:
                System.out.println("Going back...");
                break;
            default:
                System.out.println("Invalid input");
                break;
        }
    }
    public void handleEditOrderMenu(Scanner scanner, int ch53, OrderDocumentPresentation orderDocP){
        switch (ch53) {
            case 1:
                orderDocP.addProductToOrder(scanner);
                break;
            case 2:
                orderDocP.updateProductAmount(scanner);
                break;
            case 3:
                orderDocP.removeProductFromOrder(scanner);
                break;
            case 0:
                System.out.println("Going back...");
                break;
        }
    }
}


