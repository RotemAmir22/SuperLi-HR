package ControllerLayer;

import DataAccessLayer.OrderDocumentDAO;
import DomainLayer.*;

import java.util.*;

public class OrderDocumentControllerImpl implements OrderDocumentController {
    private final OrderDocumentDAO orderDocumentDAO;
    private final SupplierController supplierController;
    private final StoreController storeController;
    private final ProductController productController;


    public OrderDocumentControllerImpl(OrderDocumentDAO orderDocDAO,
                                       SupplierController supplierController, StoreController storeController,
                                       ProductController productController) {
        this.orderDocumentDAO = orderDocDAO;
        this.supplierController = supplierController;
        this.storeController = storeController;
        this.productController = productController;
    }
    public ProductController getProductController() {
        return productController;
    }
    public SupplierController getSupplierController() {
        return supplierController;
    }
    public StoreController getStoreController() {
        return storeController;
    }
    public OrderDocumentDAO getOrderDocumentDAO() { return orderDocumentDAO;}
    @Override
    public void updateWeight(OrderDocument orderDocument, double weight) {
        orderDocument.setWeight(weight);
    }
    @Override
    public OrderDocument createOrderDoc(int sourceId, int destinationId) {
        Supplier supplier = supplierController.findSupplierById(sourceId) ;
        Store store = storeController.findStoreById(destinationId) ;
        if (store == null || supplier == null) return null;
        OrderDocument orderDoc = new OrderDocument(supplier,store);
        return orderDoc;
    }
    @Override
    public Set<OrderDocument> getOrderDocumentsSet() {
        return orderDocumentDAO.getOrderDocsSet();
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
    @Override
    public void updateAmount(int orderId, String productName, double amount) {
        Product product = productController.findProductByName(productName);
        OrderDocument orderDocument = this.orderDocumentDAO.findOrderDocById(orderId);
        orderDocument.getProductsList().replace(product,amount);
    }
    @Override
    public void removeProduct(int orderDocumentId, String productName) {
        Product productToRemove = productController.findProductByName(productName);
        if(productToRemove == null) return;
        OrderDocument orderDocument = this.orderDocumentDAO.findOrderDocById(orderDocumentId);
        if(orderDocument == null) return;
        orderDocument.removeProductFromOrder(productToRemove);
    }
    @Override
    public OrderDocument findOrderDocById(int orderId) {
        return this.orderDocumentDAO.findOrderDocById(orderId);
    }
    public boolean orderDocumentChooser(int orderId){
        return orderDocumentDAO.findOrderDocById(orderId) != null;
    }
    public void moveOrderToFinish(OrderDocument completedOrder){
        this.orderDocumentDAO.moveToCompleted(completedOrder);
    }
    @Override
    public void showCompletedOrderDocs(){
        System.out.println("-----Completed Orders-----");
        Set<OrderDocument> completedOrders = this.orderDocumentDAO.getCompletedOrdersSet();
        for (OrderDocument orderDocument : completedOrders){
            orderDocument.printOrder();
        }
    }
    public void showPendingOrderDocs() {
        System.out.println("-----Pending Orders-----");
        Set<OrderDocument> pendingOrders = this.orderDocumentDAO.getOrderDocsSet();
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