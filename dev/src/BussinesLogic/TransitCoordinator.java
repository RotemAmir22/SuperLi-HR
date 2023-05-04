package BussinesLogic;

import DataAccess.DAO_BranchStore;
import DataAccess.DAO_Drivers;
import DataAccess.DAO_Generator;
import Presentation.EmployeeConstraints;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TransitCoordinator {

    private DAO_BranchStore branchStoreDAO;
    private DAO_Drivers driversDAO;

    /**
     * Using DAO's of branchStore and drivers
     * this class will let you manage a transit include add drivers
     */
    public TransitCoordinator(){
        branchStoreDAO = DAO_Generator.getBranchStoreDAO();
        driversDAO = DAO_Generator.getDriverDAO();
    }

    /**
     * This function help to transit-module to schedule the drivers
     * @param transitDate to schedule
     * @param license of the needed driver
     * @return list of available drivers
     */
    public List<Driver> getAvailableDrivers(LocalDate transitDate, License license){
        List<Driver> availableDrivers = new ArrayList<>();
        for(Driver driver : driversDAO.getNetworkDrivers())
            if(driver.getLicenses().contains(license))
                if(EmployeeConstraints.checkDriverAvailabilityForDate(transitDate, driver))
                    availableDrivers.add(driver);
        return availableDrivers;

    }

    /**
     * Add transit's date to branch
     * @param date of the potential transit
     * @param branchID to add the transit
     */
    public void addTransitInDate(LocalDate date, int branchID){
        if(branchStoreDAO.getNetworkBranches().get(branchID) != null)
        {
            BranchStore branchStore = branchStoreDAO.getNetworkBranches().get(branchID);
            branchStore.transits.put(date, null);
        }
        else
            System.out.println("Invalid branch ID");
    }

    /**
     * Add a driver to a transit
     * @param date of the transit
     * @param driver which is going to be added
     * @param branchID of the branch
     */
    public void addDriverToTransit(LocalDate date, Driver driver, int branchID){
        if(branchStoreDAO.getNetworkBranches().get(branchID) != null)
        {
            BranchStore branchStore = branchStoreDAO.getNetworkBranches().get(branchID);
            if(branchStore.transits.containsKey(date))
                branchStore.transits.put(date, driver);
            else
                System.out.println("Invalid transit date");
        }
        else
            System.out.println("Invalid branch ID");
    }

    /**
     * Get the map of date and driver's transits from a branch
     * @param branchID of the branch
     * @param date of the transit
     * @return the transits in the branch
     */
    public Map<LocalDate, Driver> getTransitsInBranch(int branchID, LocalDate date){
        if(branchStoreDAO.getNetworkBranches().get(branchID) != null)
            return branchStoreDAO.getNetworkBranches().get(branchID).transits;
        else
            System.out.println("Invalid branch ID");
        return null;
    }

    /**
     * Static function to alert of any change in transit's status
     */
    public static void Alert(String message){
        System.out.println(message);
    }
}
