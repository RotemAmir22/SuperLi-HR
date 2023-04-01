package Old.src;

import Module_HR.Employee;
import Module_HR.HR_SystemManagement;
import org.junit.Test;

import static org.junit.Assert.*;

public class HR_SystemManagementTest {

    HR_SystemManagement system = new HR_SystemManagement();

    @Test
    public void findEmployeeByID() {

        Employee e = system.findEmployeeByID("1");
        assertEquals("1", e.getId());
    }
}