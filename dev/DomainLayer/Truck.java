package DomainLayer;

import java.util.HashSet;
import java.util.Set;

/**
 * consider make this class "normal class" (not abstract)
 * need to add more methods
 **/

public class Truck {
    final String plateNumber;
    final TruckModel model;
    Set<Qualification> qSet;
    final double truckWeight;
    double currentLoadWeight;
    final double maxCarryWeight;

    public Truck(String plateNumber, TruckModel model, double truckWeight, double maxWeight) {
        this.plateNumber = plateNumber;
        this.model = model;
        this.truckWeight = truckWeight;
        this.currentLoadWeight = 0;
        this.maxCarryWeight = maxWeight;
        this.qSet = new HashSet<Qualification>();
    }
    public Truck(String plateNumber, TruckModel model, double truckWeight, double maxWeight, Set<Qualification> quliSet) {
        this.plateNumber = plateNumber;
        this.model = model;
        this.truckWeight = truckWeight;
        this.currentLoadWeight = 0;
        this.maxCarryWeight = maxWeight;
        this.qSet = quliSet;
    }

    public double getMaxCarryWeight() {
        return maxCarryWeight;
    }

    public double getCurrentWeight() {
        return currentLoadWeight;
    }

    public void loadTruck(double weight){
        currentLoadWeight += weight;
    }; // will be inside truck service
    public void unloadTruck (double weight)
    {
        currentLoadWeight-=weight;
    }
    public Set<Qualification> getTruckQualification(){
        return this.qSet;
    }
    public void addToQSet(Qualification qQual){
        this.qSet.add(qQual);
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void printTruck(){
        System.out.println("Plate number: " + this.plateNumber);
        System.out.println("Model: " + this.model);
        System.out.println("Truck's weight: " + this.truckWeight);
        System.out.println("Current load weight: " + this.currentLoadWeight);
        System.out.println("Max carry weight: " + this.maxCarryWeight);
        if (!qSet.isEmpty()) {
            System.out.println("Qualifications: ");
            for (Qualification q : qSet) {
                System.out.println("\t" + q);
            }
        }
        System.out.println();
    }

    public TruckModel getModel() {
        return model;
    }

    public Set<Qualification> getqSet() {
        return qSet;
    }
}
