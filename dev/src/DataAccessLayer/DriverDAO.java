package DataAccessLayer;
import DomainLayer.Driver;

public interface DriverDAO {
    void saveDriver(Driver driver);
    Driver findDriverByID(int driverId);
}
