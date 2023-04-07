import java.util.Set;

public class Driver {
    private int driverId;
    private String driverName;
    private Set<DriverLicense> licenses;

    public int getDriverId() {
        return driverId;
    }

    public String getDriverName() {
        return driverName;
    }

    public Set<DriverLicense> getLicenses() {
        return licenses;
    }
}
