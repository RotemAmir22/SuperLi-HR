package ControllerServiceLayer;

import DomainLayer.OrderDocument;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

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

    }

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
            this.productService.showAllProducts();
            String input = scanner.nextLine(); //assuming valid input

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


}
