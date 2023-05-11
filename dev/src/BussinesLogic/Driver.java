package BussinesLogic;

import DomainLayer.Qualification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class Driver extends Employee{
    private ArrayList<License> licenses;
    private ArrayList<LocalDate> transits;

    public Driver(String firstName, String lastName, String id, String bankAccount, double salary, String empTerms, String startDate) {
        super(firstName, lastName, id, bankAccount, salary, empTerms, startDate);
        licenses = new ArrayList<>();
        transits = new ArrayList<>();
    }

    public void addLicense(License newLicense){licenses.add(newLicense);}
    public void removeLicense(License license){  licenses.remove(license);}

    //getters
    public ArrayList<License> getLicenses(){return this.licenses;}
    public ArrayList<LocalDate> getTransits(){return transits;}

    //functions on transit
    public void addTransit(LocalDate date){this.transits.add(date);}
    public void removeTransit(LocalDate date){this.transits.remove(date);}

    /**
     * if the driver is available on given date, if the driver doesn't have the date in the transit list
     * @param date date to check
     * @return true if available, and false if not
     */
    public boolean isAvailableOnDate(LocalDate date){return !this.transits.contains(date);}

    /**
     * prints the drivers transits dates that are scheduled
     */
    public void printTransits()
    {
        System.out.printf(this.getName() +" transit schedule is: "+ this.getTransits().toString());
    }

    public void printEmployeeDetails()
    {
        printEmployeeDetails();
        printTransits();
    }
//    public void printDriver(){
//        System.out.println("driver's name: "+ driverName);
//        System.out.println("driver's id: "+ driverId);
//        System.out.println("driver's Licenses: ");
//        for (Qualification driverLicense : licenses)
//        {
//            System.out.println("\t"+driverLicense);
//        }
}
