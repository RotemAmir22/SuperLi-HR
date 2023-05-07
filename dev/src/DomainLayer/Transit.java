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
    private Double ETA;
    private final Set<Supplier> destinationSuppliers;
    private final Set<Store> destinationStores;
    private final Set<OrderDocument> ordersDocs;


    public Transit(Date transitDate, Truck truck, Driver driver) {
        transitId = nextId;
        nextId++;
        this.transitDate = transitDate;
        this.truck = truck;
        this.driver = driver;
        this.ETA = 0.0;
        this.destinationStores = new HashSet<>();
        this.destinationSuppliers = new HashSet<>();
        this.ordersDocs = new HashSet<>();
    }

    public Double getETA() {
        return ETA;
    }

    public void setETA() {
        this.ETA = calculateETA();
    }

    public double calculateETA() {
        double eta = 0.0;
        Area lastSupplierAreaCode = null;

        // Iterate through suppliers
        for (Supplier supplier : this.getDestinationSuppliers()) {
            Area supplierAreaCode = supplier.getAreaCode();
            if (lastSupplierAreaCode != null && supplierAreaCode != lastSupplierAreaCode) {
                eta += 30.0;
            } else {
                eta += 15.0;
            }
            lastSupplierAreaCode = supplierAreaCode;
        }

        // Check first store against last supplier
        if (this.getDestinationStores().size() > 0) {
            Area firstStoreAreaCode = this.getDestinationStores().iterator().next().getAreaCode();
            if (lastSupplierAreaCode != null && firstStoreAreaCode != lastSupplierAreaCode) {
                eta += 30.0;
            } else {
                eta += 15.0;
            }
        }

        // Iterate through stores
        lastSupplierAreaCode = null;
        for (Store store : this.getDestinationStores()) {
            Area storeAreaCode = store.getAreaCode();
            if (lastSupplierAreaCode != null && storeAreaCode != lastSupplierAreaCode) {
                eta += 30.0;
            } else {
                eta += 15.0;
            }
            lastSupplierAreaCode = storeAreaCode;
        }

        return eta;
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
    public void removeDestinationSupplier(Supplier dest)
    {
        int count = 0;
        for (OrderDocument orderDoc : ordersDocs)
        {
            if (orderDoc.getSource().getSupplierID() == dest.getSupplierID())
            {
                count+=1;
            }
        }
        if (count > 1) return;
        destinationSuppliers.remove(dest);
    }
    public void removeDestinationStore(Store dest)
    {
        int count = 0;
        for (OrderDocument orderDoc : ordersDocs)
        {
            if (orderDoc.getDestination().getStoreId() == dest.getStoreId())
            {
                count+=1;
            }
        }
        if (count > 1) return;
        destinationStores.remove(dest);
    }
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
