import java.util.Set;

public interface DataRepository {
    void saveTruck(Truck truck);
    Set<Truck> findAllTrucks();

    void saveOrderDoc(OrderDocument order);
    Set<OrderDocument> findAllOrdersDocs();

    void saveTransit (Transit transit);
    Set<Transit> findAllTransits();

    Set<Driver> findAllDrivers();
    Set<Product> findAllProducts();
    Set<Store> findAllStores();
    Set<Supplier> findAllSuppliers();
}
