
import Module_HR.BranchStore;
import Module_HR.DailyShift;
import Module_HR.Employee;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class BranchStoreTest {

    private static Employee e;
    private static Employee e1;
    private static BranchStore b;

    @BeforeClass
    public static void setup()
    {
        e = new Employee("Yossi", "Cohen", "1111", "098762",500,"Salary: 500 per shift", "2022-11-12");
        e1 = new Employee("Shula", "Cohen", "2222", "098763",500,"Salary: 500 per shift", "2022-11-12");
        b = new BranchStore("Super","Hayarkon-17","08-689534","24/7");
        b.addEmployee(e);
        b.addEmployee(e1);
        DailyShift shift = new DailyShift(LocalDate.now());
        b.addShiftToHistory(shift);
    }

    /**
     * Add 2 employees to branch b
     * check the employees added to the list in branch
     */
    @Test
    public void addEmployee() {

        assertEquals(2,b.getEmployees().size());
    }

    /**
     * Add 2 employees and remove one of them
     * check if the list was updated
     */
    @Test
    public void removeEmployee() {

        b.removeEmployee(e);
        assertEquals(1,b.getEmployees().size());
        b.addEmployee(e);

    }

    /**
     * Create a new shift and add to a specific branch's shifts history
     * check in the history list if there is 1 shift
     */
    @Test
    public void addShiftToHistory()
    {
        assertEquals(1,b.getShiftsHistory().size());
    }

    /**
     * Add a new shift of the last month to branch
     * delete the history (history will delete for 1 month)
     * check if the history list was updated
     */
    @Test
    public void deleteHistory() {
        DailyShift OneMonthAgo = new DailyShift(LocalDate.now().minusMonths(1));
        b.addShiftToHistory(OneMonthAgo);
        assertEquals(2,b.getShiftsHistory().size());
        b.deleteHistory(); // delete month ago
        assertEquals(1,b.getShiftsHistory().size());
    }

    /**
     * Add 2 employees to branch
     * search one of them by his id
     */
    @Test
    public void findEmployeeInBranch() {
        b.findEmployeeInBranch(e.getId());
        assertEquals("1111",b.findEmployeeInBranch(e.getId()).getId());
    }

    /**
     * Add a new shift of today's date to branch
     * get the shift by the date (today)
     */
    @Test
    public void getShiftByDate()
    {
        DailyShift tmp = b.getShiftByDate(String.valueOf(LocalDate.now()));
        assertEquals(tmp.getDate(),LocalDate.now());
    }
}