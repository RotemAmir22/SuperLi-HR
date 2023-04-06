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
                    System.out.println("2. Remove a truck ");
                    System.out.println("0. Back to main menu");
                    ch3 = scanner.nextInt();
                    switch (ch3) {
                        case 1:
                            truckController.createNewTruck(scanner);
                            break;
                        case 2:
                            truckController.removeTruckByPlate(scanner);
                            break;
                        case 0:
                            System.out.println("\nGoing back...");
                            break;
                        default:
                            System.out.println("Invalid input");
                            break;
                    }
                } while (ch3<0 || ch3>2);
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



//    /**
//     * This function interacts with the user to collect information about a new truck:
//     * plate number, weight, max carry weight, and special qualifications.
//     * Once all the required information is gathered, it uses the TruckService and DataRepository
//     * interfaces to create and save a new truck object to a database.
//     */
//    public void addNewTruck(TruckService tService){
//        String plateNumber;
//        double truckWeight;
//        double maxWeight;
//        int iModel;
//
//        Scanner scanner = new Scanner(System.in);
//
//        System.out.println("-----Add new truck-----");
//        System.out.println("Enter truck plate number: "); //assuming valid plate number
//        plateNumber = scanner.nextLine();
//
//        try {
//            iModel = truckModelHandler(scanner); // Handler for getting truck model information
//        } catch (UiException e) {
//            System.out.println(e.getMessage());
//            return;
//        }
//
//        System.out.println("Enter truck weight: "); //assuming valid truck weight
//        truckWeight = scanner.nextDouble();
//        scanner.nextLine();
//
//        System.out.println("Enter truck max carry weight: "); //assuming valid max carry weight
//        maxWeight = scanner.nextDouble();
//        scanner.nextLine();
//
//        String ans;
//        int[] iQArr = new int[0];
//        while (true){
//            System.out.println("Does this truck have special qualifications? (Y/N)");
//            ans = scanner.nextLine().toUpperCase();
//            switch (ans.charAt(0)) {
//                case 'Y':
//                    try {
//                        iQArr = truckQualificationsHandler(scanner); // Handler for getting truck qualifications
//                    } catch (UiException e) {
//                        System.out.println(e.getMessage());
//                        return;
//                    }
//                    break;
//                case 'N':
//                    System.out.println("No special qualifications");
//                    break;
//                default:
//                    System.out.println("Invalid input.");
//                    continue;
//            }
//            break;
//        }
//        Truck newTruck = tService.createTruck(plateNumber, iModel, iQArr, truckWeight, maxWeight);
//        // TODO figure out the correct way of doing this v.
//        tService.getDataRepo().saveTruck(newTruck);
//        scanner.close();
//        System.out.println("Truck added successfully!");
//        //TODO also print the truck info ?
//    }



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

//    public int truckModelHandler(Scanner scanner) throws UiException {
//        System.out.println("Choose truck model: "); //assuming valid model and weight
//        for (TruckModel tm : TruckModel.values()){
//            System.out.printf(tm.ordinal()+1 + ". " + tm);
//        }
//        System.out.println("0. exit");
//        int ModelOrdinal = scanner.nextInt();
//        scanner.nextLine();
//        if (!(0<ModelOrdinal && ModelOrdinal<TruckModel.values().length))
//            throw new UiException("Failed to choose truck model");
//        return ModelOrdinal-1;
//    }
//
//    /**
//     * This function displays a list of available truck qualifications to the user and prompts them to enter a
//     * comma-separated list of qualification numbers. It then validates the input and returns an integer array
//     * containing the selected qualification numbers.
//     * @param scanner the Scanner object used to read input from the console
//     * @return an integer array containing the selected qualification numbers
//     * @throws UiException if there is an error with the input or the selected qualification numbers are invalid
//     */
//    public int[] truckQualificationsHandler(Scanner scanner) throws UiException {
//        System.out.println("Choose truck qualification (comma-separated, e.g. 1,3,5): ");
//        for (Qualification ql : Qualification.values()){
//            System.out.println(ql.ordinal()+1 + ". " + ql);
//        }
//        String sQuali = scanner.nextLine();
//        String[] sQualiArr = sQuali.split(",");
//        for (String s : sQualiArr) {
//            try {
//                int num = Integer.parseInt(s.trim());
//                if (num < 1 || num > Qualification.values().length) {
//                    throw new UiException("Invalid qualification number: " + num);
//                }
//            } catch (NumberFormatException e) {
//                throw new UiException("Invalid input: " + s.trim() + " is not a valid number");
//            }
//        }
//        return toIntArrHelper(sQualiArr);
//    }
//
//    /**
//     * This function takes a string array and returns an integer array with the values minus 1 of the original array,
//     * but with each string converted to an integer. The input array should contain only valid integer strings.
//     * @param sArr the string array to be converted
//     * @return an integer array with values minus 1 of the input string array
//     */
//    public int[] toIntArrHelper(String[] sArr){
//        int[] intArr = new int[sArr.length];
//        for (int i = 0; i < sArr.length; i++) {
//            intArr[i] = Integer.parseInt(sArr[i])-1;
//        }
//        return intArr;
//    }
}


