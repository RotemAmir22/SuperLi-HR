package DataAccess;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * DAO generator
 * Implements by singleton
 */
public class DAO_Generator {

    private static DAO_BranchStore BranchStoreDAO;
    private static DAO_Employee EmployeeDAO;
    private static DAO_DailyShift DailyShiftDAO;

    private static Connection connection;

    static {
        try {
            connection = Database.connect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public DAO_Generator() throws SQLException, ClassNotFoundException {
        // Private constructor to prevent instantiation from outside the class
    }

    //use singleton design patter for all DAOs
    public static DAO_BranchStore getBranchStoreDAO() throws SQLException, ClassNotFoundException {
        if (BranchStoreDAO == null) {
            BranchStoreDAO = new DAO_BranchStore(connection);
        }
        return BranchStoreDAO;
    }

    public static DAO_Employee getEmployeeDAO() throws SQLException, ClassNotFoundException {
        if (EmployeeDAO == null) {
            EmployeeDAO = new DAO_Employee(connection);
        }
        return EmployeeDAO;
    }

    public static DAO_DailyShift getDailyShiftDAO() throws SQLException, ClassNotFoundException {
        if (DailyShiftDAO == null) {
            DailyShiftDAO = new DAO_DailyShift(connection);
        }
        return DailyShiftDAO;
    }

}
