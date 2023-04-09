package DomainLayer;

import java.util.HashMap;
import java.util.Map;

public class OrderDocument {
    public static int documentNextId=1;
    private final int documentId;
    private Site source;
    private Site destination;
    private double totalWeight; //detailed weight ??
    private Map<Product, Double> productsList;

    public OrderDocument(Site source, Site destination) {
        this.documentId = documentNextId;
        documentNextId++;
        this.source = source;
        this.destination = destination;
        this.totalWeight=0;
        this.productsList = new HashMap<>();
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

    public Map<Product, Double> getProductsList() {
        return productsList;
    }

    public void setProductsList(Map<Product, Double> newProductsList) {
        productsList = newProductsList;
    }

    public void setWeight(double weight) {
        totalWeight=weight;
    }
    public void printOrderProductList() {
        for (Map.Entry<Product, Double> entry : productsList.entrySet()) {
            String product = entry.getKey().getProductName();
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
