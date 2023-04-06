import java.util.List;
import java.util.Set;

public class TruckServiceImpl implements TruckService {
    static DataRepository dataRep = new DataRepositoryImpl();

    @Override
    public Truck createTruck(String plateNumber, int iModel, int[] iQArr, double truckWeight, double maxWeight) {
        TruckModel model = TruckModel.values()[iModel];
        Truck truck = new Truck(plateNumber, model, truckWeight, maxWeight);
        for (int i : iQArr) {
            truck.addToQSet(Qualification.values()[i]);
        }
        return truck;
    }


    @Override
    public Set<Truck> getAllTrucks() {
        return dataRep.findAllTrucks();
    }

}