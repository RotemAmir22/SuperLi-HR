public abstract class Site {
    protected String address;
    protected Area areaCode;
    protected String contactName;
    protected String ContactNumber;

    public Site(String address, Area areaCode, String contactName, String contactNumber) {
        this.address = address;
        this.areaCode = areaCode;
        this.contactName = contactName;
        ContactNumber = contactNumber;
    }
}
