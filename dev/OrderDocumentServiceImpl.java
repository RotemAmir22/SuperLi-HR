import java.util.Map;
import java.util.Set;

public class OrderDocumentServiceImpl implements OrderDocumentService {
    private final OrderDocumentRepository orderdocRepo;

    public OrderDocumentServiceImpl(OrderDocumentRepository orderdocRepo) {
        this.orderdocRepo = orderdocRepo;
    }

    public OrderDocumentRepository getOrderdocRepo() { return orderdocRepo;}

    @Override
    public OrderDocument createOrderDoc(int sourceId, int destinationId) {
        Store store = ;
        Supplier supplier = ;

        if (store ==null || supplier ==null){return null;}

        OrderDocument orderDoc = new OrderDocument(supplier,store);
        //save in repos what has returned to the controller
        return orderDoc;
    }

    public void AddProductsToOrder(int orderId, Map<String,Double> productList)
    {

    } //needed?
    //get order document
    //
    @Override
    public Set<OrderDocument> getAllOrderDocuments() {
        return orderdocRepo.findAllOrderDocs();
    }

    @Override
    public OrderDocumentRepository getOrderDocRepo() {
        return this.orderdocRepo;
    }

}
