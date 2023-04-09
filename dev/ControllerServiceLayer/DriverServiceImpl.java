package ControllerServiceLayer;

import DataAccessLayer.DriverRepository;
import DomainLayer.Driver;
import DomainLayer.Truck;

import java.util.Set;

public class DriverServiceImpl implements DriverService{
    private final DriverRepository driverRepo;

    public DriverServiceImpl(DriverRepository driverRepo) {
        this.driverRepo = driverRepo;
    }

    @Override
    public Driver findDriverByID(int driverId) {
        return this.driverRepo.findDriverByID(driverId);
    }

    @Override
    public Set<Driver> getDriversSet() {
        return this.driverRepo.getDriversSet();
    }

    @Override
    public DriverRepository getDriverRepo() {
        return this.driverRepo;
    }

    @Override
    public void showAllDrivers() {
        for (Driver driver: driverRepo.getDriversSet()){
            driver.printDriver();
        }
    }

    @Override
    public boolean showDriverByID(int driverId){
        Driver driverToShow = findDriverByID(driverId);
        if (driverToShow != null){
            driverToShow.printDriver();
            return true;
        }
        else {
            return false;
        }
    }
}
