package UiLayer;
import ControllerServiceLayer.*;
import DataAccessLayer.*;
import DomainLayer.*;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class PresentationSystem {
    public static void main(String[] args) {
        PresentationSystem ps = new PresentationSystem();

        DriverRepository primeDriverRepo = new DriverRepositoryImpl();
        DriverService primeDriverService = new DriverServiceImpl(primeDriverRepo);

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
                primeSupplierService,primeStoreService,primeProductService);
        OrderDocumentController orderDocumentController = new OrderDocumentController(primeOrderDocService,
                primeProductService);

        TransitRepository primeTransitRepo = new TransitRepositoryImpl();
        TransitService primeTransitService = new TransitServiceImpl(primeTransitRepo, primeTruckService,
                primeDriverService, primeOrderDocService);
        TransitController transitController = new TransitController(primeTransitService);


        Set<Qualification> s1 = new HashSet<>();
        Set<Qualification> s2 = new HashSet<>();
        Set<Qualification> sT3 = new HashSet<>();
        Set<Qualification> sT4 = new HashSet<>();
        s1.add(Qualification.C);
        s1.add(Qualification.C1);
        s1.add(Qualification.COOLER);

        s2.add(Qualification.C1);

        sT3.add(Qualification.C);
        sT3.add(Qualification.COOLER);

        sT4.add(Qualification.C1);


        Driver d1 = new Driver(1, "Moshe Mor",s1);
        Driver d2 = new Driver(2, "Dani Lev",s2);
        Product p1 = new Product(1,"Banana");
        Product p2 = new Product(2,"Apple");
        Supplier sup1 = new Supplier("Jerusalem", Area.Center, "David", "0523333333", 1);
        Supplier sup2 = new Supplier("Hiafa", Area.North, "Shlomi", "0524444444", 2);
//        Parking park = new Parking("Parking Street", Area.Center,"Michael", "0525555555");
        Store sro1 = new Store("Bash", Area.South, "Miri", "0526666666", 111);
        Store sro2 = new Store("Mevaseret", Area.Center, "Regev", "0527777777", 112);
        Truck t1 = new Truck("123", TruckModel.LARGETRUCK, 5000, 10000, sT3);
        Truck t2 = new Truck("321", TruckModel.SMALLTRUCK, 100, 2000, sT4);


        primeDriverRepo.saveDriver(d1);
        primeDriverRepo.saveDriver(d2);
        primeProductRepo.saveProduct(p1);
        primeProductRepo.saveProduct(p2);
        primeSupplierRepo.saveSupplier(sup1);
        primeSupplierRepo.saveSupplier(sup2);
        primeStoreRepo.saveStore(sro1);
        primeStoreRepo.saveStore(sro2);
        primeTruckRepo.saveTruck(t1);
        primeTruckRepo.saveTruck(t2);


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
                case 1: // create new transit
                    transitC.createNewTransit(scanner);
                    break;
                case 2: // open update transit menu
                    int ch2;
                    do{
                        displayUpdateTransitMenu();
                        ch2 = scanner.nextInt();
                        if (scanner.hasNextLine()) scanner.nextLine();
                        handleUpdateTransitMenu(ch2, scanner, transitC, orderDocC);
                    } while (ch2<0 || ch2>6);
                    break;
                case 3: // open manage truck menu
                    int ch3;
                do {
                    displayTruckManagerMenu();
                    ch3 = scanner.nextInt();
                    if (scanner.hasNextLine()) scanner.nextLine();
                    handleTruckManagerMenu(ch3, scanner, truckC);
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
                            case 1: // create new order
                                orderDocC.createNewOrderDocument(scanner);
                                break;
                            case 2: //prints all orders
                                orderDocC.showAllOrderDocs();
                                break;
                            case 3: //open edit order menu
                                int ch53;
                                do {
                                    displayEditOrderMenu();
                                    ch53 = scanner.nextInt();
                                    if (scanner.hasNextLine()) scanner.nextLine();
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
                                            System.out.println("Going back...");
                                            break;
                                    }
                                } while (ch53<0 || ch53>3);
                                break;
                            case 0:
                                System.out.println("Going back...");
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
        System.out.println("2. print all orders ");
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

    public void handleTruckManagerMenu(int ch3, Scanner scanner, TruckController truckC){
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
                System.out.println("Going back...");
                break;
            default:
                System.out.println("Invalid input");
                break;
        }
    }
    public void handleUpdateTransitMenu(int ch2, Scanner scanner, TransitController transitC, OrderDocumentController orderDocC){
        switch (ch2){
            case 1:
                transitC.printTransitDetails(scanner);
                break;
            case 2:
                orderDocC.showAllOrderDocs();
                break;
            case 3: transitC.addOrderToTransit(scanner);
                break;
            case 4:
                transitC.removeOrderFromTransit(scanner);
                break;
            case 5:
                transitC.replaceTransitTruck(scanner);
            case 6:
                transitC.beginTransit(scanner);
                break;
            case 0:
                System.out.println("Going back...");
                break;
            default:
                System.out.println("Invalid input");
                break;
        }
    }
}


