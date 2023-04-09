package ControllerServiceLayer;

import DataAccessLayer.OrderDocumentRepository;
import DomainLayer.OrderDocument;
import DomainLayer.Product;

import java.util.Map;
import java.util.Set;

public interface OrderDocumentService {
    public OrderDocument createOrderDoc(int sourceId, int destinationId);

    public OrderDocumentRepository getOrderDocRepo();

    public void updateWeight(OrderDocument orderDocument,double weight);

    public void updateProductList(OrderDocument orderDocument,Map<Product, Double> productsList);

    public ProductService getProductService();

        Set<OrderDocument> getOrderDocumentsSet();

    public void showAllProductsInDoc(int orderId);
    public void updateAmount(int orderId,String productName, double amount);

    void removeProduct(int orderDocumentId, String productName);

    OrderDocument findOrderDocById(int orderId);

    boolean showOrderDocById(int orderId);

    public boolean orderDocumentChooser(int orderId);
    public SupplierService getSupplierService();

    public StoreService getStoreService();
    }
