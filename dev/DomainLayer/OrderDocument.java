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
}
