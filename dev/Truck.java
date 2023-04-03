import java.util.Set;

/**
 * consider make this class "normal class" (not abstract)
 * need to add more methods
 **/

public class Truck {
    final String plateNumber;
    final TruckModel model;
    Set<Qualification> qSet;
    double truckWeight;
    final double maxWeight;

    public Truck(String plateNumber, TruckModel model, double truckWeight, double maxWeight, Set<Qualification> qSet) {
        this.plateNumber = plateNumber;
        this.model = model;
        this.qSet = qSet;
        this.truckWeight = truckWeight;
        this.maxWeight = maxWeight;
    }

    public double getMaxWeight() {
        return maxWeight;
    }

    public double getCurrentWeight() {
        return truckWeight;
    }

    public void loadTruck(){};


}
