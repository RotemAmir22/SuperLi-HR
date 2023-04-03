package Module_HR;

import java.time.LocalDate;
import java.util.*;


/**
 * This class will help HR user to manage the system:
 * - schedule shifts by the ShiftOrganizer class
 * - ask for employees' constraints by the EmployeeConstraints class
 * We assume there is one user at this point.
 */
public class HR_SystemManagement {

    //variables
    private List<BranchStore> networkBranches;
    private List<Employee> networkEmployees;

    //constructor
    public HR_SystemManagement() {
        this.networkBranches= new ArrayList<>();
        this.networkEmployees= new ArrayList<>();
    }

    /**
     * @return list of all employees in system
     */
    public List<Employee> getNetworkEmployees()
    {
        return networkEmployees;
    }

    /**
     * get number of employees in system
     * @return int
     */
    public int getNumOfEmployee(){return networkEmployees.size();}

    /**
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
     * look for branch using given id
     * @param ID : branch id to find
     * @return if found return branch and if not, null
     */
    public BranchStore findBranchByID(int ID) {

        for (BranchStore branch : networkBranches)
            if (branch.getBranchID()==ID)
                return branch;
        return null;
    }

    /**
     * searches for employee in network
     * @param ID: uses ID to identify employee
     * @return if found returns employee, null if not found
     */
    public Employee findEmployeeByID(String ID) {
        for (Employee employee : networkEmployees)
            if (Objects.equals(employee.getId(), ID))
                return employee;
        return null;
    }

    /**
     *
     * @param e: add employee to list of all networks employees
     */
    public void addEmployeeToList(Employee e){this.networkEmployees.add(e);}

    /**
     * @param employee: gets employee and adds a qualification to the employee
     */
    public void addQualificationToEmployee(Employee employee)
    {
        Scanner scanner = new Scanner(System.in);
        String answer = "y";
        System.out.println("-Add qualification to Employee-");
        //add qualification to employee - only one when it is a new employee
        while (Objects.equals(answer, "y"))
        {
            try{
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
            catch (Exception e)
            {
                System.out.println("Invalid choice. Please try again.");
            }

        }
    }

    /**
     * @param employee: gets employee and removes a role qualification
     */
    public void removeQualificationToEmployee(Employee employee)
    {
        Scanner scanner = new Scanner(System.in);
        String answer = "y";
        System.out.println("-Remove qualification from Employee-");
        //add qualification to employee - only one when it is a new employee
        while (Objects.equals(answer, "y"))
        {
            try{
                System.out.println("Choose from the following, please enter the number of the role");
                Role[] roles = Role.values();
                for (int i = 0; i < roles.length; i++) {
                    System.out.println(i + " - " + roles[i]);
                }
                int qualification = scanner.nextInt();
                employee.removeRole(roles[qualification]);
                answer=scanner.nextLine();
                System.out.println("Would you like to remove another qualification? (Enter y/n): ");
                answer = scanner.nextLine();
            }
            catch (Exception e)
            {
                System.out.println("Invalid choice. Please try again.");
            }

        }
    }

    /**
     * function asks the user what branch to add to
     * @param employee: employee to add
     */
    public void addEmployeeToBranch(Employee employee)
    {
        System.out.println("-Add Employee to Branch-");
        //add employee to branch
        String answer = "y";
        Scanner scanner = new Scanner(System.in);
        while (Objects.equals(answer, "y"))
        {
            try{
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
            catch (Exception e)
            {
                System.out.println("Invalid choice. Please try again.");
            }

        }
    }

    /**
     * remove employee form branch
     * @param employee: employee to remove
     */
    public void removeEmployeeFromBranch(Employee employee)
    {
        System.out.println("-Remove Employee from Branch-");
        //remove employee to branch
        String answer = "y";
        Scanner scanner = new Scanner(System.in);
        while (Objects.equals(answer, "y"))
        {
            try
            {
                System.out.println("Enter branch ID: ");
                int branchNum = scanner.nextInt();

                //find branch in network
                BranchStore branch = findBranchByID(branchNum);
                if(branch== null){
                    System.out.println("ID entered does not exist, please try again: ");
                }
                else {
                    branch.removeEmployee(employee);
                    answer=scanner.nextLine();
                    System.out.println("Do you want to remove the employee from another branch? (enter y/n): ");
                    answer = scanner.nextLine();
                }
            }
            catch (Exception e)
            {
                System.out.println("Invalid choice. Please try again.");
            }

        }
    }

    /**
     * add a new employee to system
     */
    public void newEmployeeInNetwork() {
        System.out.println("-Add Employee to Network-");
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
        System.out.println("-Update Branch opening hours-");
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
        System.out.println("Enter a summery for this branch's opening time");
        answer = scanner.nextLine();
        branchStore.setOpeningTime(answer);

    }
    /**
     * create new branch in system
     */
    public void newBranchInNetwork(){
        System.out.println("-Add Branch to Network-");
        //get from HR manager all the details to create a new employee in the system
        System.out.println("Hello HR manager, to add a new branch please enter the following details:");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter branch's name: ");
        String name = scanner.nextLine();
        System.out.println("Enter branch's address: ");
        String address = scanner.nextLine();
        System.out.println("Enter branch's phone number: ");
        String phone = scanner.nextLine();
        System.out.println("Enter branch's opening time: ");
        String openingtime = scanner.nextLine();
        BranchStore branchStore = new BranchStore(name,address,phone, openingtime);
        addBranchStoreToList(branchStore);

        System.out.println("Do you want to update the branch's open hours? Now it is 24/7 store (Enter y/n)");
        String answer = scanner.nextLine();
        if(answer.equals("y"))
            updateBranchOpenHours(branchStore);

        System.out.println("Branch successfully added to system, ID number is: "+ branchStore.getBranchID());
    }

    /**
     * once a week the system asks all the network employees for their schedule constraints
     */
    public void schedulingFromEmployees()
    {
        /*
         * First function ask all the employees in all branches to give constraints
         */
        System.out.println("TIME TO SCHEDULE CONSTRAINTS \u231B");
        for(int j = 0; j<getNetworkBranches().size(); j ++)
        {
            getNetworkBranches().get(j).printOpenHours();
        }
        for(int i = 0; i< getNumOfEmployee(); i ++)
        {
            EmployeeConstraints.askForConstraints(getNetworkEmployees().get(i));
        }
        System.out.println("DONE.");

    }

    /**
     * daily the system build the shifts for the next week in each branch
     */
    public void setShift()
    {

        for(Days day : Days.values())
        {
            System.out.println("-Set Shift Schedule for "+ LocalDate.now().plusDays(day.ordinal()+2)+" -");
            DailyShift[] newShift = new DailyShift[getNetworkBranches().size()];
            /*
             * Second function set all branches shifts for one day.
             */
            List<Employee> listEmployees;
            for(int i = 0; i<getNetworkBranches().size(); i++)
            {
                System.out.println("Setting shift for Branch No. "+getNetworkBranches().get(i).getBranchID());
                newShift[i] = new DailyShift(LocalDate.now().plusDays(day.ordinal()+2));
                // get the employees from each branch and set them a new scheduling
                listEmployees = getNetworkBranches().get(i).getEmployees();

                //schedule morning shift
                newShift[i] = ShiftOrganizer.DailyShifts(listEmployees, getNetworkBranches().get(i).getOpenHours(), 0, newShift[i],day);
                assert newShift[i] != null;
                System.out.println("This shift is set for: "+newShift[i].getDate().toString()+" in the "+ShiftOrganizer.Shift.Morning);

                //schedule evening shift
                newShift[i] = ShiftOrganizer.DailyShifts(listEmployees, getNetworkBranches().get(i).getOpenHours(), 1, newShift[i],day);
                getNetworkBranches().get(i).addShiftToHistory(newShift[i]); // add new shift to branch history
                assert newShift[i] != null;
                System.out.println("This shift is set for: "+newShift[i].getDate().toString()+" in the "+ShiftOrganizer.Shift.Evening+"\n");
            }

        }

    }

    /**
     * HR manager can change a shift schedule.
     * this function asks all the required information to do so
     */
    public void changeShiftSchedule()
    {
        System.out.println("-Update Shift Schedule-");
        String answer = "y";
        while (Objects.equals(answer, "y"))
        {
            try{
                System.out.println("Hello, please answer the following questions to update a shift:");
                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter Branch Store ID that you want to update ");
                int branchID = scanner.nextInt();

                //find branch
                BranchStore branch = findBranchByID(branchID);
                while (branch == null)
                {
                    System.out.println("Invalid Branch Store ID, please try again ");
                    branchID = scanner.nextInt();
                    branch = findBranchByID(branchID);
                }

                String dateString=scanner.nextLine();
                System.out.println("What date? ");
                dateString=scanner.nextLine();
                LocalDate date = LocalDate.parse(dateString);

                System.out.println("Morning (0) or Evening (1) shift? (enter number)");
                int shift = scanner.nextInt();

                System.out.println("Add (0) or Remove (1) employee? (enter number)");
                int choice = scanner.nextInt();

                String employeeID = scanner.nextLine();
                System.out.println("Enter employees ID");
                employeeID = scanner.nextLine();

                //find employee
                Employee employee = branch.findEmployeeInBranch(employeeID);
                while (employee == null)
                {
                    System.out.println("Invalid Employee ID, please try again ");
                    employeeID = scanner.nextLine();
                    employee = branch.findEmployeeInBranch(employeeID);
                }

                System.out.println("If you are: ");
                System.out.println("- Adding an employee, enter role you want to change to. ");
                System.out.println("- Removing an employee, enter current role. ");
                System.out.println("Enter the number of the role from the following ");
                Role[] roles = Role.values();
                for (int i = 0; i < roles.length; i++) {
                    System.out.println(i + " - " + roles[i]);
                }
                int qualification = scanner.nextInt();
                ShiftOrganizer.changeShift(branch,date , shift, choice, employee, roles[qualification]);
                answer = scanner.nextLine();
                System.out.println("SHIFT UPDATED\n\nDo you wish to update another shift? (enter y/n)");
                answer = scanner.nextLine();
            }
            catch (Exception e)
            {
                System.out.println("Invalid choice. Please try again.");
            }

        }

    }

    /**
     * updates employees constrains by id
     */
    public void updateEmployeeConstrainsByID()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("-Update Employee Constraints-");
        System.out.println("Hello, Enter your ID to update your shift constraints: ");
        String employeeId = scanner.nextLine();
        Employee employee = findEmployeeByID(employeeId);
        EmployeeConstraints.updateConstraints(employee);
        System.out.println("Constraints updated ");
    }

    /**
     * this function reset the employees limits to 6
     */
    public void resetEmployeesLimits()
    {
        LocalDate currentDate = LocalDate.now();
        /* Reset employee's limit of shifts if the week is over */
        if(currentDate.toString().equals("Saturday"))
            for (Employee employee : getNetworkEmployees()){employee.setShiftsLimit(6);}

    }

    /**
     * add permission to Shift Manager for Daily Shift today
     */
    public void addPermissionToShiftManagerForDailyShiftToday()
    {
        System.out.println("-Add permission to Shift Manager of TODAY shift-");
        String answer = "y";
        Scanner scanner = new Scanner(System.in);

        while (Objects.equals(answer, "y"))
        {
            try{
                System.out.println("Enter permission name: ");
                String name =scanner.nextLine();
                System.out.println("Enter permission description");
                String description = scanner.nextLine();

                //create new permission to add to shift manager
                ShiftM_Permissions permission = new ShiftM_Permissions(name,description);

                System.out.println("Enter branch ID: ");
                int branchNum = scanner.nextInt();

                //find branch in network
                BranchStore branch = findBranchByID(branchNum);
                if(branch== null)
                {
                    System.out.println("ID entered does not exist, please try again: ");
                }
                else
                {
                    answer=scanner.nextLine();
                    System.out.println("Enter shift managers ID: ");
                    String ID = scanner.nextLine();
                    Employee employee = branch.findEmployeeInBranch(ID);
                    if(employee != null)
                    {
                        DailyShift dailyShift = branch.getShiftByDate(LocalDate.now().toString());
                        if(dailyShift == null)
                            System.out.println("NO SHIFT YET");
                        else {
                            ShiftManager shiftManager= dailyShift.findEmployeeInShiftManager(ID);
                            if(shiftManager == null)
                            {
                                System.out.println("Shift Manager not found, maybe wrong branch, please try again");
                                continue;
                            }
                            else {
                                shiftManager.addPermission(permission);
                            }
                        }
                    }
                    else {
                        System.out.println("ID not found, maybe wrong branch, please try again");
                        continue;
                    }

                    System.out.println("Do you want to add another permission to a shift manager? (enter y/n): ");
                    answer = scanner.nextLine();
                }
            }
            catch (Exception e)
            {
                System.out.println("Invalid choice. Please try again.");
            }

        }
    }

    /**
     * remove a permission from Shift Manager for Daily Shift today
     */
    public void removePermissionToShiftManagerForDailyShiftToday()
    {
        System.out.println("-Remove permission from Shift Manager of TODAY shift-");
        String answer = "y";
        Scanner scanner = new Scanner(System.in);

        while (Objects.equals(answer, "y"))
        {
            try{
                System.out.println("Enter permission name");
                String name = scanner.nextLine();

                System.out.println("Enter branch ID: ");
                int branchNum = scanner.nextInt();

                //find branch in network
                BranchStore branch = findBranchByID(branchNum);
                if(branch== null)
                {
                    System.out.println("ID entered does not exist, please try again: ");
                }
                else
                {
                    answer=scanner.nextLine();
                    System.out.println("Enter shift managers ID: ");
                    String ID = scanner.nextLine();
                    Employee employee = branch.findEmployeeInBranch(ID);
                    if(employee != null)
                    {
                        DailyShift dailyShift = branch.getShiftByDate(LocalDate.now().toString());
                        if(dailyShift == null)
                            System.out.println("NO SHIFT YET");
                        else {
                            ShiftManager shiftManager= dailyShift.findEmployeeInShiftManager(ID);
                            if(shiftManager == null)
                            {
                                System.out.println("Shift Manager not found, maybe wrong branch, please try again");
                                continue;
                            }
                            else {
                                shiftManager.removePermission(shiftManager.findPermission(name));
                            }
                        }
                    }
                    else {
                        System.out.println("ID not found, maybe wrong branch, please try again");
                        continue;
                    }

                    System.out.println("Do you want to remove another permission from a shift manager? (enter y/n): ");
                    answer = scanner.nextLine();
                }
            }
            catch (Exception e)
            {
                System.out.println("Invalid choice. Please try again.");
            }

        }
    }

    /**
     * gets an employee and asks HR manger what details to update
     * @param employee: employee to update
     */
    public void updateEmployeesDetails(Employee employee)
    {
        int choice = 0;
        Scanner scanner = new Scanner(System.in);
        while (choice != 8) {
            System.out.println("-Update Employees details-");
            System.out.println("1. Bank Account");
            System.out.println("2. Salary");
            System.out.println("3. Employment Terms");
            System.out.println("4. Add Employee detail");
            System.out.println("5. Add qualification");
            System.out.println("6. Remove qualification");
            System.out.println("7. Add Bonus");
            System.out.println("8. Exit");
            try{
                choice = scanner.nextInt();
                String temp = scanner.nextLine();
                switch (choice) {
                    case 1 -> {
                        System.out.println("You chose Option 1.");
                        System.out.println("Enter new bank account number");
                        String bank = scanner.nextLine();
                        employee.setBankAccount(bank);
                    }
                    case 2 -> {
                        System.out.println("You chose Option 2.");
                        System.out.println("Enter new salary");
                        double salary = scanner.nextDouble();
                        employee.setSalary(salary);
                    }
                    case 3 -> {
                        System.out.println("You chose Option 3.");
                        System.out.println("Enter new employment terms");
                        String empTerms = scanner.nextLine();
                        employee.setEmpTerms(empTerms);
                    }
                    case 4 -> System.out.println("You chose Option 4.\n This option is in the works");
                    case 5 -> {
                        System.out.println("You chose Option 5.");
                        addQualificationToEmployee(employee);
                    }
                    case 6 -> {
                        System.out.println("You chose Option 6.");
                        removeQualificationToEmployee(employee);
                    }
                    case 7 -> {
                        System.out.println("You chose Option 7.");
                        System.out.println("How much do you wish to add as a bonus?");
                        double bonus = scanner.nextDouble();
                        employee.setCumulativeSalary(employee.getCumulativeSalary() + bonus);
                        System.out.println("Employees cumulative salary now is: " + employee.getCumulativeSalary());
                    }
                    case 8 -> System.out.println("Existing menu....");
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            }
            catch (Exception e)
            {
                System.out.println("Invalid choice. Please try again.");
            }

        }
    }

    /**
     * gets an employee and asks HR manger what details to update
     * @param employee: employee to present
     */
    public void getEmployeesDetails(Employee employee)
    {
        int choice = 0;
        Scanner scanner = new Scanner(System.in);
        while (choice != 8) {
            System.out.println("-Get Employees details-");
            System.out.println("1. Bank Account");
            System.out.println("2. Salary");
            System.out.println("3. Employment Terms");
            System.out.println("4. Employee detail");
            System.out.println("5. Qualifications");
            System.out.println("6. Add Bonus");
            System.out.println("7. Start Date");
            System.out.println("8. Exit");
            try{
                choice = scanner.nextInt();
                String temp = scanner.nextLine();
                switch (choice) {
                    case 1:
                        System.out.println("You chose Option 1.");
                        System.out.println(employee.getName()+" bank account is: "+employee.getBankAccount());
                        break;

                    case 2:
                        System.out.println("You chose Option 2.");
                        System.out.println(employee.getName()+" salary is: "+employee.getSalary());
                        break;
                    case 3:
                        System.out.println("You chose Option 3.");
                        System.out.println(employee.getName()+" employment terms are:\n "+employee.getSalary());
                        break;
                    case 4:
                        System.out.println("You chose Option 4.\n This option is in the works");
                        break;
                    case 5:
                        System.out.println("You chose Option 5.");
                        System.out.println(employee.getName()+" qualifications are:\n "+employee.getQualifications().toString());
                        break;
                    case 6:
                        System.out.println("You chose Option 6.");
                        System.out.println(employee.getName()+" cumulative salary is:\n "+employee.getCumulativeSalary());
                        break;
                    case 7:
                        System.out.println("You chose Option 7.");
                        System.out.println(employee.getName()+" start date was:\n "+employee.getStartDate());
                        break;
                    case 8:
                        System.out.println("Existing menu....");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }

            }
            catch (Exception e)
            {
                System.out.println("Invalid choice. Please try again.");
            }

        }
    }

    /**
     * update branch information
     * @param branch : branch to get details from
     */
    public void updateBranchDetails(BranchStore branch)
    {
        int choice = 0;
        Scanner scanner = new Scanner(System.in);
        while (choice != 4) {
            try{
                System.out.println("-Update Branch details-");
                System.out.println("1. Name");
                System.out.println("2. Phone number");
                System.out.println("3. Open Hours");
                System.out.println("4. Exit");

                choice = scanner.nextInt();
                String temp = scanner.nextLine();
                switch (choice) {
                    case 1:
                        System.out.println("You chose Option 1.");
                        System.out.println("Enter new branch name");
                        String name = scanner.nextLine();
                        branch.setName(name);
                        break;

                    case 2:
                        System.out.println("You chose Option 2.");
                        System.out.println("Enter new phone number");
                        String phoneNum = scanner.nextLine();
                        branch.setPhoneNum(phoneNum);
                        break;
                    case 3:
                        System.out.println("You chose Option 3.");
                        updateBranchOpenHours(branch);
                        break;
                    case 4:
                        System.out.println("Existing menu....");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            }
            catch (Exception e)
            {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

}
