package Module_HR;

import java.time.LocalDate;

public class ShiftManagerGeneratore {

    public ShiftManager CreateShiftManager(String name, String id, LocalDate shiftDate, int shiftSlot){
        return new ShiftManager(name,id,shiftDate, shiftSlot);
    }
}
