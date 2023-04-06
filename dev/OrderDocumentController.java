import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class OrderDocumentController {

    /**
     * q: what is the logic behind creating orderDoc with weight passed to the constructor
     *    of order - there are no product yet, so hoe can i know what the weight is ??
     */
    public OrderDocument CreateOrderDoc(Site store, Site dest, double weight){
        OrderDocument orderDoc = new OrderDocument(store,dest,weight);
        return orderDoc;

    }
public void orderdoc(){

    }
}
