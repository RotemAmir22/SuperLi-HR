package Service_HR;

import BussinesLogic.DailyShift;
import DataAccess.DAO_DailyShift;
import DataAccess.DAO_Generator;

import java.sql.SQLException;
import java.time.LocalDate;

public class SManageShifts extends AValidateInput {

    private DAO_DailyShift daoDailyShift;
    public SManageShifts() {
        try {
            daoDailyShift = DAO_Generator.getDailyShiftDAO();
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





}
