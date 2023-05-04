package DataAccessLayer;

import DomainLayer.Truck;

import java.util.HashSet;
import java.util.Set;

public class TruckDAOImpl implements TruckDAO {
    private final Set<Truck> trucksSet = new HashSet<>();


    @Override
    public void saveTruck(Truck truck) {
        trucksSet.add(truck);
    }
    @Override
    public void removeTruck(Truck truck) {
        trucksSet.remove(truck);
    }
    @Override
    public Set<Truck> getTrucksSet() {
        return trucksSet;
    }
    @Override
    public Truck findTruckByPlateNumber(String tPlateNumber) {
        for (Truck truck : trucksSet) {
            if (truck.getPlateNumber().equals(tPlateNumber)) {
                return truck;
            }
        }
        return null; // Truck with specified plate number not found
        }
}
