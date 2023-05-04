package BussinesLogic;

import java.util.HashSet;
import java.util.Set;

public class Driver extends Employee{
    private Set<License> licenses;

    public Driver(String firstName, String lastName, String id, String bankAccount, double salary, String empTerms, String startDate) {
        super(firstName, lastName, id, bankAccount, salary, empTerms, startDate);
        licenses = new HashSet<>();
    }

    public void addLicense(License newLicense){
        if(!this.licenses.contains(newLicense))
            licenses.add(newLicense);
        else
            System.out.println("This driver already has this license!");
    }

    public void removeLicense(License license){
        if(this.licenses.contains(license))
            licenses.remove(license);
        else
            System.out.println("This driver hasn't this license!");
    }

    public Set<License> getLicenses(){return licenses;}



}
