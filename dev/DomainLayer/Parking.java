package DomainLayer;

public class Parking extends Site {
    public final static int parkingId = 50;
    public Parking(String address, Area areaCode, String contactName, String contactNumber) {
        super(address, areaCode, contactName, contactNumber);
    }
}
