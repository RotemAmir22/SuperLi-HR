package src.Module_HR;

import java.time.LocalDate;

/**
 * this class only generates shift manager objexts
 */
public class ShiftManagerGenerator {

    //generator of shift managers
    public ShiftManager CreateShiftManager(String name, String id, LocalDate shiftDate, int shiftSlot){
        return new ShiftManager(name,id,shiftDate, shiftSlot);
    }
}
