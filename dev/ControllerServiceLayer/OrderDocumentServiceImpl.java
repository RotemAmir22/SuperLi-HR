package ControllerServiceLayer;

import DataAccessLayer.OrderDocumentRepository;
import DomainLayer.*;

import java.util.*;

public class OrderDocumentServiceImpl implements OrderDocumentService {
    private final OrderDocumentRepository orderDocRepo;
    private final SupplierService supplierService;
    private final StoreService storeService;
    private final ProductService productService;
    public OrderDocumentServiceImpl(OrderDocumentRepository orderDocRepo,
                                    SupplierService supplierService, StoreService storeService,
                                    ProductService productService) {
        this.orderDocRepo = orderDocRepo;
        this.supplierService = supplierService;
        this.storeService = storeService;
        this.productService = productService;
    }
    public ProductService getProductService() {
        return productService;
    }
    public SupplierService getSupplierService() {
        return supplierService;
    }
    public StoreService getStoreService() {
        return storeService;
    }
    public OrderDocumentRepository getOrderDocRepo() { return orderDocRepo;}
    @Override
    public void updateWeight(OrderDocument orderDocument, double weight) {
        orderDocument.setWeight(weight);
    }
    @Override
    //update and not drive through
    public void updateProductList(OrderDocument orderDocument, Map<Product, Double> productsList) {
        orderDocument.setProductsList(productsList);
    }
    @Override
    public OrderDocument createOrderDoc(int sourceId, int destinationId) {
        Supplier supplier = supplierService.findSupplierById(sourceId) ;
        Store store = storeService.findStoreById(destinationId) ;
        if (store == null || supplier == null) return null;
        OrderDocument orderDoc = new OrderDocument(supplier,store);
        return orderDoc;
    }
    @Override
    public Set<OrderDocument> getOrderDocumentsSet() {
        return orderDocRepo.getOrderDocsSet();
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
        Product product = productService.findProductByName(productName);
        OrderDocument orderDocument = this.orderDocRepo.findOrderDocById(orderId);
        orderDocument.getProductsList().replace(product,amount);
    }

    @Override
    public void removeProduct(int orderDocumentId, String productName) {
        Product productToRemove = productService.findProductByName(productName);
        if(productToRemove == null) return;
        OrderDocument orderDocument = this.orderDocRepo.findOrderDocById(orderDocumentId);
        if(orderDocument == null) return;
        orderDocument.removeProductFromOrder(productToRemove);
    }

    @Override
    public OrderDocument findOrderDocById(int orderId) {
        return this.orderDocRepo.findOrderDocById(orderId);
    }

    @Override
    public boolean showOrderDocById(int orderId) {
        OrderDocument orderDocument = orderDocRepo.findOrderDocById(orderId);
        if (orderDocument == null) return false;
        orderDocument.printOrder();
        return true;
    }
    public boolean orderDocumentChooser(int orderId){
        if(orderDocRepo.findOrderDocById(orderId)==null) {return false;}
        return true;
    }

    public void moveOrderToFinished(OrderDocument completedOrder){
        this.orderDocRepo.removeOrderDoc(completedOrder);
        this.orderDocRepo.saveToCompleted(completedOrder);
    }
    @Override
    public void showCompletedOrderDocs(){
        System.out.println("-----Completed Orders-----");
        Set<OrderDocument> completedOrders = this.orderDocRepo.getCompletedOrdersSet();
        for (OrderDocument orderDocument : completedOrders){
            orderDocument.printOrder();
        }
    }

    public void showPendingOrderDocs() {
        System.out.println("-----Pending Orders-----");
        Set<OrderDocument> pendingOrders = this.orderDocRepo.getOrderDocsSet();

        List<OrderDocument> sortedOrders = new ArrayList<>(pendingOrders);
        Comparator<OrderDocument> bySupplierArea = Comparator.comparing(
                order -> order.getSource().getAreaCode().name());
        Collections.sort(sortedOrders, bySupplierArea);

        for (OrderDocument orderDocument : sortedOrders) {
            System.out.println("Area: "+ orderDocument.getSource().getSupplierArea());
            orderDocument.printOrder();
        }
    }
}