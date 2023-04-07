package DomainLayer;

import java.util.Map;

public class OrderDocument {
    public static int documentNextId=1;
    private int documentId;
    private Site source;
    private Site destination;
    private double totalWeight; //detailed weight ??
    public Map<String, Double> ProductsList;

    public OrderDocument(Site source, Site destination) {
        this.documentId = documentNextId;
        documentNextId++;
        this.source = source;
        this.destination = destination;
        this.totalWeight=0;
    }

    public int getDocumentId() {
        return documentId;
    }

    public Site getSource() {
        return source;
    }

    public Site getDestination() {
        return destination;
    }

    public double getTotalWeight() {
        return totalWeight;
    }

    public Map<String, Double> getProductsList() {
        return ProductsList;
    }

    public void setProductsList(Map<String, Double> productsList) {
        ProductsList = productsList;
    }

    public void setWeight(double weight) {
        totalWeight=weight;
    }
    public void printOrderProductList() {
        for (Map.Entry<String, Double> entry : this.ProductsList.entrySet()) {
            String product = entry.getKey();
            Double amount = entry.getValue();
            System.out.println(product + " : " + amount);
        }
    }
    public void printOrderDestination() {
        System.out.println("Destination is: " +this.destination.address);
    }
    public void printOrderSource(){
        System.out.println("Source is: " + this.source.address);
    }

    public void printOrderId() {
        System.out.println("Order Id is: " + this.documentId);
    }
}
