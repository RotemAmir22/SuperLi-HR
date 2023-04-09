package ControllerServiceLayer;

import DataAccessLayer.TruckRepository;
import DomainLayer.Qualification;
import DomainLayer.Truck;
import DomainLayer.TruckModel;

import java.util.Set;

public interface TruckService {
    Truck createTruck(String plateNumber, int iModel, int[] iQArr,
                      double truckWeight, double maxWeight);
    boolean removeTruckByPlateNumber(String tPlateNumber);
    Truck findTruckByPlate(String tPlateNumber);
    Set<Truck> getTrucksSet();
    TruckRepository getTruckRepo();
    void showAllTrucks();
    boolean showTruckByPlate(String tPlateNumber);
    TruckModel getTruckModel(Truck truck);
    Set<Qualification> getTruckQualiSet(Truck truck);


}
