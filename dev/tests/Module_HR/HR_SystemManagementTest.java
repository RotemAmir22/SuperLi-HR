package Module_HR;

import org.junit.Test;

import static org.junit.Assert.*;

public class HR_SystemManagementTest {

    HR_SystemManagement system = new HR_SystemManagement();


    @Test
    public void CreateEmployee() {

        assertEquals(1,system.getNumOfEmployee());
    }

    @Test
    public void newBranchInNetwork() {
    }

    @Test
    public void schedulingFromEmployees() {
    }

    @Test
    public void setShift() {
    }

    @Test
    public void changeShiftSchedule() {
    }

    @Test
    public void updateEmployeeConstrainsByID() {
    }
}