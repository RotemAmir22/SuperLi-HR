import java.util.HashMap;
import java.util.Set;

public class OrderDocument {
    private int documentId;
    private Site source;
    private Site destination;
    private double totalWeight; //detailed weight ??
    private Set<HashMap<Product, Double>> ProductsList;

}
