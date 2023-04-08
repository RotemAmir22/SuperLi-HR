package ControllerServiceLayer;

import DataAccessLayer.TransitRepository;
import DataAccessLayer.TruckRepository;
import DomainLayer.*;
import ExceptionsPackage.UiException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

public class TransitServiceImpl implements TransitService{
    private TransitRepository transitRepo;
    private TruckService truckService;
    private DriverService driverService;

    public TransitServiceImpl(TransitRepository transitRepo) {
        this.transitRepo = transitRepo;
    }

    @Override
    public Transit createTransit(String dateString, String truckPlateNumber, int driverId) throws UiException {
        Date transitDate;
        try {
            transitDate = createDateObj(dateString);
        } catch (ParseException e) {
            throw new UiException("Invalid date format " + dateString + "%t" + "The correct format is: dd/mm/yyyy ");
        }

        Truck truckForTransit = truckService.findTruckByPlate(truckPlateNumber);
        if (truckForTransit == null) {
            throw new UiException("Truck's plate number not found: " + truckPlateNumber);
        }
        Driver driverForTransit = driverService.findDriverByID(driverId);
        if (driverForTransit == null) {
            throw new UiException("Driver id not found: " + truckPlateNumber);
        }
        Transit newTransit = new Transit(transitDate, truckForTransit, driverForTransit);
        return newTransit;
    }

    @Override
    public boolean removeTransitById(int transitId) {
        Transit transitToRemove = findTransitByID(transitId);
        if(transitToRemove==null)return false;
        this.transitRepo.removeTransit(transitToRemove);
        return true;
    }

    @Override
    public Transit findTransitByID(int transitId) {
        return this.transitRepo.findTransitByID(transitId);
    }

    @Override
    public Set<Transit> getTransitsSet() {
        return this.transitRepo.getTransitsSet();
    }

    @Override
    public TransitRepository getTransitRepo() {
        return this.transitRepo;
    }

    @Override
    public void showAllTransits() {
        for (Transit transit : getTransitsSet()){
            transit.printTransit();
        }
    }

    @Override
    public boolean showTransitByID(int transitId) {
        Transit transitToShow = findTransitByID(transitId);
        if(transitToShow==null) return false;
        transitToShow.printTransit();
        return true;
    }

    @Override
    public boolean setTransitTruck(Transit transit, String truckPlate) {
        Truck foundTruck = truckService.findTruckByPlate(truckPlate);
        if (foundTruck == null)return false;
        transit.setTruck(foundTruck);

        return true;
    }

    private Date createDateObj(String dateString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateString);
        Date transitDate = dateFormat.parse(dateString);
        return transitDate;
    }

    public void verifyDriverForTruck(Truck truck){
        TruckModel truckModel = truck.getModel();
        Set<Qualification> truckQualiSet = truck.getTruckQualification();
    }
}
