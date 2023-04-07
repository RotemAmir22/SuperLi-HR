package DomainLayer;

public class Supplier extends Site{
    private int supplierID;

    public Supplier(String address, Area areaCode, String contactName, String contactNumber, int supplierID) {
        super(address, areaCode, contactName, contactNumber);
        this.supplierID = supplierID;

    }

    public int getSupplierID() {
        return supplierID;
    }
}
