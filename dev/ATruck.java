/**
 * consider make this class "normal class" (not abstract)
 * need to add more methods
 **/

public abstract class ATruck {
    final String plateNumber;
    final String model;
    final double truckWeight;
    final double maxWeight;
    double currentWeight;
    final DriverLicenseNeeded licenseRequired;

    public ATruck(String plateNumber, String model, double truckWeight, double maxWeight) {
        this.plateNumber = plateNumber;
        this.model = model;
        this.truckWeight = truckWeight;
        this.maxWeight = maxWeight;
        this.currentWeight = truckWeight;
        this.licenseRequired = maxWeight > 12000 ? DriverLicenseNeeded.c1 : DriverLicenseNeeded.c;
    }

    public double getMaxWeight() {
        return maxWeight;
    }

    public DriverLicenseNeeded getLicenseRequired() {
        return licenseRequired;
    }

    public double getCurrentWeight() {
        return currentWeight;
    }

    public void setCurrentWeight(double currentWeight) {
        this.currentWeight = currentWeight;
    }

    public void loadTruck(){};


}
