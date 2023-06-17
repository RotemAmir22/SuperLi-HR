package Service_HR;

import BussinesLogic.*;
import DataAccess.DAO_BranchStore;
import DataAccess.DAO_Generator;
import DataAccessLayer.TransitDAO;
import DomainLayer.Area;
import DomainLayer.Transit;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SManageBranches extends AValidateInput {

    DAO_BranchStore branchStoreDAO;

    public SManageBranches() {
        try {
            branchStoreDAO = DAO_Generator.getBranchStoreDAO();
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    /**
     * Insert new branch to network
     * @param name of the branch
     * @param address of the branch
     * @param area of the branch
     * @param phoneNum of the branch contact number
     * @param openH of the branch
     * @return success status
     */
    public int insertNewBranch(String name, String address, String area, String phoneNum, String openH){
        if(!isPhone(phoneNum))
            return -1;
        try{
            int id = branchStoreDAO.nextID();
            BranchStore newBranch = BranchStoreGenerator.CreateBranchStore(name, Area.valueOf(area), address, phoneNum, openH, id);
            branchStoreDAO.insert(newBranch);
            return id;
        } catch (SQLException | ClassNotFoundException e) {
            return -1;
        }
    }
    public boolean update(int type, String ID, String input){
        try {
            BranchStore toUpdate = (BranchStore) branchStoreDAO.findByID(ID);
            switch (type) {
                case 1 -> toUpdate.setName(input);
                case 2 -> toUpdate.setPhoneNum(input);
                case 3 -> toUpdate.setOpeningTime(input);
                default -> {
                }
            }
            branchStoreDAO.update(toUpdate);
            return true;
        }catch (SQLException | ClassNotFoundException e){
            return false;
        }
    }

    /**
     * Update open hours
     * @param ID of the branch
     * @param data to update
     * @return status
     */
    public boolean updateOpenHours(int ID, boolean[][]data){
        try{
            BranchStore toUpdate = (BranchStore) branchStoreDAO.findByID(ID);
            for(int i=0; i<7; i++) {
                boolean isOpenM = data[i][0];
                toUpdate.setOpenHours(i, 0, !isOpenM?1:0);
                boolean isOpenE = data[i][1];
                toUpdate.setOpenHours(i, 1, !isOpenE?1:0);
            }
            branchStoreDAO.update(toUpdate);
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            return false;
        }
    }

    public int[][] getOpenhours(int ID){
        try{
            return ((BranchStore)branchStoreDAO.findByID(ID)).getOpenHours();
        } catch (SQLException | ClassNotFoundException e) {
           return null;
        }
    }
    /**
     * Alert if the input is not a suitable id
     * @param input branch id
     * @return status
     */
    public boolean searchBranch(String input){
        try{
            BranchStore b = (BranchStore) branchStoreDAO.findByID(input);
            if(b != null)
                return true;
        }catch (SQLException | ClassNotFoundException e){
            return false;
        }
        return false;
    }

    public Map<LocalDate, Boolean> getStoreKeeper(int id){
        try{
            return ((BranchStore) branchStoreDAO.findByID(id)).storekeeperStatusByDate;
        } catch (SQLException | ClassNotFoundException e) {
            return null;
        }
    }

    /**
     * Insert an employee to branch
     * @param BID the branch id
     * @param EID the employee id
     * @return -1 in case it's driver, 1 in case the employee already work in this branch, and 2 SQL exception
     */
    public int addEmployee(int BID, String EID){
        try {
            SManageEmployees SME = new SManageEmployees();
            BranchStore branchStore = (BranchStore) branchStoreDAO.findByID(BID);
            for (Employee e : SME.getAllEmployees())
                if (e.getId().equals(EID)) {
                    if (e.canDoRole(Role.DRIVER))
                        return -1;
                    else {
                        if(!branchStore.getEmployees().contains(e)) {
                            branchStore.addEmployee(e);
                            branchStoreDAO.update(branchStore);
                            return 0;
                        }
                        else
                            return 1;
                    }
                }
            return 2;
        }
        catch (SQLException | ClassNotFoundException e) {
            return 3;
        }
    }

    /**
     * Remove an employee from branch
     * @param BID branch id
     * @param EID employee id
     * @return status
     */
    public int removeEmployee(int BID, String EID){
        try{
            SManageEmployees SME = new SManageEmployees();
            BranchStore branchStore = (BranchStore) branchStoreDAO.findByID(BID);
            for (Employee e : SME.getAllEmployees())
                if (e.getId().equals(EID)) {
                    if(!branchStore.getEmployees().contains(e))
                        return 1; // is not there
                    else {
                        branchStore.removeEmployee(e);
                        branchStoreDAO.update(branchStore);
                        return 0; // success
                    }
                }
            return -1; // not found
        }
        catch (SQLException | ClassNotFoundException e) {
            return 2;
        }
    }

    public List<BranchStore> getAllBranches(){
        try{
            return branchStoreDAO.getNetworkBranches();
        } catch (SQLException | ClassNotFoundException e) {
            return null;
        }
    }

    public List<Employee> getAllEmployees(int id){
        try{
           return ((BranchStore) branchStoreDAO.findByID(id)).getEmployees();
        } catch (SQLException | ClassNotFoundException e) {
            return null;
        }
    }

    public List<Transit> getAllTransits(int ID){
        try{
            TransitDAO transitDAO = DAO_Generator.getTransitDAO();
            BranchStore branchStore = (BranchStore) branchStoreDAO.findByID(ID);
            List<Transit> toReturn = new ArrayList<>();
            for(Map.Entry<LocalDate, Boolean> transit : branchStore.storekeeperStatusByDate.entrySet())
            {
                Boolean status = transit.getValue();
                if(status) {
                    LocalDate date = transit.getKey();
                    for(Transit t : transitDAO.getTransitsSet(false))
                        if(t.getTransitDate().equals(date))
                            toReturn.add(t);
                }
            }
            return toReturn;
        } catch (SQLException | ClassNotFoundException e) {
            return null;
        }
    }
}
