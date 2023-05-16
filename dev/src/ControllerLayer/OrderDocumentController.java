package ControllerLayer;

import BussinesLogic.TransitCoordinator;
import DataAccessLayer.OrderDocumentDAO;
import DomainLayer.OrderDocument;


public interface OrderDocumentController {
    OrderDocument createOrderDoc(int sourceId, int destinationId);
    TransitCoordinator getTransitCoordinator();
    void updateWeight(OrderDocument orderDocument, double weight);
    void showAllProductsInDoc(int orderId);
    void updateAmount(int orderId, String productName, double amount);
    void removeProductFromOrderDoc(int orderDocumentId, String productName);
    boolean orderDocumentChooser(int orderId);
    void moveOrderToFinish(OrderDocument completedOrder);
    void showCompletedOrderDocs();
    void showPendingOrderDocs();
    OrderDocument findOrderDocById(int orderId);

    //    Set<OrderDocument> getOrderDocumentsSet();
}
