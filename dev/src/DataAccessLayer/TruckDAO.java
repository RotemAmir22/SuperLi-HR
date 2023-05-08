package DataAccessLayer;

import DomainLayer.Truck;

import java.util.Set;

public interface TruckDAO {
    void removeTruckFromTrucksLicenses(Truck truck);

    Set<Truck> getTrucksSet();
    Truck findTruckByPlateNumber(String PlateNumber);
    void saveTruck(Truck truck);
    void removeTruck(Truck truck);
}
