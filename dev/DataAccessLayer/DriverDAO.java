package DataAccessLayer;
import DomainLayer.Driver;

import java.util.Set;

public interface DriverDAO {
    void saveDriver(Driver driver);
    Driver findDriverByID(int driverId);
    void removeDriver(Driver driver);
}
