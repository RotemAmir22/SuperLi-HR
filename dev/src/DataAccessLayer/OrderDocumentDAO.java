package DataAccessLayer;

import DomainLayer.OrderDocument;

import java.util.Set;

public interface OrderDocumentDAO {

    void saveOrderDocument(OrderDocument orderDocument);

    OrderDocument findOrderDocById(int OrderDocId);

    void moveToCompleted(OrderDocument completedOrder);

    Set<OrderDocument> getOrderDocsSet(boolean isCompleted);
}