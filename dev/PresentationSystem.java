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
                    System.out.println("-----Truck Manager-----:");
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

    /**
     * responsible on creating new order document
     * at first it won't have any products in it,
     * only the destination and its source
     **/
    public void createOrderDoc(){
        int supplierId;
        int storeId;

        Scanner scanner = new Scanner(System.in);

        System.out.println("-----Create new Order-----");
        System.out.println("Enter source (supplier id) of the order: "); // assuming valid supplier id
        supplierId = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter destination (store id)  of the order: "); //assuming valid store id
        storeId = scanner.nextInt();
        scanner.nextLine();

        System.out.println("This order id is: "+ OrderDocument.documentNextId);

        // function to call the constructor of order, and will generate new order number

        } //switchCase ??

    /**
    * responsible on adding products to an existing orderDocument
     * @param orderDoc that the user inputs to know which
     * order needs the products
     **/
    public void addProductToOrder(OrderDocument orderDoc) {
        Map<String, Double> productsList = new HashMap<>();
        double weight = 0;
        Scanner scanner = new Scanner(System.in);
        boolean flag = false;
        while (!flag) {
            System.out.println("Please choose which products you want from the supplier: ");
            //function that prints all products instead of what there is now

            System.out.println("1. Banana");
            System.out.println("2. Apple");
            System.out.println("3. Avocado");
            System.out.println("4. Basil");
            String input = scanner.nextLine();

            System.out.println("Please choose what amount of the product you would like: ");
            Double inAmount = scanner.nextDouble();
            scanner.nextLine();
            weight += inAmount;
            productsList.put(input, inAmount);
            System.out.println("Would you like to add more products? ");
            System.out.println(("Y/N "));
            input = scanner.nextLine();

            if (input.equalsIgnoreCase("N")) {
                flag = true;
            }
        }
        orderDoc.setProductsList(productsList); // need to change the string here to products
        orderDoc.setWeight(weight);

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


