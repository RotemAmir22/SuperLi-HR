import java.util.Date;
import java.util.Set;

public class Transit {
    private int TransitId;
    private Date TransitDate;
    private Truck truck;
    private Driver driver; // String driverName??
    private Site source;
    private Set<Site> destinations;
    private Set<OrderDocument> ordersDocs;


}
