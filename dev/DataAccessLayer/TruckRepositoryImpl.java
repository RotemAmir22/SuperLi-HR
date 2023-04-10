package DataAccessLayer;

import DomainLayer.Truck;

import java.util.HashSet;
import java.util.Set;

public class TruckRepositoryImpl implements TruckRepository{
    private final Set<Truck> trucks = new HashSet<>();
    @Override
    public void saveTruck(Truck truck) {
        trucks.add(truck);
    }
    @Override
    public void removeTruck(Truck truck) {
        trucks.remove(truck);
    }
    @Override
    public Set<Truck> getTrucksSet() {
        return trucks;
    }
    @Override
    public Truck findTruckByPlateNumber(String tPlateNumber) {
        for (Truck truck : trucks) {
            if (truck.getPlateNumber().equals(tPlateNumber)) {
                return truck;
            }
        }
        return null; // Truck with specified plate number not found
        }
}
