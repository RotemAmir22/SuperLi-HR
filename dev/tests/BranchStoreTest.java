
import Module_HR.BranchStore;
import Module_HR.DailyShift;
import Module_HR.Employee;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

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
    }
    @Test
    public void addEmployee() {

        b.addEmployee(e);
        b.addEmployee(e1);
        assertEquals(2,b.getEmployees().size());
    }

    @Test
    public void removeEmployee() {

        b.addEmployee(e);
        b.addEmployee(e1);
        b.removeEmployee(e);
        assertEquals(1,b.getEmployees().size());

    }

    @Test
    public void addShiftToHistory() {

        DailyShift shift = new DailyShift(LocalDate.now().minusDays(2));
        b.addShiftToHistory(shift);
        assertEquals(1,b.getShiftsHistory().size());
    }

    @Test
    public void deleteHistory() {
        DailyShift shift = new DailyShift(LocalDate.now().minusDays(2));
        b.addShiftToHistory(shift);
        b.deleteHistory();
        assertEquals(0,b.getShiftsHistory().size());
    }

    @Test
    public void findEmployeeInBranch() {

        b.addEmployee(e);
        b.addEmployee(e1);
        b.findEmployeeInBranch(e.getId());
        assertEquals("1111",b.findEmployeeInBranch(e.getId()).getId());
    }
}