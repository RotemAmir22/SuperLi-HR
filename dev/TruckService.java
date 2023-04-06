import java.util.Set;

public interface TruckService {
    public Truck createTruck(String plateNumber, int iModel, int[] iQArr,
                      double truckWeight, double maxWeight);
   public Set<Truck> getAllTrucks();

}
