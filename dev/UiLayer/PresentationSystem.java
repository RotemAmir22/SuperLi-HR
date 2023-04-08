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
            System.out.println("-----Main Menu-----");
            System.out.println("Please select an option: ");
            System.out.println("1. Create new transit ");
            System.out.println("2. Update transit ");
            System.out.println("3. Manage trucks ");
            System.out.println("4. Manage documents ");
            System.out.println("5. Manage orders ");
            System.out.println("6. Non functional yet ");
            System.out.println("0. Exit");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    transitC.createNewTransit(scanner);
                    break;
                case 2:
                    System.out.println("You selected Option 2.");
                    break;
                case 3:
                    int ch3;
                do {
                    System.out.println("-----Truck Manager-----");
                    System.out.println("1. Add a new truck ");
                    System.out.println("2. Remove a truck ");
                    System.out.println("3. Show all trucks ");
                    System.out.println("0. Back to main menu");
                    ch3 = scanner.nextInt();
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
                } while (ch3<0 || ch3>3);
                    break;
                case 4: //manage documents

                case 5:
                    int ch5;
                    do {
                        System.out.println("-----Manage Orders-----");
                        System.out.println("1. Create a new Order ");
                        System.out.println("2. Show all orders ");
                        System.out.println("3. manage specific order ");
                        System.out.println("0. Back to main menu");
                        ch5 = scanner.nextInt();
                        switch (ch5) {
                            case 1:
                                orderDocC.createNewOrderDocument(scanner);
                                break;
                            case 2:
                                orderDocC.showAllOrderDocs(scanner);
                                break;
                            case 3:
                                int ch53;
                                do {
                                    System.out.println("\nhow would you like to manage this order? ");
                                    System.out.println("1. Add products to an order ");
                                    System.out.println("2. Change the amount of a product in order ");
                                    System.out.println("3. Remove an order ");
                                    System.out.println("0. Back to main menu");
                                    ch53 = scanner.nextInt();
                                    switch (ch53) {
                                        case 1:
                                            orderDocC.addProductToOrder(scanner);
                                            break;
                                        case 2:
                                            orderDocC.updateProductAmount(scanner);
                                            break;
                                        case 3:
                                            orderDocC.removeProductFromOrder(scanner);
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

}


