package Service_HR;

import BussinesLogic.*;
import DataAccess.DAO_BranchStore;
import DataAccess.DAO_Employee;
import DataAccess.DAO_Generator;
import DomainLayer.Area;

import java.sql.SQLException;

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

    public boolean updateOpenHours(int ID, boolean[][]data){
        try{
            BranchStore toUpdate = (BranchStore) branchStoreDAO.findByID(ID);
            for(int i=0; i<7; i++) {
                boolean isOpenM = data[i][0];
                if(!isOpenM)
                    toUpdate.setOpenHours(i, 0, 1);
                boolean isOpenE = data[i][1];
                if(!isOpenE)
                    toUpdate.setOpenHours(i, 1, 1);
            }
            branchStoreDAO.update(toUpdate);
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            return false;
        }
    }

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
}
