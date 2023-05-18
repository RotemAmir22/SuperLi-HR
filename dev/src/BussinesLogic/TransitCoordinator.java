package BussinesLogic;

import DataAccess.DAO_BranchStore;
import DataAccess.DAO_Employee;
import DataAccess.DAO_Generator;
import Presentation.EmployeeConstraints;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class TransitCoordinator {
    private DAO_BranchStore branchStoreDAO;
    private DAO_Employee driversDAO;

    /**
     * Using DAO's of branchStore and drivers
     * this class will let you manage a transit include add drivers
     */
    public TransitCoordinator() throws SQLException, ClassNotFoundException {
        branchStoreDAO = DAO_Generator.getBranchStoreDAO();
        driversDAO = DAO_Generator.getEmployeeDAO();
    }

    public DAO_Employee getDriversDAO() {
        return driversDAO;
    }

    /**
     * This function help to transit-module to schedule the drivers
     * @param transitDate to schedule
     * @param license of the needed driver
     * @return list of available drivers
     */

    public List<Driver> getAvailableDrivers(LocalDate transitDate, Set<License> license) {
        List<Driver> availableDrivers = new ArrayList<>();
        List<Driver> netWorkDrivers = null;
        try {
            netWorkDrivers = driversDAO.getNetworkDrivers();
        } catch (SQLException s) {
            s.printStackTrace();
        }
        if (netWorkDrivers == null) return null;
        System.out.println("debug: 1");
        for(Driver driver : netWorkDrivers)
            if(driver.getLicenses().containsAll(license))
                if(EmployeeConstraints.checkDriverAvailabilityForDate(transitDate, driver))
                    availableDrivers.add(driver);
        return availableDrivers;
    }

    /**
     * Add transit's date to branch
     * @param date of the potential transit
     * @param branchID to add the transit
     */
    public void addTransitInDate(LocalDate date, int branchID) throws SQLException, ClassNotFoundException {
        if (branchStoreDAO.getNetworkBranches().get(branchID) != null) {
            BranchStore branchStore = branchStoreDAO.getNetworkBranches().get(branchID);
            branchStore.storekeeperStatusByDate.put(date, false); // default value until validate there is a storekeeper
            branchStoreDAO.update(branchStore);
        } else
            System.out.println("Invalid branch ID");
    }

    /**
     * Add a driver to a transit
     * @param date of the transit
     * @param driverID which is going to be added
     * @param licenses of the truck that the driver needs to know
     */
    public Driver addDriverToTransit(Date date, String driverID, Set<License> licenses){
        //need to add function that seeks a driver by id and Date in the DAO
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        List<Driver> driversList = getAvailableDrivers(localDate,licenses);
        for (Driver driver : driversList) {
            if (driver.getId().equalsIgnoreCase(driverID)) {
                return driver;
            }
        }
        return null;
    }


    /**
     * switch a new driver to a transit instead of the old driver
     * used both in begin transit (on the fly) and in update transit - switch truck
     * in case that the old driver is not eligible to drive the new truck
     * @param date of the transit
     * @param newdriverID which is going to be added
     * @param licenses of the truck that the driver must have
     * @param oldDriverID which needed to be removed from the transit - need to delete its date from transitDate list
     */

    public Driver SwitchDriverInTransit(Date date, String newdriverID, Set<License> licenses, String oldDriverID) {

        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        List<Driver> driversList = getAvailableDrivers(localDate,licenses);
        for (Driver driver : driversList) {
            if (driver.getId().equalsIgnoreCase(newdriverID)) {
                return driver;
            }
        }
        return null;
    }

    /**
     *
     * @param store the store that we need to check if there are workers there
     * @param date date of transit
     * @return true if there are 2 storageWorkers, else false
     */
    public boolean StorageWorkersExist(BranchStore store, LocalDate date)
    {
        if(store.storekeeperStatusByDate.containsKey(date))
            return store.storekeeperStatusByDate.get(date);
        return false;
    }


    /**
     * Get the map of date and driver's transits from a branch
     * @param branchID of the branch
     * @param date of the transit
     * @return the transits in the branch
     */
    public Map<LocalDate, Boolean> getTransitsInBranch(int branchID, LocalDate date) throws SQLException, ClassNotFoundException {
        if(branchStoreDAO.getNetworkBranches().get(branchID) != null)
            return branchStoreDAO.getNetworkBranches().get(branchID).storekeeperStatusByDate;
        else
            System.out.println("Invalid branch ID");
        return null;
    }

    /**
     *
     * @param storeId the store id from the DAO
     * used in orderDocumentController
     * @return branchStore
     */
    public BranchStore findStoreById(int storeId) {
        BranchStore branchStore = null;
        try {
            branchStore = (BranchStore) branchStoreDAO.findByID(storeId);
        } catch (SQLException | ClassNotFoundException s)
        {
            s.printStackTrace();
        }
        return branchStore;
    }

    /**
     * Static function to alert of any change in transit's status
     */
    public static void Alert(String message){
        System.out.println(message);
    }
}
