package DataAccess;

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

    public static DAO_BranchStore getBranchStoreDAO() {
        if (BranchStoreDAO == null) {
            BranchStoreDAO = new DAO_BranchStore();
        }
        return BranchStoreDAO;
    }

    public static DAO_Employee getEmployeeDAO() {
        if (EmployeeDAO == null) {
            EmployeeDAO = new DAO_Employee();
        }
        return EmployeeDAO;
    }


}
