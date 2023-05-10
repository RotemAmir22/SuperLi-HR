package ControllerLayer;

import BussinesLogic.TransitCoordinator;
import DataAccessLayer.OrderDocumentDAO;
import DomainLayer.OrderDocument;

import java.util.Set;

public interface OrderDocumentController {
    OrderDocument createOrderDoc(int sourceId, int destinationId);
    OrderDocumentDAO getOrderDocumentDAO();
    ProductController getProductController();
    Set<OrderDocument> getOrderDocumentsSet();
    OrderDocument findOrderDocById(int orderId);
    SupplierController getSupplierController();
    TransitCoordinator getTransitCoordinator();
    void updateWeight(OrderDocument orderDocument, double weight);
    void showAllProductsInDoc(int orderId);
    void updateAmount(int orderId, String productName, double amount);
    void removeProduct(int orderDocumentId, String productName);
    boolean orderDocumentChooser(int orderId);
    void moveOrderToFinish(OrderDocument completedOrder);
    void showCompletedOrderDocs();
    void showPendingOrderDocs();
}
