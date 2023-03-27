package Module_HR_Part1.src;

import java.time.LocalDate;
import java.util.*;


/**
 * This class will help HR user to manage the system:
 * - schedule shifts by the ShiftOrganizer class
 * - ask for employees' constraints by the EmployeeConstraints class
 * - manage a menu with options for user's system.
 * We assume there is one user at this point.
 */
public class HR_SystemManagement {
    
    private List<BranchStore> branches;
    private List<Employee> networkEmployees;

    /**
     * look for branch using given id
     * @param ID : branch id to find
     * @return if found return branch and if not, null
     */
    public BranchStore findBranchByID(int ID) {

        for (BranchStore branch : branches) {
            if (branch.getBranchID()==ID) {
                return branch;
            }
        }
        return null;
    }

    /**
     *
     * @param e: add employee to list of all networks employees
     */
    public void addEmployeeToNetwork(Employee e)
    {
        networkEmployees.add(e);
    }

    /**
     * add a new employee to system
     */
    public void newEmployeeDetails()
    {
        //get from HR manager all the details to create a new employee in the system
        System.out.println("Hello HR manager, to add a new employee please enter the following details:");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter employee's First name: ");
        String first = scanner.nextLine();
        System.out.println("Enter employee's Last name: ");
        String last = scanner.nextLine();
        System.out.println("Enter employee's ID: ");
        String id = scanner.nextLine();
        System.out.println("Enter employee's bank account: ");
        String bankAccount = scanner.nextLine();
        System.out.println("Enter employee's salary: ");
        double salary = scanner.nextDouble();
        System.out.println("Enter computer path of employee's terms of employment: ");
        String filePath = scanner.nextLine();
        System.out.println("Enter employee's start Date (enter as yyy-MM-dd): ");
        LocalDate startDate = LocalDate.parse(scanner.nextLine());

        //create an employee generator
        EmployeeGenerator employeeGenerator = new EmployeeGenerator();
        //create new employee
        Employee employee = employeeGenerator.CreateEmployee(first,last,id,bankAccount,salary,filePath,startDate);
        //add employee to list of all employees in network
        addEmployeeToNetwork(employee);


        //add employee to branch
        BranchStore branchToAddTo = null;
        while (true)
        {
            System.out.println("Enter branch id of employee: ");
            int branchNum = scanner.nextInt();

            //find branch in network
            branchToAddTo = findBranchByID(branchNum);
            if(branchToAddTo == null){
                System.out.println("Enter ID that does not exists, try again: ");
            }
            else break;
        }
        //add to branch
        branchToAddTo.addEmployee(employee);
        System.out.println("Employee successfully added to system");
    }
    public List<Employee> getNetworkEmployees()
    {
        return networkEmployees;
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
        HR_SystemManagement system = new HR_SystemManagement();

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
                    for(int i = 0; i< system.getNetworkEmployees().size(); i ++)
                    {
                        EmployeeConstraints.askForConstraints(system.getNetworkEmployees().get(i));
                    }
                }
            }

        };
        // Schedule the task to run every week
        timer.schedule(weeklyConstraints, 0,604800000);

        /*
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
    /*
     * save cancellations->save in shift manager the details and counter in daily shift
     * save reports
     */

}
