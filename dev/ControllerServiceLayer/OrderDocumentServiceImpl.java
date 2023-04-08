package ControllerServiceLayer;

import DataAccessLayer.OrderDocumentRepository;
import DomainLayer.OrderDocument;
import DomainLayer.Store;
import DomainLayer.Supplier;

import java.util.Map;
import java.util.Set;

public class OrderDocumentServiceImpl implements OrderDocumentService {
    private final OrderDocumentRepository orderDocRepo;
    private final SupplierService supplierService;
    private final StoreService storeService;
    public OrderDocumentServiceImpl(OrderDocumentRepository orderDocRepo,
                                    SupplierService supplierService, StoreService storeService) {
        this.orderDocRepo = orderDocRepo;
        this.supplierService = supplierService;
        this.storeService = storeService;
    }

    public OrderDocumentRepository getOrderDocRepo() { return orderDocRepo;}

    @Override
    public void updateWeight(OrderDocument orderDocument, double weight) {
        orderDocument.setWeight(weight);
    }

    @Override
    public void updateProductList(OrderDocument orderDocument, Map<String, Double> productsList) {
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

    public void AddProductsToOrder(int orderId, Map<String,Double> productList)
    {

    } //needed?
    //get order document
    //
    @Override
    public Set<OrderDocument> getOrderDocumentsSet() {
        return orderDocRepo.getOrderDocsSet();
    }


    //TODO - i don't like that the print is happening here
    @Override
    public void showAllProductsInDoc(int orderId) {
        OrderDocument orderDoc = findOrderDocById(orderId);
        Map<String, Double> productsList = orderDoc.getProductsList();
        for (Map.Entry<String, Double> entry : productsList.entrySet()){
            String productName = entry.getKey();
            Double amount = entry.getValue();
            System.out.println("Product Name: "+ productName + " amount: " +amount);
        }
    }

    @Override
    public void updateAmount(int orderId, String productName, double amount) {
        OrderDocument orderDocument = this.orderDocRepo.findOrderDocById(orderId);
        orderDocument.getProductsList().replace(productName,amount);
    }

    @Override
    public void removeProduct(int orderDocumentId, String productName) {
        OrderDocument orderDocument = this.orderDocRepo.findOrderDocById(orderDocumentId);
        orderDocument.getProductsList().remove(productName);
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
}