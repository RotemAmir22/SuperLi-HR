package DomainLayer;

import java.time.LocalTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Transit {
    public static int nextId=1;
    private final int transitId;
    private final Date transitDate;
    private LocalTime departureTime;
    private Truck truck;
    private Driver driver; // String driverName??
    private Site source;
    private final Set<Supplier> destinationSuppliers;
    private final Set<Store> destinationStores;
    private final Set<OrderDocument> ordersDocs;


    public Transit(Date transitDate, Truck truck, Driver driver) {
        transitId = nextId;
        nextId++;
        this.transitDate = transitDate;
        this.truck = truck;
        this.driver = driver;
        this.destinationStores = new HashSet<>();
        this.destinationSuppliers = new HashSet<>();
        this.ordersDocs = new HashSet<>();
    }
    public int getTransitId() {
        return transitId;
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
    public Set<Supplier> getDestinationSuppliers() {
        return destinationSuppliers;
    }
    public Set<Store> getDestinationStores() {
        return destinationStores;
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
        for (OrderDocument od : ordersDocs){
            od.printOrder();
        }
    }
    public void addDestinationStore(Store dest){
        destinationStores.add(dest);
    };
    public void addDestinationSupplier(Supplier dest){
        destinationSuppliers.add(dest);
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
    public void removeOrderDoc(OrderDocument orderDocument) {
        this.ordersDocs.remove(orderDocument);
    }
    public double calcOrdersWeight(){
        double currentWeight = 0;
        for (OrderDocument od : ordersDocs){
            currentWeight += od.getTotalWeight();
        }
        return currentWeight;
    }
    public Site getSource() {
        return source;
    }
    public void setSource(Site source) {
        this.source = source;
    }
    public LocalTime getDepartureTime() {
        return departureTime;
    }
}
