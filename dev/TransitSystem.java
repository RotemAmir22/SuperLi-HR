import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class TransitSystem {
    Set<Truck> trucksSet;
    Set<Supplier> suppliersSet;
    Set<Store> storeSet;
    Set<Transit> transitSet;
    Set<Driver> driverSet;
    Set<OrderDocument> orderSet;
    Set<Product> productsSet;

    public void createTruck(){
        String plateNumber;
        TruckModel model;
        Set<Qualification> qSet;
        double truckWeight;
        double maxWeight;
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
        //String sModel = scanner.nextLine();

    }
    public void createOrderDoc(){}; //switchCase
    public void addProductToOrder(){};
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


