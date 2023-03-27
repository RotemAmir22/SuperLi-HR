package Module_HR_Part1.src;

import java.io.File;
import java.util.Date;
import java.util.List;

public class ShiftManager extends Employee {

    List<ShiftM_Permissions> permissions;

    public ShiftManager(String firstName, String lastName, String id, String bankAccount, double salary, File empTerms, Date startDate) {
        super(firstName, lastName, id, bankAccount, salary, empTerms, startDate);
    }
}
