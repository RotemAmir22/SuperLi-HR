import java.util.Set;

public interface DataRepository {
    void saveTruck(Truck truck);
    Set<Truck> findAllTrucks();

    void saveOrderDoc(OrderDocument order);
    Set<OrderDocument> findAllOrdersDocs();

    void saveTransit (Transit transit);
    Set<Transit> findAllTransits();

    Set<Supplier> findAllSuppliers();
    Set<Store> findAllStores();
    Set<Driver> findAllDrivers();
    Set<Product> findAllProducts();
}
