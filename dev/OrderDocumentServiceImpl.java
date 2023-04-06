import java.util.Map;
import java.util.Set;

public class OrderDocumentServiceImpl implements OrderDocumentService {
    static DataRepository dataRep = new DataRepositoryImpl();

    @Override
    public OrderDocument createOrderDoc(int sourceId, int destinationId) {
        Store store = searchStore(destinationId);
        Supplier supplier = searchSupllier(sourceId);

        if (store ==null || supplier ==null){return null;}

        OrderDocument orderDoc = new OrderDocument(supplier,store);
        return orderDoc;
    }

    public void AddProductsToOrder(int orderId, Map<String,Double> productList){} //needed?

    @Override
    public Set<OrderDocument> getAllOrderDocuments() {
        return dataRep.findAllOrdersDocs();
    }

    /**
     *
     * @param supplierId
     * search function for specific suppliers and stores
     * not sure if suppose to be here !!!
     * @return supplier
     */
    public Supplier searchSupllier(int supplierId) {
        for (Supplier supplier : dataRep.findAllSuppliers()) {
            if (supplier.getSupplierID() == supplierId) {
                return supplier;
            }
        }
        return null;
    }
    public Store searchStore(int storeId) {
        for (Store store : dataRep.findAllStores()) {
            if (store.getStoreId() == storeId) {
                return store;
            }
        }
        return null;
    }
}
