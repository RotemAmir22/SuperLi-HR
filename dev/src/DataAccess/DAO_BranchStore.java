package DataAccess;

import BussinesLogic.*;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

import static BussinesLogic.Role.DRIVER;

/**
 * This DAO is for the branchStores
 */
public class DAO_BranchStore implements IDAO_Entity {

    private Map<Integer,BranchStore> networkBranches;

    private ArrayList<BranchStore> branchesList;

    private Connection conn;

    // constructor
    public DAO_BranchStore() throws SQLException, ClassNotFoundException {
        conn = Database.connect();
        networkBranches = new HashMap<>();
        branchesList = new ArrayList<>();
    }

    /**
     * find branchStore by ID
     * @param ID of the branch
     * @return the branch or null if not exist
     * @throws SQLException in case of error
     * @throws ClassNotFoundException in case of error
     */
    public Object findByID(Object ID) throws SQLException, ClassNotFoundException {
        Integer id = 0;
        try {
            id = Integer.parseInt((String) ID);
        }
        catch (Exception e){
            id= (Integer) ID;
        }
        if (networkBranches.containsKey(id))
            return networkBranches.get(id);
        else {
            PreparedStatement stmt = conn.prepareStatement("SELECT name, openingTime, address, areaCode, contactName FROM BranchStore WHERE branchID = ?");stmt.setInt(1, id);
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
                stmt.setInt(1, id);
                rs = stmt.executeQuery();
                while (rs.next()){
                    int day = rs.getInt("dayOfWeek");
                    int morning = rs.getInt("morningOpen");
                    int evening = rs.getInt("eveningOpen");
                    branchStore.setOpenHours(day, 0, morning);
                    branchStore.setOpenHours(day, 1, evening);
                }
                stmt.close();
                rs.close();
                // get branch's employees
                stmt = conn.prepareStatement("SELECT * FROM EmployeeBranches WHERE branchID = ?");
                stmt.setInt(1, id);
                rs = stmt.executeQuery();
                DAO_Employee employeesDAO = DAO_Generator.getEmployeeDAO();
                while (rs.next()){
                    String employeeId = rs.getString("employeeID");
                    Employee e = (Employee) employeesDAO.findByID(employeeId);
                    branchStore.addEmployee(e);
                }
                stmt.close();
                rs.close();
                // get branch's transits
                stmt = conn.prepareStatement("SELECT * FROM BranchStoreTransits WHERE branchID = ?");
                stmt.setInt(1, id);
                rs = stmt.executeQuery();
                while (rs.next()){
                    LocalDate transitDate = rs.getDate("transitDate").toLocalDate();
                    Boolean transitStatus = rs.getBoolean("status");
                    branchStore.storekeeperStatusByDate.put(transitDate, transitStatus);
                }
                stmt.close();
                rs.close();
                networkBranches.put(id, branchStore);
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
            // set opening hours
            for(int i=0; i<7; i++) {
                stmt = conn.prepareStatement("INSERT INTO BranchOpeningHours (branchID, dayOfWeek, morningOpen, eveningOpen)" + "VALUES (?,?,?,?)");
                stmt.setInt(1, branch.getBranchID());
                stmt.setInt(2, i);
                stmt.setInt(3, branch.getOpenHours()[i][0]);
                stmt.setInt(4, branch.getOpenHours()[i][1]);
                stmt.executeQuery();

            }
            networkBranches.put(branch.getBranchID(), branch);
        }
    }

    /**
     *  update a branch
     * @param o : branch to update
     * @throws SQLException
     */
    @Override
    public void update(Object o) throws SQLException {
        BranchStore branch = (BranchStore) o;
        if (branch != null) {
            //set details
            PreparedStatement stmt = conn.prepareStatement("UPDATE BranchStore SET name = ?, openingTime = ?, address = ?, areaCode = ?, contactName = ?, contactNumber = ? WHERE branchID = ?");
            stmt.setString(1, branch.getName());
            stmt.setString(2, branch.getOpeningTime());
            stmt.setString(3, branch.getAddress());
            stmt.setString(4, branch.getArea().toString());
            stmt.setString(5, branch.getContactName());
            stmt.setString(6, branch.getContactNumber());
            stmt.executeUpdate();

            // set opening hours
            for(int i=0; i<7; i++) {
                stmt = conn.prepareStatement("UPDATE BranchOpeningHours SET dayOfWeek = ?, morningOpen = ?, eveningOpen = ? WHERE branchID = ?");
                stmt.setInt(1, i);
                stmt.setInt(2, branch.getOpenHours()[i][0]);
                stmt.setInt(3, branch.getOpenHours()[i][1]);
                stmt.executeQuery();
            }

            // set Employees
            stmt = conn.prepareStatement("SELECT * FROM EmployeeBranches WHERE branchID = ?");
            stmt.setInt(1, branch.getBranchID());
            ResultSet rs = stmt.executeQuery();
            ArrayList<String> workersInDB = new ArrayList<>();
            while(rs.next()) {
                workersInDB.add(rs.getString("employeeID"));
            }
            // add employee to DB
            ArrayList<String> workersIdInbranch = new ArrayList<>();
            for(Employee e : branch.getEmployees())
            {
                workersIdInbranch.add(e.getId());
                if(!workersInDB.contains(e.getId())){
                    stmt = conn.prepareStatement("INSERT INTO EmployeeBranches (employeeID,branchID)" + "VALUES (?,?)");
                    stmt.setString(1,e.getId());
                    stmt.setInt(2, branch.getBranchID());
                    stmt.executeQuery();
                    break;
                }
            }
            // remove from DB
            for(String workerID : workersInDB){
                if(!workersIdInbranch.contains(workerID))
                {
                    stmt = conn.prepareStatement("DELETE FROM EmployeeBranches WHERE employeeID = ? AND branchID = ? ;VALUES (?,?)");
                    stmt.setString(1,workerID);
                    stmt.setInt(2, branch.getBranchID());
                    stmt.executeQuery();
                    break;
                }
            }

            // set transits
            stmt = conn.prepareStatement("SELECT * FROM BranchStoreTransits WHERE branchID = ?");
            stmt.setInt(1, branch.getBranchID());
            rs = stmt.executeQuery();
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

    /**
     *  delete branch from data base
     * @param o : branch to delete
     * @throws SQLException
     */
    @Override
    public void delete(Object o) throws SQLException {
        BranchStore b = (BranchStore) o;
        if(b != null) {
            //delete from branch store table
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM BranchStore WHERE branchID = ?");
            stmt.setInt(1, b.getBranchID());
            stmt.executeQuery();

            //delete from open hours
            stmt = conn.prepareStatement("DELETE FROM BranchOpeningHours WHERE branchID = ?");
            stmt.setInt(1, b.getBranchID());
            stmt.executeQuery();

            //delete from BranchStoreTransits
            stmt = conn.prepareStatement("DELETE FROM BranchStoreTransits WHERE branchID = ?");
            stmt.setInt(1, b.getBranchID());
            stmt.executeQuery();

            //delete from DailyShifts
            stmt = conn.prepareStatement("DELETE FROM DailyShifts WHERE branchID = ?");
            stmt.setInt(1, b.getBranchID());
            stmt.executeQuery();

            //delete from EmployeeBranches
            stmt = conn.prepareStatement("DELETE FROM EmployeeBranches WHERE branchID = ?");
            stmt.setInt(1, b.getBranchID());
            stmt.executeQuery();

            networkBranches.remove(b.getBranchID());
        }
    }

    /**
     * Get the branches in list
     * @return returns all the branches
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public List<BranchStore> getNetworkBranches() throws SQLException, ClassNotFoundException {
        if(networkBranches.isEmpty())
            ifEmptyMaps();
        if(branchesList.isEmpty())
            branchesList.addAll(networkBranches.values());
        else {
            branchesList.clear();
            branchesList.addAll(networkBranches.values());
        }
        return branchesList;
    }

    /**
     * uploads the data to the list if they are required
     * @throws SQLException
     */
    private void ifEmptyMaps() throws SQLException, ClassNotFoundException {
        PreparedStatement stmt = conn.prepareStatement("SELECT branchID FROM BranchStore");
        ResultSet rs = stmt.executeQuery();
        while(rs.next()) {
            findByID(rs.getString("branchID"));
        }
    }
}