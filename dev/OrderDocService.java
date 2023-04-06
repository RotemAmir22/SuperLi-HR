import java.util.List;

public interface OrderDocService {
    public OrderDocument createOrderDoc(int sourceID, int destinationID, double totalWeight);
    public List<Truck> getAllOrdersDocs();

}
