package ControllerServiceLayer;

import DataAccessLayer.OrderDocumentRepository;
import DomainLayer.OrderDocument;

import java.util.Map;
import java.util.Set;

public interface OrderDocumentService {
     OrderDocument createOrderDoc(int sourceId, int destinationId);

     Set<OrderDocument> getOrderDocumentsSet();

     OrderDocumentRepository getOrderDocRepo();

     void updateWeight(OrderDocument orderDocument,double weight);

     void updateProductList(OrderDocument orderDocument,Map<String, Double> productsList);

     // TODO why this split prints ??
     void showAllProductsInDoc(int orderId);
     void updateAmount(int orderId,String productName, double amount);
    void removeProduct(int orderDocumentId, String productName);
    OrderDocument findOrderDocById(int orderId);
    boolean showOrderDocById(int orderId);
}
