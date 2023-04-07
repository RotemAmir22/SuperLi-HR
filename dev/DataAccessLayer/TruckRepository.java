package DataAccessLayer;

import DomainLayer.Truck;

import java.util.Set;

public interface TruckRepository {
    void saveTruck(Truck truck);
    void removeTruck(Truck truck);
    Set<Truck> getTrucksSet();
    Truck findTruckByPlateNumber(String PlateNumber);
}
