package ControllerServiceLayer;

import DataAccessLayer.OrderDocumentRepository;
import DomainLayer.OrderDocument;
import DomainLayer.Product;

import java.util.Map;
import java.util.Set;

public interface OrderDocumentService {
    OrderDocument createOrderDoc(int sourceId, int destinationId);
    OrderDocumentRepository getOrderDocRepo();
    ProductService getProductService();
    Set<OrderDocument> getOrderDocumentsSet();
    OrderDocument findOrderDocById(int orderId);
    SupplierService getSupplierService();
    StoreService getStoreService();
    void updateWeight(OrderDocument orderDocument, double weight);
    void updateProductList(OrderDocument orderDocument, Map<Product, Double> productsList);
    void showAllProductsInDoc(int orderId);
    void updateAmount(int orderId, String productName, double amount);
    void removeProduct(int orderDocumentId, String productName);
    boolean showOrderDocById(int orderId);
    boolean orderDocumentChooser(int orderId);
    void moveOrderToFinished(OrderDocument completedOrder);
    void showCompletedOrderDocs();
    void showPendingOrderDocs();
}
