package Service_HR;

import BussinesLogic.BranchStore;
import BussinesLogic.DailyShift;
import BussinesLogic.Role;
import DataAccess.DAO_BranchStore;
import DataAccess.DAO_DailyShift;
import DataAccess.DAO_Generator;

import java.sql.SQLException;
import java.time.LocalDate;

public class SManageShifts extends AValidateInput {

    private DAO_DailyShift daoDailyShift;
    private DAO_BranchStore branchStoreDAO;
    public SManageShifts() {
        try {
            daoDailyShift = DAO_Generator.getDailyShiftDAO();
            branchStoreDAO = DAO_Generator.getBranchStoreDAO();
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    /**
     * Insert new shift to DB
     * @param dailyShift from Gui
     * @param id of the branch
     * @return status
     */
    public boolean insert(DailyShift dailyShift, int id){
        try{
            daoDailyShift.insert(dailyShift, id);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Check if the current day and branch had a shift
     * @param id of the branch
     * @param date of the shift
     * @return status
     */
    public boolean isShift(int id, LocalDate date){
        try{
            if(daoDailyShift.findByKey(date, id) != null)
                return true;
            return false;
        } catch (SQLException | ClassNotFoundException e) {
           return false;
        }
    }

    /**
     * Get a daily shift
     * @param id of the branch
     * @param date of the shift
     * @return the shift or null
     */
    public DailyShift get(int id, LocalDate date){
        try{
            return (DailyShift) daoDailyShift.findByKey(date, id);
        } catch (SQLException | ClassNotFoundException e) {
            return null;
        }
    }

    public boolean updateShift(BranchStore branch, int choice, LocalDate date, int shift, String employeeID, int role){
        try{
            if(choice == 0)
                daoDailyShift.addToShift(date, shift, employeeID, role, branch.getBranchID());
            else if(choice == 1)
                daoDailyShift.removefromShift(date, shift, employeeID, role, branch.getBranchID());
            daoDailyShift.update(branch.getShiftByDate(String.valueOf(date)),branch.getBranchID());
            branchStoreDAO.update(branch);
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            return false;
        }
    }





}
