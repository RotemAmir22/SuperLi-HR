package DataAccessLayer;

import DomainLayer.OrderDocument;

import java.util.Set;

public interface OrderDocumentDAO {
    Set<OrderDocument> getOrderDocsSet();
    Set<OrderDocument> getCompletedOrdersSet();
    OrderDocument findOrderDocById(int OrderDocId);
    void saveOrderDocument(OrderDocument orderDocument);
    void removeOrderDoc(OrderDocument orderDocument);
    void moveToCompleted(OrderDocument completedOrder);

}