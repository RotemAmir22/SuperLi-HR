package ControllerServiceLayer;

import DataAccessLayer.DriverRepository;
import DomainLayer.Driver;
import java.util.Set;

public interface DriverService {
    Driver findDriverByID(int driverId);
    Set<Driver> getDriversSet();
    DriverRepository getDriverRepo();
    void showAllDrivers();
    boolean showDriverByID(int driverId);
}
