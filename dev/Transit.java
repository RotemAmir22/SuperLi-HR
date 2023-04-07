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

    public int getTransitId() {
        return TransitId;
    }

    public Date getTransitDate() {
        return TransitDate;
    }

    public Truck getTruck() {
        return truck;
    }

    public Driver getDriver() {
        return driver;
    }

    public Site getSource() {
        return source;
    }

    public Set<Site> getDestinations() {
        return destinations;
    }

    public Set<OrderDocument> getOrdersDocs() {
        return ordersDocs;
    }
}
