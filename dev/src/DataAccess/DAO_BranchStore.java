package DataAccess;

import BussinesLogic.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
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

    /**
     * find branchStore by ID
     * @param ID of the branch
     * @return the branch or null if not exist
     * @throws SQLException in case of error
     * @throws ClassNotFoundException in case of error
     */
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
                // get branch's transits
                stmt = conn.prepareStatement("SELECT * FROM BranchStoreTransits WHERE branchID = ?");
                stmt.setString(1, (String) ID);
                rs = stmt.executeQuery();
                while (rs.next()){
                    LocalDate transitDate = rs.getDate("transitDate").toLocalDate();
                    Boolean transitStatus = rs.getBoolean("status");
                    branchStore.storekeeperStatusByDate.put(transitDate, transitStatus);
                }
                networkBranches.put((int) ID, branchStore);
                return branchStore;
            }
        }
        return null;
    }

    /**
     * insert new branch
     * @param o the branchStore
     * @throws SQLException in case of error
     */
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

            stmt = conn.prepareStatement("SELECT * FROM BranchOpeningHours WHERE branchID = ?");
            stmt.setInt(1, branch.getBranchID());
            ResultSet rs = stmt.executeQuery();

            networkBranches.put(branch.getBranchID(), branch);
        }
    }

    @Override
    public void update(Object o) throws SQLException {
        BranchStore branch = (BranchStore) o;
        if (branch != null) {
            //set details
            PreparedStatement stmt = conn.prepareStatement("UPDATE BranchStore SET branchID = ?, name = ?, openingTime = ?, address = ?, areaCode = ?, contactName = ?, contactNumber = ? WHERE branchID = ?");
            stmt.setInt(1, branch.getBranchID());
            stmt.setString(2, branch.getName());
            stmt.setString(3, branch.getOpeningTime());
            stmt.setString(4, branch.getAddress());
            stmt.setString(5, branch.getArea().toString());
            stmt.setString(6, branch.getContactName());
            stmt.setString(7, branch.getContactNumber());
            stmt.executeUpdate();
            // set transits
            stmt = conn.prepareStatement("SELECT * FROM BranchStoreTransits WHERE branchID = ?");
            stmt.setInt(1, branch.getBranchID());
            ResultSet rs = stmt.executeQuery();
            // get last date to count the transits
            rs.last();
            int amount = rs.getRow();
            rs.beforeFirst();
            // remove transit from branch
            if(branch.storekeeperStatusByDate.size() < amount){
                while(rs.next()) {
                    if(!branch.storekeeperStatusByDate.containsKey(rs.getDate("transitDate"))){
                        stmt = conn.prepareStatement("DELETE FROM BranchStoreTransits WHERE branchID = ? AND transitDate = ?");
                        stmt.setInt(1, branch.getBranchID());
                        stmt.setDate(2, rs.getDate("transitDate"));
                        stmt.executeQuery();
                        break;
                    }
                }
            }
            // add new transit to branch
            else if(branch.storekeeperStatusByDate.size() > amount){
                ArrayList<LocalDate> datesInDB = new ArrayList<>();
                while(rs.next()) {
                    datesInDB.add(rs.getDate("transitDate").toLocalDate());
                }
                for(LocalDate transitDate: branch.storekeeperStatusByDate.keySet()){
                    if(!datesInDB.contains(transitDate)) {
                        stmt = conn.prepareStatement("INSERT INTO BranchStoreTransits (branchID,transitDate, status)" + "VALUES (?,?,?)");
                        stmt.setInt(1,branch.getBranchID());
                        stmt.setDate(2, Date.valueOf(transitDate));
                        stmt.setBoolean(3,branch.storekeeperStatusByDate.get(transitDate));
                        stmt.executeQuery();
                        break;
                    }
                }
            }


        }
    }

    @Override    public void delete(Object o) {

    }

    public List<BranchStore> getNetworkBranches(){
        return (List<BranchStore>) networkBranches.values();
    }
}
