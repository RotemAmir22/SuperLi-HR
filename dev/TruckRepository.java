import java.util.Set;

public interface TruckRepository {
    void saveTruck(Truck truck);
    void removeTruck(Truck truck);
    Set<Truck> findAllTrucks();
    Truck findTruckByPlateNumber(String PlateNumber);
}
