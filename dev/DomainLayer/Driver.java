package DomainLayer;

import java.util.Set;

public class Driver {
    private int driverId;
    private String driverName;
    private Set<DriverLicense> licenses;

    public Driver(int driverId, String driverName, Set<DriverLicense> licenses) {
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

    public Set<DriverLicense> getLicenses() {
        return licenses;
    }

    public void printDriver(){
        System.out.println("driver's name: "+ driverName);
        System.out.println("driver's id: "+ driverId);
        System.out.println("driver's Licenses: ");
        for (DriverLicense driverLicense : licenses)
        {
            System.out.println("%t"+driverLicense);
        }
    }
}
