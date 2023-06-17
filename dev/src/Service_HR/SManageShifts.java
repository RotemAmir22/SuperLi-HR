package Service_HR;

import BussinesLogic.DailyShift;
import DataAccess.DAO_DailyShift;
import DataAccess.DAO_Generator;

import java.sql.SQLException;

public class SManageShifts {

    private DAO_DailyShift daoDailyShift;
    public SManageShifts() {
        try {
            daoDailyShift = DAO_Generator.getDailyShiftDAO();
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public boolean insert(DailyShift dailyShift, int id){
        try{
            daoDailyShift.insert(dailyShift, id);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

}
