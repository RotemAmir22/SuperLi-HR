package Service_HR;

import BussinesLogic.*;
import DataAccess.DAO_BranchStore;
import DataAccess.DAO_DailyShift;
import DataAccess.DAO_Employee;
import DataAccess.DAO_Generator;

import java.io.File;
import java.sql.SQLException;
import java.time.LocalDate;

public class SManageShifts extends AValidateInput {

    private DAO_DailyShift daoDailyShift;
    private DAO_BranchStore branchStoreDAO;
    private DAO_Employee employeeDAO;
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

    public boolean manageShift(ShiftManager shiftManager, DailyShift currentShift, LocalDate date, int branchID){
        try{
            DailyShift shift = (DailyShift) daoDailyShift.findByKey(date, branchID);

        }catch (SQLException | ClassNotFoundException e) {
            return false;
        }
        return false;
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


    public int cancleItem(LocalDate date, int id, String name, int amount, String shiftMId) {
        try{
            DailyShift shift = (DailyShift) daoDailyShift.findByKey(date, id);
            Cancellation cancellation = new Cancellation(name,amount); // get an id
            for(ShiftManager shiftM : shift.getShiftManagers())
                if(shiftM.getId().equals(shiftMId)) {
                    shift.removeShiftManager(shiftM); // remove from shift
                    shiftM.addToCancelations(cancellation); // add cancellation
                    shift.addShiftManager(shiftM); // add back
                    daoDailyShift.update(shift, id);
                    return cancellation.getCancelID();
                }
        return -1;
        }catch (SQLException | ClassNotFoundException e) {
            return -1;
        }
    }

    public boolean uploadEndOfDayReport(File file, LocalDate date, int branchID){
        try{
            DailyShift currentShift = (DailyShift) daoDailyShift.findByKey(date, branchID);
            currentShift.setEndOfDayReport(file);
            daoDailyShift.update(currentShift, branchID);
            return true;
        }catch (SQLException | ClassNotFoundException e) {
            return false;
        }
    }

    public Cancellation getC(int branchID, LocalDate date, String ID, int cancleID){
        try{
            DailyShift shift = (DailyShift) daoDailyShift.findByKey(date, branchID);
            for(ShiftManager shiftM : shift.getShiftManagers())
                if(shiftM.getId().equals(ID))
                    return shiftM.findCancellationInList(cancleID);
        }catch (SQLException | ClassNotFoundException e) {
            return null;
        }
        return null;
    }
}
