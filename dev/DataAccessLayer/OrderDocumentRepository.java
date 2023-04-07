package DataAccessLayer;

import DomainLayer.OrderDocument;

import java.util.Set;

public interface OrderDocumentRepository {
    void saveOrderDocument(OrderDocument orderDocument);
    Set<OrderDocument> findAllOrderDocs();
    OrderDocument findOrderDocById(int OrderDocId);
}
