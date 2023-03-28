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
     *
     * @param employee: gets employee and adds to the employee
     */
    public void addQualificationToEmployee(Employee employee)
    {
        Scanner scanner = new Scanner(System.in);
        String answer = "y";
        System.out.println("Add qualification to new Employee: ");
        //add qualification to employee - only one when it is a new employee
        while (Objects.equals(answer, "y")) {
            System.out.println("Choose from the following, please enter the number of the role");
            Role[] roles = Role.values();
            for (int i = 0; i < roles.length; i++) {
                System.out.println(i + " - " + roles[i]);
            }
            int qualification = scanner.nextInt();
            employee.addRole(roles[qualification]);
            answer=scanner.nextLine();
            System.out.println("Would you like to add more qualifications? (Enter y/n): ");
            answer = scanner.nextLine();

        }
    }

    /**
     * function asks the user what branch to add to
     * @param employee: employee to add
     */
    public void addEmployeeToBranch(Employee employee)
    {
        //add employee to branch
        String answer = "y";
        Scanner scanner = new Scanner(System.in);
        while (Objects.equals(answer, "y"))
        {
            System.out.println("Enter branch ID: ");
            int branchNum = scanner.nextInt();

            //find branch in network
            BranchStore branch = findBranchByID(branchNum);
            if(branch== null){
                System.out.println("ID entered does not exist, please try again: ");
            }
            else {
                branch.addEmployee(employee);
                answer=scanner.nextLine();
                System.out.println("Do you want to add the employee to another branch? (enter y/n): ");
                answer = scanner.nextLine();
            }
        }
    }
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
        System.out.println("Enter computer path of employee's terms of employment: ");
        String filePath = "";

        //create an employee generator
        EmployeeGenerator employeeGenerator = new EmployeeGenerator();
        //create new employee
        Employee employee = employeeGenerator.CreateEmployee(first,last,id,bankAccount,salary,filePath,startDate);
        //add employee to list of all employees in network
        addEmployeeToList(employee);

        //add employee to branch
        addEmployeeToBranch(employee);

        //add qualifications
        addQualificationToEmployee(employee);

        System.out.println("Employee successfully added to system");
    }

    /**
     * gets from user a branch that they want to change its open hours
     * inside this function it calls a helper function that changes a specific day
     * @param branchStore : store to update
     */
    public void updateBranchOpenHours(BranchStore branchStore)
    {
        Scanner scanner = new Scanner(System.in);
        String answer = "y";
        while (Objects.equals(answer, "y"))
        {
            System.out.println("Enter day you want to update (e.g Saturday): ");
            String day = scanner.nextLine();

            System.out.println("Enter which shift you want to update (morning - 0, evening - 1): ");
            int shift = scanner.nextInt();

            System.out.println("Do you want the shift to be open or closed (open - 0, closed - 1): ");
            int availability = scanner.nextInt();

            branchStore.setOpenHours(Days.valueOf(day).ordinal(),shift,availability);

            answer=scanner.nextLine();
            System.out.println("Would you like to change another days open hours? (enter y/n)");
            answer = scanner.nextLine();

        }
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

        System.out.println("Do you want to update the branch's open hours? Now it is 24/7 store (Enter y/n)");
        String answer = scanner.nextLine();
        if(answer.equals("y"))
            updateBranchOpenHours(branchStore);

        System.out.println("Branch successfully added to system, ID number is: "+ branchStore.getBranchID());
    }



    /**
     *
     * @return list of all employees in system
     */
    public List<Employee> getNetworkEmployees()
    {
        return networkEmployees;
    }

    public int getNumOfEmployee(){return networkEmployees.size();}

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
                    for(int i = 0; i< getNumOfEmployee(); i ++)
                    {
                        EmployeeConstraints.askForConstraints(getNetworkEmployees().get(i));
                    }
                    System.out.println("DONE.");
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
        Timer timer = new Timer();
        /*
         * Second function set all branches shifts for one day.
         */
        timer = new Timer();
        TimerTask dailyShift = new TimerTask() {
            @Override
            public void run() {
                List<Employee> listEmployees = new ArrayList<>();
                //for loop that run on the branch list and collect the employees list
                for(int i = 0; i<getNetworkBranches().size(); i++)
                {
                    // get the employees from each branch and set them a new scheduling
                    listEmployees = getNetworkBranches().get(i).getEmployees();
                    DailyShift newShift = ShiftOrganizer.DailyShifts(listEmployees, getNetworkBranches().get(i).getOpenHours()); // Call the function to run every 24 hours
                    getNetworkBranches().get(i).addShiftToHistory(newShift); // add new shift to branch history
                    assert newShift != null;
                    System.out.println("This shift is set for: "+newShift.getDate().toString());
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
        system.schedualingFromEmployees();
        system.setShift();
        //system.getNetworkBranches().get(0).showShiftByDate("2023-03-28");

    }
    /*
     * save cancellations->save in shift manager the details and counter in daily shift
     * save reports
     */

}
