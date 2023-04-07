package ControllerServiceLayer;

import DataAccessLayer.OrderDocumentRepository;
import DomainLayer.OrderDocument;

import java.util.Set;

public interface OrderDocumentService {
    public OrderDocument createOrderDoc(int sourceId, int destinationId);

    public Set<OrderDocument> getAllOrderDocuments();


    public OrderDocumentRepository getOrderDocRepo();
}
