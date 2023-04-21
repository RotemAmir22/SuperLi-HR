package Module_HR;

import DataAccess.DAO_BranchStore;
import DataAccess.DAO_Employee;
import DataAccess.DAO_Generator;

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
    private DAO_Employee employeesDAO;
    private DAO_BranchStore branchStoreDAO;

    //constructor
    public HR_SystemManagement() {
        employeesDAO = DAO_Generator.getEmployeeDAO();
        branchStoreDAO = DAO_Generator.getBranchStoreDAO();
    }


    /**
     * @param employee: gets employee and adds a qualification to the employee
     */
    public void addQualificationToEmployee(Employee employee)
    {
        Scanner scanner = new Scanner(System.in);
        String answer = "y";
        boolean change = false;
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
                if(!employee.getQualifications().contains(roles[qualification])){
                    employee.addRole(roles[qualification]);
                    change = true;
                }

                else
                    System.out.println("This employee is already a " + roles[qualification].toString());
                answer=scanner.nextLine();
                System.out.println("Would you like to add more qualifications? (Enter y/n): ");
                answer = scanner.nextLine();
            }
            catch (Exception e)
            {
                System.out.println("Invalid choice. Please try again.");
            }

        }
        if(change){employeesDAO.update(employee);} // update DB
    }

    /**
     * @param employee: gets employee and removes a role qualification
     */
    public void removeQualificationToEmployee(Employee employee)
    {
        Scanner scanner = new Scanner(System.in);
        String answer = "y";
        boolean change = false;
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
                if(employee.getQualifications().contains(roles[qualification])){
                    employee.removeRole(roles[qualification]);
                    change = true;
                }

                else
                    System.out.println("This employee isn't a " + roles[qualification].toString());
                answer=scanner.nextLine();
                System.out.println("Would you like to remove another qualification? (Enter y/n): ");
                answer = scanner.nextLine();
            }
            catch (Exception e)
            {
                System.out.println("Invalid choice. Please try again.");
            }

        }
        if(change){employeesDAO.update(employee);}
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
                BranchStore branch = branchStoreDAO.findBranchByID(branchNum); // check in DAO
                if(branch== null){
                    System.out.println("ID entered does not exist, please try again: ");
                }
                else {
                    branch.addEmployee(employee);
                    branchStoreDAO.update(branch); // connect to DB
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
                BranchStore branch = branchStoreDAO.findBranchByID(branchNum);
                if(branch== null){
                    System.out.println("ID entered does not exist, please try again: ");
                }
                else {
                    branch.removeEmployee(employee);
                    branchStoreDAO.update(branch); // connect to DB
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
        String startDate;
        while(true){
            System.out.println("Enter employee's start Date (enter as yyyy-MM-dd): ");
            try {
                startDate = scanner.nextLine();
                LocalDate.parse(startDate);
                break;
            }
            catch (Exception e)
            {
                System.out.println("Invalid format");
            }
        }
        System.out.println("Enter employee's First name: ");
        String first = scanner.nextLine();
        System.out.println("Enter employee's Last name: ");
        String last = scanner.nextLine();
        System.out.println("Enter employee's ID: ");
        String id = scanner.nextLine();
        System.out.println("Enter employee's bank account: ");
        String bankAccount = scanner.nextLine();
        String tmp;
        double salary;
        while(true){
            System.out.println("Enter employee's salary: ");
            try {
                tmp = scanner.nextLine();
                salary = Double.parseDouble(tmp);
                break;
            }
            catch (Exception e)
            {
                System.out.println("Invalid input. Enter a number please");
            }
        }
        System.out.println("Enter employee's terms of employment: ");
        String filePath = "";

        //create an employee generator
        EmployeeGenerator employeeGenerator = new EmployeeGenerator();
        //create new employee
        Employee employee = employeeGenerator.CreateEmployee(first,last,id,bankAccount,salary,filePath,startDate);
        //add employee to list of all employees in network and update the DB
        employeesDAO.insert(employee);
        //add employee to branch and update the DB
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
        branchStoreDAO.update(branchStore); // connect to DB

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
        BranchStore branchStore = new BranchStore(name,address,phone, "24/7");

        branchStoreDAO.insert(branchStore);

        System.out.println("Please update the open hours in according to the opening time for scheduling purposes\nthe default is that the branch store is open 24/7");
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
        for(int j = 0; j<branchStoreDAO.getNetworkBranches().size(); j ++) // note that it's not all
        {
            branchStoreDAO.getNetworkBranches().get(j).printOpenHours();
        }
        for(int i = 0; i< employeesDAO.getNetworkEmployees().size(); i ++) // note that it's not all
        {
            EmployeeConstraints.askForConstraints(employeesDAO.getNetworkEmployees().get(i));
            Employee e = employeesDAO.getNetworkEmployees().get(i);
            employeesDAO.update(e); // update DB
        }
        System.out.println("DONE.");

    }

    /**
     * daily the system build the shifts for the next week in each branch
     */
    public void setShift()
    {

        for(int j=0; j<2 ; j++)
        {
            Days day = Days.values()[j];
            System.out.println("\n- Set Shift Schedule for "+ LocalDate.now().plusDays(day.ordinal()+2)+" -");
            DailyShift[] newShift = new DailyShift[branchStoreDAO.getNetworkBranches().size()];
            /*
             * Second function set all branches shifts for one day.
             */
            List<Employee> listEmployees;
            for(int i = 0; i<branchStoreDAO.getNetworkBranches().size(); i++) // note that it's not all
            {
                System.out.println("\nSetting shift for Branch No. "+branchStoreDAO.getNetworkBranches().get(i).getBranchID());
                newShift[i] = new DailyShift(LocalDate.now().plusDays(day.ordinal()+2));
                // get the employees from each branch and set them a new scheduling
                listEmployees = branchStoreDAO.getNetworkBranches().get(i).getEmployees();

                //schedule morning shift
                newShift[i] = ShiftOrganizer.DailyShifts(listEmployees, branchStoreDAO.getNetworkBranches().get(i).getOpenHours(), 0, newShift[i],day);
                assert newShift[i] != null;
                System.out.println("This shift is set for: "+newShift[i].getDate().toString()+" in the "+ShiftOrganizer.Shift.Morning);

                //schedule evening shift
                newShift[i] = ShiftOrganizer.DailyShifts(listEmployees, branchStoreDAO.getNetworkBranches().get(i).getOpenHours(), 1, newShift[i],day);
                branchStoreDAO.getNetworkBranches().get(i).addShiftToHistory(newShift[i]); // add new shift to branch history
                branchStoreDAO.update(branchStoreDAO.getNetworkBranches().get(i));
                assert newShift[i] != null;
                System.out.println("This shift is set for: "+newShift[i].getDate().toString()+" in the "+ShiftOrganizer.Shift.Evening+"\n");

                newShift[i].showMeSchedualing();
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
                BranchStore branch = branchStoreDAO.findBranchByID(branchID);
                while (branch == null)
                {
                    System.out.println("Invalid Branch Store ID, please try again ");
                    branchID = scanner.nextInt();
                    branch = branchStoreDAO.findBranchByID(branchID);
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
        while (true)
        {
            System.out.println("Hello, Enter your ID to update your shift constraints: ");
            String employeeId = scanner.nextLine();
            Employee employee = employeesDAO.findEmployeeByID(employeeId);
            if (employee == null){
                System.out.println("Invalid ID.");
                continue;
            }
            EmployeeConstraints.updateConstraints(employee);
            System.out.println("Constraints updated ");
            break;
        }

    }

    /**
     * this function reset the employees limits to 6
     */
    public void resetEmployeesLimits()
    {
        LocalDate currentDate = LocalDate.now();
        /* Reset employee's limit of shifts if the week is over */
        if(currentDate.toString().equals("Saturday"))
            for (Employee employee : employeesDAO.getNetworkEmployees()){
                employee.setShiftsLimit(6);
                employeesDAO.update(employee);
            }
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
                BranchStore branch = branchStoreDAO.findBranchByID(branchNum);
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
                        DailyShift dailyShift = branch.getShiftByDate(LocalDate.now().plusDays(2).toString());
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
                branchStoreDAO.update(branch); // connect to DB
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
                BranchStore branch = branchStoreDAO.findBranchByID(branchNum);
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
                        DailyShift dailyShift = branch.getShiftByDate(LocalDate.now().plusDays(2).toString());
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
                branchStoreDAO.update(branch); // connect to DB
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
                    case 1:
                        System.out.println("You chose Option 1.");
                        System.out.println("Enter new bank account number");
                        String bank = scanner.nextLine();
                        employee.setBankAccount(bank);
                        break;

                    case 2:
                        System.out.println("You chose Option 2.");
                        while (true){
                            System.out.println("Enter new salary");
                            try{
                                String salary = scanner.nextLine();
                                employee.setSalary(Double.parseDouble(salary));
                                break;
                            }
                            catch (Exception e){
                                System.out.println("Invalid input. Please enter a number");
                            }
                        }
                        break;

                    case 3:
                        System.out.println("You chose Option 3.");
                        System.out.println("Enter new employment terms");
                        String empTerms = scanner.nextLine();
                        employee.setEmpTerms(empTerms);
                        break;

                    case 4:
                        System.out.println("You chose Option 4.\n This option is in the works");
                        break;
                    case 5:
                        System.out.println("You chose Option 5.");
                        addQualificationToEmployee(employee);
                        break;

                    case 6:
                        System.out.println("You chose Option 6.");
                        removeQualificationToEmployee(employee);
                        break;

                    case 7 :
                        System.out.println("You chose Option 7.");
                        while (true){
                            System.out.println("How much do you wish to add as a bonus?");
                            try{
                                String bonus = scanner.nextLine();
                                employee.setCumulativeSalary(employee.getCumulativeSalary() + Double.parseDouble(bonus));
                                System.out.println("Employees cumulative salary now is: " + employee.getCumulativeSalary());
                                break;
                            }
                            catch (Exception e){
                                System.out.println("Invalid input. Please enter a number");
                            }
                        }
                        break;
                    case 8:
                        System.out.println("Existing menu....");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
                employeesDAO.update(employee);
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
            System.out.println("6. Cumulative Salary");
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
                branchStoreDAO.update(branch);
            }
            catch (Exception e)
            {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * print all employees cumulative salary
     */
    public void calculateSalary()
    {
        System.out.println("- Cumulative Salary -");

        for (Employee employee: employeesDAO.getNetworkEmployees())
            System.out.println(employee.getName()+ ": "+ employee.getCumulativeSalary());

        System.out.println("Do you want to nullify employees cumulative salary? (y/n)");
        Scanner scanner = new Scanner(System.in);
        String ans = scanner.nextLine();
        if(Objects.equals(ans, "y"))
            for (Employee employee: employeesDAO.getNetworkEmployees()){
                employee.setCumulativeSalary(0);
                employeesDAO.update(employee);
            }


        System.out.println("Complete");
    }

}
