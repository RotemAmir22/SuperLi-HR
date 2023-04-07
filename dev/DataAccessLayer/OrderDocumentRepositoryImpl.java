package DataAccessLayer;

import DomainLayer.OrderDocument;

import java.util.HashSet;
import java.util.Set;

public class OrderDocumentRepositoryImpl implements OrderDocumentRepository{
    private final Set<OrderDocument> orderDocuments = new HashSet<>();
    @Override
    public void saveOrderDocument(OrderDocument orderDocument) {
        orderDocuments.add(orderDocument);
    }


    @Override
    public Set<OrderDocument> findAllOrderDocs() {
        return orderDocuments;
    }

    @Override
    public OrderDocument findOrderDocById(int orderDocId) {
        for (OrderDocument orderDocument : orderDocuments) {
            if (orderDocument.getDocumentId()==orderDocId) {
                return orderDocument;
            }
        }
        return null; // Orderdocs with specified id not found
    }
}
