package Module_HR;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args){

        HR_SystemManagement system = new HR_SystemManagement();
        LocalDate currentDate = LocalDate.now();
        /* Reset employee's limit of shifts if the week is over */
        if(currentDate.toString().equals("Saturday"))
        {
            for (Employee employee : system.getNetworkEmployees()){employee.setShiftsLimit(6);}
        }

        system.newBranchInNetwork();
        system.newEmployeeInNetwork();
        system.schedulingFromEmployees();
        system.setShift();
        system.getNetworkBranches().get(0).showShiftByDate("2023-03-30");
        system.changeShiftSchedule();



    }
}
