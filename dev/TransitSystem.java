<<<<<<< Updated upstream
import java.util.*;
=======
import java.util.Scanner;
import java.util.Set;
>>>>>>> Stashed changes


/**
 * UI
 */
public class TransitSystem {


    public void addNewTruck(){
        String plateNumber;
        TruckModel model;
        double truckWeight;
        double maxWeight;
        Set<Qualification> qSet;
        Scanner scanner = new Scanner(System.in);

        System.out.println("-----Create new Truck-----");
        System.out.println("Enter truck plate number: ");
        plateNumber = scanner.nextLine();

        System.out.println("Enter truck model: "); //valid model and weight
        String sModel = scanner.nextLine();

        System.out.println("Enter truck weight: ");
        truckWeight = scanner.nextDouble();

        System.out.println("Enter truck max carry weight: ");
        maxWeight = scanner.nextDouble();

        System.out.println("Enter truck qualification: ");
        String sQuali = scanner.nextLine();

    }
    /**
     * responsible on creating new order document
     * at first it won't have any products in it,
     * only the destination and its source
     **/
    public void createOrderDoc(){
        Scanner scanner = new Scanner(System.in);
        int supplierId;
        int storeId;

        System.out.println("-----Create new Order-----");
        System.out.println("Enter source (supplier id) of the order: ");
        supplierId = scanner.nextInt();

        //some function

        System.out.println("Enter destination (store id)  of the order: ");
        storeId = scanner.nextInt();

        System.out.println("this order id is: "+ OrderDocument.documentNextId);

        // function to call the constructor of order, and will generate new order number

        } //switchCase ??

    /**
    * responsible on adding products to an existing orderdocument
     * @param orderDoc that the user inputs to know which
     * order needs the products
     **/
    public void addProductToOrder(OrderDocument orderDoc) {
        Scanner scanner = new Scanner(System.in);
        Map<String, Double> productsList = new HashMap<>();
        double weight = 0;

        System.out.println("-----Create new Order-----");
        System.out.println("Enter source (supplier) of the order: ");
        //some function
        System.out.println("Enter destination (store)  of the order: ");
        //some function to get the pointer to the source and destination
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


