import java.util.HashMap;
import java.util.List;

public class TransitDocument {
    private int DocumentId;
    private Site Destination;
    private double TotalWeight;
    private List<HashMap<Integer,String>> ProductList;

    public TransitDocument(int documentId, Site destination,
                           double totalWeight, List<HashMap<Integer, String>> productList) {
        DocumentId = documentId;
        Destination = destination;
        TotalWeight = totalWeight;
        ProductList = productList;
    }
}
