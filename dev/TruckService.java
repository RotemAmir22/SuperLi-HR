import java.util.Set;

public interface TruckService {
    public Truck createTruck(String plateNumber, int iModel, int[] iQArr,
                      double truckWeight, double maxWeight);
    public boolean removeTruckByPlateNumber(String tPlateNumber);
    public Truck findTruckByPlate(String tPlateNumber);
    public Set<Truck> getAllTrucks();
    public TruckRepository getTruckRepo();

}
