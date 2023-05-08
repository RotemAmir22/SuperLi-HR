package DomainLayer;

import java.util.Set;

public class Driver {
    private final int driverId;
    private final String driverName;
    private final Set<License> licenses;


    public Driver(int driverId, String driverName, Set<License> licenses) {
        this.driverId = driverId;
        this.driverName = driverName;
        this.licenses = licenses;
    }
    public int getDriverId() {
        return driverId;
    }
    public String getDriverName() {
        return driverName;
    }
    public Set<License> getLicenses() {
        return licenses;
    }
    public void printDriver(){
        System.out.println("driver's name: "+ driverName);
        System.out.println("driver's id: "+ driverId);
        System.out.println("driver's Licenses: ");
        for (License driverLicense : licenses)
        {
            System.out.println("\t"+driverLicense);
        }
    }
}
