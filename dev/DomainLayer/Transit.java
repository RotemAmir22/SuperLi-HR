package DomainLayer;

import java.time.LocalTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Transit {
    public static int nextId=1;
    private int transitId;
    private Date transitDate;
    private LocalTime departureTime;
    private Truck truck;
    private Driver driver; // String driverName??
    private Site source;
    private Set<Site> destinations;
    private Set<OrderDocument> ordersDocs;


    public Transit(Date transitDate, Truck truck, Driver driver) {
        transitId = nextId;
        nextId++;
        this.transitDate = transitDate;
        this.truck = truck;
        this.driver = driver;
        destinations = new HashSet<>();
        ordersDocs = new HashSet<>();
    }

    public int getTransitId() {
        return transitId;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public Date getTransitDate() {
        return transitDate;
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
        System.out.println("Transit id: " + transitId);
        System.out.println("Date: " + transitDate);
        System.out.println("Truck Details: ");
        getTruck().printTruck();
        System.out.println("Driver Details: ");
        getDriver().printDriver();
    }

    public void addDestination(Site dest){
        destinations.add(dest);
    };
    public void addOrderDoc(OrderDocument orderDocument){
        ordersDocs.add(orderDocument);
    };

    public void setTruck(Truck truck) {
        this.truck = truck;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }
}
