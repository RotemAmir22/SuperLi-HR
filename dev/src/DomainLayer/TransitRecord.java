package DomainLayer;

import java.util.HashMap;
import java.util.Map;

public class TransitRecord {
    public static int recordNextId = 1;
    private int transitRecordId;
    private boolean transitProblem;
    private final Map<Supplier,Double> weightAtExit;
    private Transit transit;


    public TransitRecord(Transit transit) {
        this.transitRecordId = recordNextId;
        recordNextId++;
        this.transitProblem = false;
        this.weightAtExit = new HashMap<>();
        this.transit = transit;
    }
    public int getTransitRecordId() {
        return transitRecordId;
    }
    public void setTransitRecordId(int transitRecordId) {
        this.transitRecordId = transitRecordId;
    }
    public boolean isTransitProblem() {
        return transitProblem;
    }
    public void updateTransitProblem() {
        this.transitProblem = true;
    }
    public Map<Supplier, Double> getWeightAtExit() {
        return weightAtExit;
    }
    public void addSupWeightExit(Supplier supplier,Double weight) {
        weightAtExit.put(supplier,weight);
    }
    public Transit getTransit() {
        return transit;
    }
    public void setTransit(Transit transit) {
        this.transit = transit;
    }
    public void printTransitRecord(){
        System.out.println("Record id: " + transitRecordId);
        System.out.println("Transit id: " + transit.getTransitId());
        System.out.println("Overweight: " + transitProblem);
        weightAtExit.forEach((supplier, weight) -> {
            supplier.printSupplier();
            System.out.println(" Truck weight at exit: " + weight);
        });
    }
}
