package DomainLayer;

public class Store extends Site {
    private final int storeId;


    public Store(String address, Area areaCode, String contactName, String contactNumber, int storeId) {
        super(address, areaCode, contactName, contactNumber);
        this.storeId = storeId;
    }
    public int getStoreId() {
        return storeId;
    }
}
