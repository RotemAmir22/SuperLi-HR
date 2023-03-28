package Module_HR_Part1.src;

import java.time.LocalDate;
import java.util.Date;

public class ShiftManagerGeneratore {

    public ShiftManager CreateShiftManager(String name, String id, LocalDate shiftDate, int shiftSlot){
        return new ShiftManager(name,id,shiftDate, shiftSlot);
    }
}
