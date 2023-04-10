package ControllerServiceLayer;

import DataAccessLayer.TransitRepository;
import DomainLayer.*;
import ExceptionsPackage.QualificationsException;
import ExceptionsPackage.UiException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

public class TransitServiceImpl implements TransitService{
    private final TransitRepository transitRepo;
    private final TruckService truckService;
    private final DriverService driverService;
    private final OrderDocumentService orderDocService;
    private final TransitRecordService transitRecordService;

    public TransitServiceImpl(TransitRepository transitRepo, TruckService truckService,
                              DriverService driverService, OrderDocumentService orderDocService,
                              TransitRecordService transitRecordService) {
        this.transitRepo = transitRepo;
        this.truckService = truckService;
        this.driverService = driverService;
        this.orderDocService = orderDocService;
        this.transitRecordService = transitRecordService;

    }

    @Override
    public Transit createTransit(String dateString, String truckPlateNumber, int driverId) throws UiException, QualificationsException {
        Date transitDate;
        try {
            transitDate = createDateObj(dateString);
        } catch (ParseException e) {
            throw new UiException("Invalid date format " + dateString + "\t" + "The correct format is: dd/mm/yyyy ");
        }
        Truck truckForTransit = truckService.findTruckByPlate(truckPlateNumber);
        if (truckForTransit == null) {
            throw new UiException("Truck's plate number not found: " + truckPlateNumber);
        }
        Driver driverForTransit = driverService.findDriverByID(driverId);
        if (driverForTransit == null) {
            throw new UiException("Driver id not found: " + truckPlateNumber);
        }
        boolean driverCanDriveTruckFlag = isDriverAllowToDriveTruck(truckForTransit, driverForTransit);
        if (!driverCanDriveTruckFlag){
            throw new QualificationsException("Driver lack certain qualifications for driving the chosen truck");
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
    public int replaceTransitTruck(int transitId, String newTruckPlate) {
        Transit transitToUpdate = findTransitByID(transitId);
        if (transitToUpdate == null) return -2; //transit fail
        Truck otherTruck = truckService.findTruckByPlate(newTruckPlate);
        if (otherTruck == null) return -1; //truck fail
        Driver currentDriver = transitToUpdate.getDriver();
        boolean qualifiedDriverFlag = isDriverAllowToDriveTruck(otherTruck,currentDriver);
        if (!qualifiedDriverFlag) return 0; //driver fail
        transitToUpdate.setTruck(otherTruck);
        return 1;// successes
    }
    public int replaceTransitDriver(int transitId, int newDriverId, String truckPlate){
        Driver otherDriver = driverService.findDriverByID(newDriverId);
        if (otherDriver == null) return -1; //fail to find driver
        Truck newTruck = truckService.findTruckByPlate(truckPlate);
        Transit transitToUpdate = findTransitByID(transitId);
        boolean qualifiedDriverFlag = isDriverAllowToDriveTruck(newTruck,otherDriver);
        if (!qualifiedDriverFlag) return 0; // driver is not qualified
        //driver is qualified
        transitToUpdate.setDriver(otherDriver);
        return 1;
    }
    @Override
    public OrderDocumentService getOrderDocService() {
        return orderDocService;
    }

    @Override
    public TransitRecordService getTransitRecordService() {
        return transitRecordService;
    }

    public void printTransitRecords(){
        this.transitRecordService.showTransitRecords();
    }
    @Override
    public boolean isValidWeight(Transit currentTransit, OrderDocument orderDocument) {
        Truck currentTruck = currentTransit.getTruck();
        double maxCarry = currentTruck.getMaxCarryWeight();
        double currentTransitWeight = currentTransit.calcOrdersWeight();
        double weightToAdd = orderDocument.getTotalWeight();
        double newCapacity = maxCarry-(currentTransitWeight+weightToAdd);
        if (newCapacity < 0){
            System.out.println("Exceeding truck's max weight");
            System.out.printf("available capacity is: %.2f %n", (maxCarry-currentTransitWeight));
            System.out.println("Order was not added to transit");
            return false;
        }
        return true;
    }
    public Date createDateObj(String dateString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date transitDate = dateFormat.parse(dateString);
        return transitDate;
    }
    public boolean isDriverAllowToDriveTruck(Truck truck, Driver driver){
        Set <Qualification> truckQualiSet = truck.getTruckQualification();
        Set <Qualification> driverLicenseSet = driver.getLicenses();
        return (driverLicenseSet.containsAll(truckQualiSet));
    }
    public void moveTransitToFinished(Transit completedTransit){
        this.transitRepo.removeTransit(completedTransit);
        this.transitRepo.saveToCompleted(completedTransit);
    }
}