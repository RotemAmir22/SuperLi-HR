package DataAccessLayer;
import DomainLayer.Driver;
import java.util.HashSet;
import java.util.Set;


public class DriverRepositoryImpl implements DriverRepository{
    private final Set<Driver> drivers = new HashSet<>();

    @Override
    public void saveDriver(Driver driver) {
        drivers.add(driver);
    }

    @Override
    public void removeDriver(Driver driver) {
        drivers.remove(driver);
    }

    @Override
    public Set<Driver> getDriversSet() {
        return drivers;
    }

    @Override
    public Driver findDriverByID(int driverId) {
        for (Driver driver: drivers){
            if(driver.getDriverId() == driverId)
                return driver;
        }
        return null;
    }
}
