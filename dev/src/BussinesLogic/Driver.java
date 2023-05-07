package BussinesLogic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Driver extends Employee{
    private ArrayList<License> licenses;

    public Driver(String firstName, String lastName, String id, String bankAccount, double salary, String empTerms, String startDate) {
        super(firstName, lastName, id, bankAccount, salary, empTerms, startDate);
        licenses = new ArrayList<>();
    }

    public void addLicense(License newLicense){licenses.add(newLicense);}

    public void removeLicense(License license){  licenses.remove(license);}

    public ArrayList<License> getLicenses(){return this.licenses;}



}
