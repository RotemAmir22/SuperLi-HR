package Module_HR_Part1.src;

import java.util.Hashtable;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * This class will help HR user to manage the system:
 * - schedule shifts by the ShiftOrganizer class
 * - ask for employees' constraints by the EmployeeConstraints class
 * - manage a menu with options for user's system.
 * We assume there is one user at this point.
 */
public class HR_SystemManagment {
    
    private List<BranchStore> branches;
    private List<Employee> employees;

    public void addEmployee(Employee e)
    {
        employees.add(e);
    }
    public List<Employee> getEmployees()
    {
        return employees;
    }

    public void addBranchStore(BranchStore b)
    {
        branches.add(b);
    }

    public List<BranchStore> getBranches()
    {
        return branches;
    }
    /**
     * Main function
     * @param args
     */
    public static void main(String[] args) {
        HR_SystemManagment system = new HR_SystemManagment();

        /**
         * First function ask all the employees in all branches to give constraints
         */
        Timer timer = new Timer();
        TimerTask weeklyConstraints = new TimerTask() {
            private long startTime = System.currentTimeMillis();
            public void run() {
                // This code will be executed every week until 3 days have passed
                if (System.currentTimeMillis() - startTime > 259200000) {
                    // Cancel the timer after 3 days
                    System.out.println("TIME IS UP \u231B if you don't give anything, your shifts are open!");
                    cancel();
                } else {
                    System.out.println("TIME TO SCHEDULE CONSTRAINTS \u231B");
                    for(int i = 0; i< system.getEmployees().size(); i ++)
                    {
                        EmployeeConstraints.askForConstraints(system.getEmployees().get(i));
                    }
                }
            }

        };
        // Schedule the task to run every week
        timer.schedule(weeklyConstraints, 0,604800000);

        /**
         * Second function set all branches shifts for one day.
         */
        timer = new Timer();
        TimerTask dailyShift = new TimerTask() {
            @Override
            public void run() {
                Hashtable<String, Employee> listEmployees = null;
                //for loop that run on the branch list and collect the employees list
                for(int i=0; i<system.getBranches().size(); i++)
                {
                    // get the employees from each branch and set them a new scheduling
                    listEmployees = system.getBranches().get(i).getEmployees();
                    ShiftOrganizer.DailyShifts(listEmployees); // Call the function to run every 24 hours
                }

            }
        };
        // Schedule the task to run every 24 hours
        timer.schedule(dailyShift, 0, 24 * 60 * 60 * 1000);

    }
    /**
     * save cancellations->save in shift manager the details and counter in daily shift
     * save reports
     */
}
