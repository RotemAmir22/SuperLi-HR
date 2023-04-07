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
    public OrderDocumentServiceImpl(OrderDocumentRepository orderDocRepo, SupplierService supplierService,
                                    StoreService storeService) {
        this.orderDocRepo = orderDocRepo;
        this.supplierService = supplierService;
        this.storeService = storeService;
    }

    public OrderDocumentRepository getOrderDocRepo() { return orderDocRepo;}

    @Override
    public OrderDocument createOrderDoc(int sourceId, int destinationId) {
        Supplier supplier = supplierService.findSupplierById(sourceId) ;
        Store store = storeService.findStoreById(destinationId) ;
        if (store ==null || supplier ==null){return null;}
        OrderDocument orderDoc = new OrderDocument(supplier,store);
        return orderDoc;
    }

    public void AddProductsToOrder(int orderId, Map<String,Double> productList)
    {

    } //needed?
    //get order document
    //
    @Override
    public Set<OrderDocument> getAllOrderDocuments() {
        return orderDocRepo.getOrderDocsSet();
    }

}
