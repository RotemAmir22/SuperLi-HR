package DataAccessLayer;
import DomainLayer.Driver;
import java.util.HashSet;
import java.util.Set;


public class DriverDAOImpl implements DriverDAO {
    private final Set<Driver> driversSet = new HashSet<>();


    @Override
    public void saveDriver(Driver driver) {
        driversSet.add(driver);
    }
    @Override
    public Driver findDriverByID(int driverId) {
        if (driversSet.isEmpty())return null;
        for (Driver driver: driversSet){
            if(driver.getDriverId() == driverId)
                return driver;
        }
        return null;
    }
    @Override
    public void removeDriver(Driver driver) {
        driversSet.remove(driver);
    }
}
