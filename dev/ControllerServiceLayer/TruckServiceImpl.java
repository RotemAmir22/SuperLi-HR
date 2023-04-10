package ControllerServiceLayer;

import DataAccessLayer.TruckRepository;
import DomainLayer.Qualification;
import DomainLayer.Truck;
import DomainLayer.TruckModel;

import java.util.Set;

public class TruckServiceImpl implements TruckService {
    private final TruckRepository truckRepo;
    public TruckServiceImpl(TruckRepository truckRepo){
        this.truckRepo = truckRepo;
    }
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
    public boolean removeTruckByPlateNumber(String tPlateNumber) {
        Truck truckToRemove = this.truckRepo.findTruckByPlateNumber(tPlateNumber);
        if (truckToRemove == null)return false;
        this.truckRepo.removeTruck(truckToRemove);
        return true;
        }
    @Override
    public Truck findTruckByPlate(String tPlateNumber) {
        return this.truckRepo.findTruckByPlateNumber(tPlateNumber);
    }
    @Override
    public Set<Truck> getTrucksSet() {
        return this.truckRepo.getTrucksSet();
    }
    @Override
    public TruckRepository getTruckRepo() {
        return this.truckRepo;
    }
    @Override
    public void showAllTrucks() {
        Set<Truck> trucks = truckRepo.getTrucksSet();
        for (Truck t : trucks){
            t.printTruck();
        }
    }
    @Override
    public boolean showTruckByPlate(String tPlateNumber) {
        Truck truckToShow = findTruckByPlate(tPlateNumber);
        if (truckToShow == null)return false;
        truckToShow.printTruck();
        return true;
        }
    @Override
    public TruckModel getTruckModel(Truck truck) {
        return truck.getModel();
    }
    @Override
    public Set<Qualification> getTruckQualiSet(Truck truck) {
        return null;
    }
    @Override
    public void transferLoad(Truck smallerTruck, Truck biggerTruck){
        biggerTruck.loadTruck(smallerTruck.getCurrentWeight());
        smallerTruck.setCurrentLoadWeight(0);
    }
}