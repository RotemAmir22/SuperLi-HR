package UiLayer;

import ControllerLayer.OrderDocumentController;
import ControllerLayer.ProductController;
import DomainLayer.OrderDocument;
import DomainLayer.Product;

import java.util.Scanner;

public class OrderDocumentPresentation {
    private final OrderDocumentController orderDocumentController;
    private final ProductController productController;


    public OrderDocumentPresentation(OrderDocumentController orderDocumentController, ProductController productController) {
        this.orderDocumentController = orderDocumentController;
        this.productController = productController;
    }
    /**
     * This function interacts with the user to collect information about a new orderdocs:
     * Once all the required information is gathered, it uses the OrderService and OrderRepository
     * interfaces to create and save a new Order object to a database.
     */
    public void createNewOrderDocument(Scanner scanner){
        int storeId;
        int supplierId;

        System.out.println("-----Create new Order-----");
        System.out.println("Enter source (supplier id) of the order: "); // assuming valid supplier id
        supplierId = scanner.nextInt();
        scanner.nextLine();
        if(orderDocumentController.getSupplierController().findSupplierById(supplierId) == null)
        {
            System.out.println("that is not an existing id of a supplier! ");
            return;
        }
        System.out.println("Enter destination (store id)  of the order: "); //assuming valid store id
        storeId = scanner.nextInt();
        scanner.nextLine();
        if(orderDocumentController.getStoreController().findStoreById(storeId) == null)
        {
            System.out.println("that is not an existing id of a store! ");
            return;
        }

        //TODO if we want this last, need to change the constructor
        System.out.println("This order id is: "+ OrderDocument.documentNextId);


        OrderDocument newOrderDoc = this.orderDocumentController.createOrderDoc(supplierId,storeId);
        //TODO figure out the correct way of doing this v.
        this.orderDocumentController.getOrderDocumentDAO().saveOrderDocument(newOrderDoc);
        //TODO also print the docs info ?
        addProductToOrder(scanner);
    }
    /**
     * responsible on adding products to an existing orderDocument
     **/
    public void addProductToOrder(Scanner scanner) {
        int orderDocId;
        orderDocId = orderDocChoice(scanner);
        if (orderDocId ==-1) // order id not found
        {
            System.out.println("The order id does not exist! ");
            return;
        }
        OrderDocument orderDocument = this.orderDocumentController.getOrderDocumentDAO().findOrderDocById(orderDocId);

        double weight = 0;
        boolean flag = false;
        while (!flag) {
            System.out.println("Please choose which products you want from the supplier: ");
            //function that prints all products instead of what there is now
            this.productController.showAllProducts();
            String input = scanner.nextLine(); //assuming valid input
            Product newProduct = orderDocumentController.getProductController().findProductByName(input);

            if(newProduct != null) //product exist
            {
                System.out.println("Please choose what amount of the product you would like: ");
                Double inAmount = scanner.nextDouble();
                scanner.nextLine();
                weight += inAmount;
                orderDocument.getProductsList().put(newProduct, inAmount);
                System.out.println("Would you like to add more products? ");
                System.out.println(("Y/N "));
                input = scanner.nextLine();

                if (input.equalsIgnoreCase("N")) {
                    flag = true;
                }
            }
            else { System.out.println("Not a valid product name! ");}
        }
        orderDocumentController.updateWeight(orderDocument,weight);
    }
    public void updateProductAmount(Scanner scanner){
        int orderDocumentId;
        orderDocumentId = orderDocChoice(scanner);
        if (orderDocumentId ==-1)
        {
            System.out.println("The order id does not exist! ");
            return;
        }
        this.orderDocumentController.showAllProductsInDoc(orderDocumentId);
        System.out.println("Please enter which product you would like to change the Amount: ");
        String productName = scanner.nextLine();

        System.out.println("Please enter the new amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        OrderDocument orderDoc= this.orderDocumentController.getOrderDocumentDAO().findOrderDocById(orderDocumentId);
        this.orderDocumentController.updateAmount(orderDocumentId,productName,amount);
        System.out.println("Amount has changed to: "+ amount);
    }
    public void removeProductFromOrder(Scanner scanner){
        int orderDocumentId;
        orderDocumentId = orderDocChoice(scanner);
        if (orderDocumentId ==-1)
        {
            System.out.println("The order id does not exist! ");
            return;
        }
        this.orderDocumentController.showAllProductsInDoc(orderDocumentId);
        System.out.println("Please enter which product you would like to remove: ");
        String productName = scanner.nextLine();
        OrderDocument orderDoc= this.orderDocumentController.getOrderDocumentDAO().findOrderDocById(orderDocumentId);
        this.orderDocumentController.removeProduct(orderDocumentId,productName);
        System.out.println("product: "+ productName + " has been removed");
    };
    public void printPendingOrderDocs() {
        this.orderDocumentController.showPendingOrderDocs();
    }
    public void printCompletedOrderDocs(){
        this.orderDocumentController.showCompletedOrderDocs();
    }
    public int orderDocChoice(Scanner scanner)
    {
        int orderId;
        System.out.println("What Order would you like to manage? ");
        orderId = scanner.nextInt();
        if (scanner.hasNextLine()) scanner.nextLine();
        if(orderDocumentController.orderDocumentChooser(orderId)){
            return orderId;}
        return -1;
    }
}
