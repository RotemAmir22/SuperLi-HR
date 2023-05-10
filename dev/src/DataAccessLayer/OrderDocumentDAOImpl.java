package DataAccessLayer;

import DomainLayer.OrderDocument;

import java.util.HashSet;
import java.util.Set;

public class OrderDocumentDAOImpl implements OrderDocumentDAO {
    private final Set<OrderDocument> ordersDocumentsSet = new HashSet<>();
    private final Set<OrderDocument> completedOrdersDocumentsSet = new HashSet<>();


    @Override
    public void saveOrderDocument(OrderDocument orderDocument) {
        ordersDocumentsSet.add(orderDocument);
    }
    @Override
    public void removeOrderDoc(OrderDocument orderDocument) {
        ordersDocumentsSet.remove(orderDocument);
    }
    @Override
    public Set<OrderDocument> getOrderDocsSet() {
        return ordersDocumentsSet;
    }
    @Override
    public Set<OrderDocument> getCompletedOrdersSet() {
        return completedOrdersDocumentsSet;
    }
    @Override
    public OrderDocument findOrderDocById(int orderDocId) {
        for (OrderDocument orderDocument : ordersDocumentsSet) {
            if (orderDocument.getDocumentId()==orderDocId) {
                return orderDocument;
            }
        }
        return null; // Orderdocs with specified id not found
    }
    @Override
    public void moveToCompleted(OrderDocument completedOrder) {
        this.ordersDocumentsSet.remove(completedOrder);
        this.completedOrdersDocumentsSet.add(completedOrder);
    }

}
