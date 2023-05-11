package DataAccess;

import java.sql.SQLException;

/**
 * DAO generator
 * Implements by singleton
 */
public class DAO_Generator {

    private static DAO_BranchStore BranchStoreDAO;
    private static DAO_Employee EmployeeDAO;

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



}
