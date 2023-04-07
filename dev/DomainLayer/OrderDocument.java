package DomainLayer;

import java.util.Map;

public class OrderDocument {
    public static int documentNextId=1;
    private final int documentId;
    private Site source;
    private Site destination;
    private double totalWeight; //detailed weight ??
    private Map<String, Double> productsList;

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
        return productsList;
    }

    public void setProductsList(Map<String, Double> newProductsList) {
        productsList = newProductsList;
    }

    public void setWeight(double weight) {
        totalWeight=weight;
    }
    public void printOrderProductList() {
        for (Map.Entry<String, Double> entry : productsList.entrySet()) {
            String product = entry.getKey();
            Double amount = entry.getValue();
            System.out.println(product + " : " + amount);
        }
    }
    public void printOrderDestination() {
        System.out.println("Destination is: " +destination.address);
    }
    public void printOrderSource(){
        System.out.println("Source is: " + source.address);
    }
    public void printOrderId() {
        System.out.println("Order Id is: " + documentId);
    }

    public void printOrder(){
        System.out.println("Document id: " + this.documentId);
        System.out.println("Source: " + source.address);
        System.out.println("Destination: " + destination.address);
    }

}
