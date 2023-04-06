import java.util.HashSet;
import java.util.Set;

public class DataRepositoryImpl implements DataRepository {
    private final Set<Truck> trucks = new HashSet<>();
    private final Set<OrderDocument> orderDocs = new HashSet<>();
    private final Set<Transit> transits = new HashSet<>();
    private final Set<Driver> drivers = new HashSet<>();
    private final Set<Product> products = new HashSet<>();
    private final Set<Supplier> suppliers = new HashSet<>();
    private final Set<Store> stores = new HashSet<>();


    @Override
    public void saveTruck(Truck truck) { trucks.add(truck);
    }

    @Override
    public Set<Truck> findAllTrucks() {
        return trucks;
    }


    @Override
    public void saveOrderDoc(OrderDocument orderDoc) {
        orderDocs.add(orderDoc);
    }

    @Override
    public Set<OrderDocument> findAllOrdersDocs() {
        return orderDocs;
    }

    @Override
    public void saveTransit(Transit transit) {
        transits.add(transit);
    }

    @Override
    public Set<Transit> findAllTransits() {
        return transits;
    }

    @Override
    public Set<Driver> findAllDrivers() {
        return drivers;
    }

    @Override
    public Set<Product> findAllProducts() {
        return products;
    }

    public Set<Supplier> findAllSuppliers() {
        return suppliers;
    }

    public Set<Store> findAllStores() {
        return stores;
    }
}
