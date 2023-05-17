package DataAccess;

import BussinesLogic.TransitCoordinator;
import DataAccessLayer.*;

import java.sql.SQLException;

/**
 * DAO generator
 * Implements by singleton
 */
public class DAO_Generator {

    private static DAO_BranchStore BranchStoreDAO;
    private static DAO_Employee EmployeeDAO;
    private static DAO_DailyShift DailyShiftDAO;

    private static OrderDocumentDAO orderDocumentDAO;
    private static SupplierDAO supplierDAO;
    private static ProductDAO productDAO;
    private static TruckDAO truckDAO;
    private static TransitDAO transitDAO;

    private DAO_Generator() {
        // Private constructor to prevent instantiation from outside the class
    }

    //use singleton design patter for all DAOs
    public static DAO_BranchStore getBranchStoreDAO() throws SQLException, ClassNotFoundException {
        if (BranchStoreDAO == null) {
            BranchStoreDAO = new DAO_BranchStore();
        }
        return BranchStoreDAO;
    }

    public static DAO_Employee getEmployeeDAO() throws SQLException, ClassNotFoundException {
        if (EmployeeDAO == null) {
            EmployeeDAO = new DAO_Employee();
        }
        return EmployeeDAO;
    }

    public static DAO_DailyShift getDailyShiftDAO() throws SQLException, ClassNotFoundException {
        if (DailyShiftDAO == null) {
            DailyShiftDAO = new DAO_DailyShift();
        }
        return DailyShiftDAO;
    }

    public static TruckDAO getTruckDAO() throws SQLException, ClassNotFoundException {
        if (truckDAO == null) {
            truckDAO = new TruckDAOImpl();
        }
        return truckDAO;
    }
    public static SupplierDAO getSupplierDAO() throws SQLException, ClassNotFoundException {
        if (supplierDAO == null) {
            supplierDAO = new SupplierDAOImpl();
        }
        return supplierDAO;
    }
    public static ProductDAO getProductDAO() throws SQLException, ClassNotFoundException {
        if (productDAO == null) {
            productDAO = new ProductDAOImpl();
        }
        return productDAO;
    }

    public static OrderDocumentDAO getOrderDocumentDAO() throws SQLException, ClassNotFoundException {
        if (orderDocumentDAO == null) {
            orderDocumentDAO = new OrderDocumentDAOImpl(getSupplierDAO(), getBranchStoreDAO(), getProductDAO());
        }
        return orderDocumentDAO;
    }

    public static TransitDAO getTransitDAO() throws SQLException, ClassNotFoundException {
        if (transitDAO == null) {
            transitDAO = new TransitDAOImpl(getTruckDAO(), getEmployeeDAO(), getOrderDocumentDAO(), getSupplierDAO(), getBranchStoreDAO());
        }
        return transitDAO;
    }

}
