package ControllerServiceLayer;

import DomainLayer.OrderDocument;

import java.util.Scanner;

public class OrderDocumentController {
    private final OrderDocumentService orderDocService;

    public OrderDocumentController(OrderDocumentService orderDocService) { this.orderDocService = orderDocService;}

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


}
