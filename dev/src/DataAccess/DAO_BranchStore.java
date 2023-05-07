package DataAccess;

import BussinesLogic.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static BussinesLogic.Role.DRIVER;

public class DAO_BranchStore implements DAO{

    private Map<Integer,BranchStore> networkBranches;

    private Connection conn;

    // constructor
    public DAO_BranchStore() throws SQLException, ClassNotFoundException {
        conn = Database.connect();
        networkBranches = new HashMap<>();
    }

    public Object findByID(Object ID) throws SQLException, ClassNotFoundException {
        if (networkBranches.containsKey(ID))
            return networkBranches.get(ID);
        else {
            PreparedStatement stmt = conn.prepareStatement("SELECT name, openingTime, address, areaCode, contactName FROM BranchStore WHERE branchID = ?");stmt.setString(1, (String) ID);
            ResultSet rs = stmt.executeQuery();
            BranchStore branchStore;
            if (rs.next()) {
                String name = rs.getString("name");
                String openingTime = rs.getString("openingTime");
                String address = rs.getString("address");
                int areaCode = rs.getInt("areaCode");
                String contractName = rs.getString("contactName");
                branchStore = BranchStoreGenerator.CreateBranchStore(name, Area.values()[areaCode], openingTime, address, contractName);
                // get the openingHours
                stmt = conn.prepareStatement("SELECT * FROM BranchOpeningHours WHERE branchID = ?");
                stmt.setString(1, (String) ID);
                rs = stmt.executeQuery();
                while (rs.next()){
                    int day = rs.getInt("dayOfWeek");
                    int morning = rs.getInt("morningOpen");
                    int evening = rs.getInt("eveningOpen");
                    branchStore.setOpenHours(day, 0, morning);
                    branchStore.setOpenHours(day, 1, evening);
                }
                // get branch's employees
                stmt = conn.prepareStatement("SELECT * FROM EmployeeBranches WHERE branchID = ?");
                stmt.setString(1, (String) ID);
                rs = stmt.executeQuery();
                while (rs.next()){
                    String employeeId = rs.getString("employeeID");
                    DAO_Employee employeesDAO = DAO_Generator.getEmployeeDAO();
                    Employee e = (Employee) employeesDAO.findByID(employeeId);
                    branchStore.addEmployee(e);
                }
                networkBranches.put((int) ID, branchStore);
                return branchStore;
            }
        }
        return null;
    }
    @Override
    public void insert(Object o) throws SQLException {
        BranchStore branch = (BranchStore)o;
        if(branch != null) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO BranchStore (branchID, name, openingTime, address, areaCode, contactName, contactNumber)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)");
            stmt.setInt(1, branch.getBranchID());
            stmt.setString(2, branch.getName());
            stmt.setString(3, branch.getOpeningTime());
            stmt.setString(4, branch.getAddress());
            stmt.setString(5, branch.getArea().toString());
            stmt.setString(6, branch.getContactName());
            stmt.setString(7, branch.getContactNumber());
            stmt.executeUpdate();
        }

    }

    @Override
    public void update(Object o) {

    }

    @Override
    public void delete(Object o) {

    }

    public List<BranchStore> getNetworkBranches(){
        return (List<BranchStore>) networkBranches.values();
    }
}
