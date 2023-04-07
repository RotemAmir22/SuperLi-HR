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
    double truckWeight;
    final double maxWeight;

    public Truck(String plateNumber, TruckModel model, double truckWeight, double maxWeight) {
        this.plateNumber = plateNumber;
        this.model = model;
        this.truckWeight = truckWeight;
        this.maxWeight = maxWeight;
        this.qSet = new HashSet<Qualification>();
    }

    public double getMaxWeight() {
        return maxWeight;
    }

    public double getCurrentWeight() {
        return truckWeight;
    }

    public void loadTruck(){};

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
        System.out.println("plate number: " + this.plateNumber);
        System.out.println("model: " + this.model);
        System.out.println("truck weight: " + this.truckWeight);
        System.out.println("max carry weight: " + this.maxWeight);
        System.out.println("Qualifications: ");
        for (Qualification q : qSet){
            System.out.println("\t" + q);
        }
        System.out.println();
    }
}
