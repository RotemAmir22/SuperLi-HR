package DataAccessLayer;
import DomainLayer.Driver;

import java.util.Set;

public interface DriverRepository {
    void saveDriver(Driver driver);
    void removeDriver(Driver driver);
    Set<Driver> getDriversSet();
    Driver findDriverByID(int driverId);
}
