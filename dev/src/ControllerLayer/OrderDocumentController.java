package ControllerLayer;

import DomainLayer.OrderDocument;


public interface OrderDocumentController {
    OrderDocument createOrderDoc(int sourceId, int destinationId);
    void updateWeight(OrderDocument orderDocument, double weight);
    void addProductToOrderDoc(int orderDocumentId, int productId, double productAmount);
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
