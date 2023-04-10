package DataAccessLayer;

import DomainLayer.OrderDocument;

import java.util.HashSet;
import java.util.Set;

public class OrderDocumentRepositoryImpl implements OrderDocumentRepository{
    private final Set<OrderDocument> orderDocuments = new HashSet<>();
    private final Set<OrderDocument> completedOrderDocs = new HashSet<>();
    @Override
    public void saveOrderDocument(OrderDocument orderDocument) {
        orderDocuments.add(orderDocument);
    }
    @Override
    public void removeOrderDoc(OrderDocument orderDocument) {
        orderDocuments.remove(orderDocument);
    }
    @Override
    public Set<OrderDocument> getOrderDocsSet() {
        return orderDocuments;
    }

    @Override
    public Set<OrderDocument> getCompletedOrdersSet() {
        return completedOrderDocs;
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
    @Override
    public void saveToCompleted(OrderDocument completedOrder) {
        this.completedOrderDocs.add(completedOrder);
    }

}
