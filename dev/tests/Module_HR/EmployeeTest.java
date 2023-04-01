package Module_HR;

import org.junit.Test;

import static org.junit.Assert.*;

public class EmployeeTest {

    Employee e = new Employee("Yossi", "Cohen", "1111", "098762",500,"Salary: 500 per shift", "2022-11-12");

    @Test
    public void setConstrains() {

        e.setConstrains(0,0,false);
        assertFalse(e.getConstrains()[0][0]);

    }

    @Test
    public void setShiftsLimit() {

        e.setShiftsLimit(5);
        assertEquals(5,e.getShiftsLimit());
    }

    @Test
    public void setSalary() {

        e.setSalary(560);
        assertEquals(560, e.getSalary(), 0);
    }

    @Test
    public void addRole() {

        e.addRole(Role.SHIFTMANAGER);
        assertTrue(e.canDoRole(Role.SHIFTMANAGER));
    }

}