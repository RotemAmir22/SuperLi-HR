package UiLayer;
import ControllerServiceLayer.*;
import DataAccessLayer.*;
import java.util.Scanner;

public class PresentationSystem {
    public static void main(String[] args) {
        PresentationSystem ps = new PresentationSystem();

        TruckRepository primeTruckRepo = new TruckRepositoryImpl();
        TruckService primeTruckService = new TruckServiceImpl(primeTruckRepo);
        TruckController truckController = new TruckController(primeTruckService);

        StoreRepository primeStoreRepo = new StoreRepositoryImpl();
        StoreService primeStoreService = new StoreServiceImpl(primeStoreRepo);

        SupplierRepository primeSupplierRepo = new SupplierRepositoryImpl();
        SupplierService primeSupplierService = new SupplierServiceImpl(primeSupplierRepo);

        ProductRepository primeProductRepo = new ProductRepositoryImpl();
        ProductService primeProductService = new ProductServiceImpl(primeProductRepo);

        OrderDocumentRepository primeOrderDocRepo = new OrderDocumentRepositoryImpl();
        OrderDocumentService primeOrderDocService = new OrderDocumentServiceImpl(primeOrderDocRepo,
                primeSupplierService,primeStoreService);
        OrderDocumentController orderDocumentController = new OrderDocumentController(primeOrderDocService,
                primeProductService);

        TransitRepository primeTransitRepo = new TransitRepositoryImpl();
        TransitService primeTransitService = new TransitServiceImpl(primeTransitRepo);
        TransitController transitController = new TransitController(primeTransitService);

        ps.switchMenu(truckController,orderDocumentController, transitController);
    }

    public void switchMenu(TruckController truckC,OrderDocumentController orderDocC, TransitController transitC){
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            displayMainMenu();
            choice = scanner.nextInt();
            if (scanner.hasNextLine()) scanner.nextLine();
            switch (choice) {
                case 1:
                    transitC.createNewTransit(scanner);
                    break;
                case 2:
                    int ch2;
                    do{
                        displayUpdateTransitMenu();
                        ch2 = scanner.nextInt();
                        if (scanner.hasNextLine()) scanner.nextLine();
                        switch (ch2){
                            case 1:
                                transitC.printTransitDetails(scanner);
                                break;
                            case 2:
                                orderDocC.showAllOrderDocs();
                                break;
                            case 3:




                        }
                    } while (ch2<0 || ch2>4);

                    break;
                case 3:
                    int ch3;
                do {
                    displayTruckManagerMenu();
                    ch3 = scanner.nextInt();
                    if (scanner.hasNextLine()) scanner.nextLine();
                    handleTruckManager(ch3, scanner, truckC);
                } while (ch3<0 || ch3>3);
                    break;
                case 4: //manage documents
                    break;
                case 5:
                    int ch5;
                    do {
                        displayOrderManagerMenu();
                        ch5 = scanner.nextInt();
                        if (scanner.hasNextLine()) scanner.nextLine();

                        switch (ch5) {
                            case 1:
                                orderDocC.createNewOrderDocument(scanner);
                                break;
                            case 2:
                                orderDocC.showAllOrderDocs();
                                break;
                            case 3:
                                int ch53;
                                int orderId;
                                do {
                                    System.out.println("-----Manage Order-----");
                                    System.out.println("What Order would you like to manage? ");
                                    orderId = scanner.nextInt();
                                    System.out.println("1. Add products to an order ");
                                    System.out.println("2. Change the amount of a product ");
                                    System.out.println("3. Remove products ");
                                    System.out.println("0. Back to main menu");
                                    ch53 = scanner.nextInt();
                                    switch (ch53) {
                                        case 1:
                                            orderDocC.addProductToOrder(orderId, scanner);
                                            break;
                                        case 2:
                                            orderDocC.updateProductAmount(orderId, scanner);
                                            break;
                                        case 3:
                                            orderDocC.removeProductFromOrder(orderId, scanner);
                                            break;
                                        case 0:
                                            System.out.println("\nGoing back...");
                                            break;
                                    }
                                } while (ch53<0 || ch53>3);
                                break;
                            case 0:
                                System.out.println("\nGoing back...");
                                break;
                            default:
                                System.out.println("Invalid input");
                                break;
                        }
                    } while (ch5<0 || ch5>3);
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

    public void displayMainMenu(){
        System.out.println("-----Main Menu-----");
        System.out.println("Please select an option: ");
        System.out.println("1. Create new transit ");
        System.out.println("2. Update transit ");
        System.out.println("3. Manage trucks ");
        System.out.println("4. Manage documents ");
        System.out.println("5. Manage orders ");
        System.out.println("6. Non functional yet ");
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
        System.out.println("2. Show all orders ");
        System.out.println("3. Manage specific order ");
        System.out.println("0. Back to main menu");
    }

    public void handleTruckManager(int ch3, Scanner scanner, TruckController truckC){
        switch (ch3) {
            case 1:
                truckC.createNewTruck(scanner);
                break;
            case 2:
                truckC.removeTruckByPlate(scanner);
                break;
            case 3:
                truckC.printAllTrucks();
                break;
            case 0:
                System.out.println("\nGoing back...");
                break;
            default:
                System.out.println("Invalid input");
                break;
        }
    }

    public void displayUpdateTransitMenu(){
        System.out.println("-----Update Transit-----");
        System.out.println("1. Show transit details ");
        System.out.println("2. Show ?pending? orders ");
        System.out.println("3. Replace truck ");
        System.out.println("4. Remove order from transit ");
        System.out.println("0. Back to main menu ");
    }
}


