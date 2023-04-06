import java.util.Set;

public interface OrderDocumentService {
    public OrderDocument createOrderDoc(int sourceId, int destinationId);

    public Set<OrderDocument> getAllOrderDocuments();


}
