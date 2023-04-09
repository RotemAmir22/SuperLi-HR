package ControllerServiceLayer;

import DomainLayer.OrderDocument;
import DomainLayer.Product;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class OrderDocumentController {
    private final OrderDocumentService orderDocService;
    private final ProductService productService;

    public OrderDocumentController(OrderDocumentService orderDocService, ProductService productService) {
        this.orderDocService = orderDocService;
        this.productService = productService;
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

        System.out.println("Enter destination (store id)  of the order: "); //assuming valid store id
        storeId = scanner.nextInt();
        scanner.nextLine();

        //TODO if we want this last, need to change the constructor
        System.out.println("This order id is: "+ OrderDocument.documentNextId);


        OrderDocument newOrderDoc = this.orderDocService.createOrderDoc(supplierId,storeId);
        //TODO figure out the correct way of doing this v.
        this.orderDocService.getOrderDocRepo().saveOrderDocument(newOrderDoc);
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
        OrderDocument orderDocument = this.orderDocService.getOrderDocRepo().findOrderDocById(orderDocId);

        double weight = 0;
        boolean flag = false;
        while (!flag) {
            System.out.println("Please choose which products you want from the supplier: ");
            //function that prints all products instead of what there is now
            this.productService.showAllProducts();
            String input = scanner.nextLine(); //assuming valid input
            Product newProduct = orderDocService.getProductService().findProductByName(input);

            if(newProduct != null); //product exist
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
            System.out.println("Not a valid product name! ");
            }
        }
        orderDocService.updateWeight(orderDocument,weight);
    }
    public void updateProductAmount(Scanner scanner){
        int orderDocumentId;
        orderDocumentId = orderDocChoice(scanner);
        if (orderDocumentId ==-1)
        {
            System.out.println("The order id does not exist! ");
            return;
        }
        this.orderDocService.showAllProductsInDoc(orderDocumentId);
        System.out.println("Please enter which product you would like to change the Amount: ");
        String productName = scanner.nextLine();

        System.out.println("Please enter the new amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        OrderDocument orderDoc= this.orderDocService.getOrderDocRepo().findOrderDocById(orderDocumentId);
        this.orderDocService.updateAmount(orderDocumentId,productName,amount);
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
        this.orderDocService.showAllProductsInDoc(orderDocumentId);
        System.out.println("Please enter which product you would like to remove: ");
        String productName = scanner.nextLine();

        OrderDocument orderDoc= this.orderDocService.getOrderDocRepo().findOrderDocById(orderDocumentId);
        this.orderDocService.removeProduct(orderDocumentId,productName);
        System.out.println("product: "+ productName + " has been removed");
    };

    public void showAllOrderDocs() {
        Set<OrderDocument> allOrders = orderDocService.getOrderDocRepo().getOrderDocsSet();
        for (OrderDocument orderDoc :allOrders){
            showSpecificOrderDoc(orderDoc.getDocumentId());
        }
    }
    public void showSpecificOrderDoc(int orderId){
        OrderDocument orderDoc = orderDocService.getOrderDocRepo().findOrderDocById(orderId);
        orderDoc.printOrderId();
        orderDoc.printOrderSource();
        orderDoc.printOrderDestination();
        orderDoc.printOrderProductList();
    }
    public int orderDocChoice(Scanner scanner)
    {
        int orderId;
        System.out.println("What Order would you like to manage? ");
        orderId = scanner.nextInt();
        if (scanner.hasNextLine()) scanner.nextLine();
        if(orderDocService.orderDocumentChooser(orderId)){
            return orderId;}
        return -1;
    }
}
