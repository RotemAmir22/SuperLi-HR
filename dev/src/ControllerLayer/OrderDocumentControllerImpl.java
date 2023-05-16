package ControllerLayer;

import BussinesLogic.BranchStore;
import BussinesLogic.TransitCoordinator;
import DataAccessLayer.OrderDocumentDAO;
import DomainLayer.*;

import java.util.*;

public class OrderDocumentControllerImpl implements OrderDocumentController {
    private final OrderDocumentDAO orderDocumentDAO;
    private final SupplierController supplierController;
    private final TransitCoordinator transitCoordinator;
    private final ProductController productController;


    public OrderDocumentControllerImpl(OrderDocumentDAO orderDocDAO,
                                       SupplierController supplierController, TransitCoordinator transitCoordinator,
                                       ProductController productController) {
        this.orderDocumentDAO = orderDocDAO;
        this.supplierController = supplierController;
        this.transitCoordinator = transitCoordinator;
        this.productController = productController;
    }
    public TransitCoordinator getTransitCoordinator() {
        return transitCoordinator;
    }
    @Override
    public OrderDocument createOrderDoc(int sourceId, int destinationId) {
        Supplier supplier = supplierController.findSupplierById(sourceId) ;
        //TODO find storeById with BranchStore;
        BranchStore branchStore = transitCoordinator.findStoreById(destinationId) ;
        if (branchStore == null || supplier == null) return null;
        OrderDocument orderDoc = new OrderDocument(supplier, branchStore);
        orderDocumentDAO.saveOrderDocument(orderDoc);
        return orderDoc;

    }
    @Override
    public void showAllProductsInDoc(int orderId) {
        OrderDocument orderDoc = findOrderDocById(orderId);
        Map<Product, Double> productsList = orderDoc.getProductsList();
        for (Map.Entry<Product, Double> entry : productsList.entrySet()){
            String productName = entry.getKey().getProductName();
            Double amount = entry.getValue();
            System.out.println("Product Name: "+ productName + " amount: " +amount);
        }
    }
    // TODO think about DB approach

    @Override
    public void updateWeight(OrderDocument orderDocument, double weight) {
        orderDocument.setWeight(weight);
    }
    // TODO think about DB approach
    @Override
    public void updateAmount(int orderId, String productName, double amount) {
        Product product = productController.findProductByName(productName);
        OrderDocument orderDocument = this.orderDocumentDAO.findOrderDocumentById(orderId);
        orderDocument.getProductsList().replace(product,amount);
    }
    // TODO think about DB approach
    @Override
    public void removeProductFromOrderDoc(int orderDocumentId, String productName) {
        Product productToRemove = productController.findProductByName(productName);
        if(productToRemove == null) return;
        OrderDocument orderDocument = this.orderDocumentDAO.findOrderDocumentById(orderDocumentId);
        if(orderDocument == null) return;
        orderDocument.removeProductFromOrder(productToRemove);
    }
    @Override
    public OrderDocument findOrderDocById(int orderId) {
        return orderDocumentDAO.findOrderDocumentById(orderId);
    }
    public boolean orderDocumentChooser(int orderId){
        return orderDocumentDAO.findOrderDocumentById(orderId) != null;
    }
    public void moveOrderToFinish(OrderDocument completedOrder){
        orderDocumentDAO.moveToCompleted(completedOrder);
    }
    @Override
    public void showCompletedOrderDocs(){
        System.out.println("-----Completed Orders-----");
        Set<OrderDocument> completedOrders = orderDocumentDAO.getOrderDocsSet(true);
        for (OrderDocument orderDocument : completedOrders){
            orderDocument.printOrder();
        }
    }
    public void showPendingOrderDocs() {
        System.out.println("-----Pending Orders-----");
        Set<OrderDocument> pendingOrders = orderDocumentDAO.getOrderDocsSet(false);
        List<OrderDocument> sortedOrders = new ArrayList<>(pendingOrders);
        Comparator<OrderDocument> bySupplierArea = Comparator.comparing(
                order -> order.getSource().getAreaCode().name());
        sortedOrders.sort(bySupplierArea);

        for (OrderDocument orderDocument : sortedOrders) {
            System.out.println("Area: "+ orderDocument.getSource().getSupplierArea());
            orderDocument.printOrder();
        }
    }

}