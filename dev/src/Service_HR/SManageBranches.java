package Service_HR;

import BussinesLogic.BranchStore;
import BussinesLogic.BranchStoreGenerator;
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

    public boolean updateOpenHours(int ID, boolean[][]data){
        try{
            BranchStore toUpdate = (BranchStore) branchStoreDAO.findByID(ID);
            for (int day = 0; day < data.length; day++) {
                for (int shift = 0; shift < data[day].length; shift++) {
                    boolean isOpen = data[day][shift];
                    if(!isOpen)
                        toUpdate.setOpenHours(day, shift, 1);
                }
            }
            branchStoreDAO.update(toUpdate);
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            return false;
        }


    }
}
