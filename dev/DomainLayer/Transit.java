package DomainLayer;

import java.util.Date;
import java.util.Set;

public class Transit {
    public static int nextId=1;
    private int TransitId;
    private Date TransitDate;
    private Truck truck;
    private Driver driver; // String driverName??
    public static Site source = new Parking("ParkingStreet", Area.Center, null, null);
    private Set<Site> destinations;
    private Set<OrderDocument> ordersDocs;


    public Transit(Date transitDate, Truck truck, Driver driver) {
        TransitId = nextId;
        nextId++;
        TransitDate = transitDate;
        this.truck = truck;
        this.driver = driver;
    }

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

    public void printTransit(){
        System.out.println("Transit id: " + this.TransitId);
        System.out.println("Date: " + this.TransitDate);
        System.out.println("Truck Details: ");
        getTruck().printTruck();
        System.out.println("Driver Details: ");
        getDriver().printDriver();
    }
}
