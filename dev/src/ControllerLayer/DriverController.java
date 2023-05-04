package ControllerLayer;

import DomainLayer.Driver;

public interface DriverController {
    Driver findDriverByID(int driverId);
}
