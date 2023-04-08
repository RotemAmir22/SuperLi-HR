package ControllerServiceLayer;

import DataAccessLayer.OrderDocumentRepository;
import DomainLayer.OrderDocument;

import java.util.Map;
import java.util.Set;

public interface OrderDocumentService {
    public OrderDocument createOrderDoc(int sourceId, int destinationId);

    public OrderDocumentRepository getOrderDocRepo();

    public void updateWeight(OrderDocument orderDocument,double weight);

    public void updateProductList(OrderDocument orderDocument,Map<String, Double> productsList);

    Set<OrderDocument> getOrderDocumentsSet();

    public void showAllProductsInDoc(int orderId);
    public void updateAmount(int orderId,String productName, double amount);

    void removeProduct(int orderDocumentId, String productName);

    OrderDocument findOrderDocById(int orderId);

    boolean showOrderDocById(int orderId);

    public boolean orderDocumentChooser(int orderId);
    }
