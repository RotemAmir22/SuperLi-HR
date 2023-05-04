package ControllerLayer;

import DataAccessLayer.DriverDAO;
import DomainLayer.Driver;

public class DriverControllerImpl implements DriverController {
    private final DriverDAO driverDAO;


    public DriverControllerImpl(DriverDAO driverDAO) {
        this.driverDAO = driverDAO ;
    }
    @Override
    public Driver findDriverByID(int driverId) {
        return this.driverDAO.findDriverByID(driverId);
    }
}
