package Module_HR_Part1.src;

import java.text.ParseException;
import java.util.*;


/**
 * This class will help HR user to manage the system:
 * - schedule shifts by the ShiftOrganizer class
 * - ask for employees' constraints by the EmployeeConstraints class
 * - manage a menu with options for user's system.
 * We assume there is one user at this point.
 */
public class HR_SystemManagement {
    
    private List<BranchStore> networkBranches;
    private List<Employee> networkEmployees;

    public HR_SystemManagement() {
        this.networkBranches=new ArrayList<BranchStore>();
        this.networkEmployees=new ArrayList<Employee>();
    }

    /**
     * look for branch using given id
     * @param ID : branch id to find
     * @return if found return branch and if not, null
     */
    public BranchStore findBranchByID(int ID) {

        for (BranchStore branch : networkBranches) {
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
    public void addEmployeeToList(Employee e){this.networkEmployees.add(e);}

    /**
     * add a new employee to system
     */
    public void newEmployeeInNetwork() {
        //get from HR manager all the details to create a new employee in the system
        System.out.println("Hello HR manager, to add a new employee please enter the following details:");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter employee's start Date (enter as yyyy-MM-dd): ");
        String startDate = scanner.nextLine();
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
        System.out.println("Enter computer path of employee's terms of employment:\n");
        String filePath = "";

        //create an employee generator
        EmployeeGenerator employeeGenerator = new EmployeeGenerator();
        //create new employee
        Employee employee = employeeGenerator.CreateEmployee(first,last,id,bankAccount,salary,filePath,startDate);
        //add employee to list of all employees in network
        addEmployeeToList(employee);

        //add employee to branch
        String answer = "y";
        while (answer == "y")
        {
            System.out.println("Enter branch id of employee: ");
            int branchNum = scanner.nextInt();

            //find branch in network
            boolean addEm = addEmployeeToBranch(employee,branchNum);
            if(!addEm){
                System.out.println("ID entered does not exist, please try again: ");
            }
            else {
                System.out.println("Do you want to add the employee to another branch? (enter y/n)");
                answer = scanner.nextLine();
            }
        }
        System.out.println("Employee successfully added to system");
    }

    /**
     * create new branch in system
     */
    public void newBranchInNetwork(){
        //get from HR manager all the details to create a new employee in the system
        System.out.println("Hello HR manager, to add a new branch please enter the following details:");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter branch's name: ");
        String name = scanner.nextLine();
        System.out.println("Enter branch's address: ");
        String address = scanner.nextLine();
        System.out.println("Enter branch's phone number: ");
        String phone = scanner.nextLine();

        BranchStore branchStore = new BranchStore(name,address,phone);
        addBranchStoreToList(branchStore);
        System.out.println("Branch successfully added to system, ID number is: "+ branchStore.getBranchID());
    }

    /**
     *
     * @param employee: employee to add
     * @param branchID: id of branch to add to
     * @return return ture id successful
     */
    public boolean addEmployeeToBranch(Employee employee, int branchID)
    {
        BranchStore branch = findBranchByID(branchID);
        if (branch == null)
            return false;
        branch.addEmployee(employee);
        return true;
    }

    /**
     *
     * @return list of all employees in system
     */
    public List<Employee> getNetworkEmployees()
    {
        return networkEmployees;
    }

    /**
     *
     * @param b: adds this branch to the list of all the branches in the network
     */
    public void addBranchStoreToList(BranchStore b)
    {
        networkBranches.add(b);
    }

    /**
     *
     * @return list of all the branches in the network
     */
    public List<BranchStore> getNetworkBranches()
    {
        return networkBranches;
    }

    /**
     * once a week the system asks all the network employees for their schedule constraints
     */
    public void schedualingFromEmployees()
    {
        HR_SystemManagement system = new HR_SystemManagement();
        /*
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

    }

    /**
     * daily the system build the shifts for the next day in each branch
     */
    public void setShift()
    {
        HR_SystemManagement system = new HR_SystemManagement();
        Timer timer = new Timer();
        /*
         * Second function set all branches shifts for one day.
         */
        timer = new Timer();
        TimerTask dailyShift = new TimerTask() {
            @Override
            public void run() {
                Hashtable<String, Employee> listEmployees = null;
                //for loop that run on the branch list and collect the employees list
                for(int i = 0; i<system.getNetworkBranches().size(); i++)
                {
                    // get the employees from each branch and set them a new scheduling
                    listEmployees = system.getNetworkBranches().get(i).getEmployees();
                    ShiftOrganizer.DailyShifts(listEmployees); // Call the function to run every 24 hours
                }

            }
        };
        // Schedule the task to run every 24 hours
        timer.schedule(dailyShift, 0, 24 * 60 * 60 * 1000);
    }

    /**
     * Main function
     * @param args
     */
    public static void main(String[] args) throws ParseException {

        HR_SystemManagement system = new HR_SystemManagement();
        system.newBranchInNetwork();
        system.newEmployeeInNetwork();

    }
    /*
     * save cancellations->save in shift manager the details and counter in daily shift
     * save reports
     */

}
