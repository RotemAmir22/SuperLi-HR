package Module_HR;

import java.io.File;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Scanner;

/**
 * this class is the main user interface
 */
public class Main {

    /**
     * upload data of network into system
     * @param system upload data
     */
    public static void uploadData(HR_SystemManagement system)
    {
        //employees
        Employee e1 = new Employee("Yoni","Cohen","111111","923-456120",600,"Salary per shift - 600","2022-11-13");
        Employee e2 = new Employee("Efrat","Gosh","111112","908-257197",750,"Salary per shift - 750","2022-12-13");
        Employee e3 = new Employee("Ninet","Tayeb","111113","786-352124",700,"Salary per shift - 700","2022-11-22");
        Employee e4 = new Employee("Roza","Li","222222","012-453240",600,"Salary per shift - 600","2022-11-13");
        Employee e5 = new Employee("Natan","Goshen","222221","123-873120",550,"Salary per shift - 550","2022-11-13");
        Employee e6 = new Employee("Arik","Ainshtein","076333","432-356187",760,"Salary per shift - 760","2022-11-13");
        Employee e7 = new Employee("Or","Peretz","092354","025-823321",900,"Salary per shift - 900","2022-11-13");
        Employee e8 = new Employee("Whitney","Uston","035281","497-423920",650,"Salary per shift - 650","2022-11-13");
        Employee e9 = new Employee("Erez","Tal","798321","082-623120",450,"Salary per shift - 450","2022-11-13");
        Employee e10 = new Employee("Assi","Cohen","0176234","384-623430",450,"Salary per shift - 450","2022-11-13");
        Employee e11 = new Employee("Tzvika","Pik","666666","213-732120",450,"Salary per shift - 450","2022-11-13");
        Employee e12 = new Employee("Rotem","Sela","777777","098-456523",530,"Salary per shift - 530","2022-11-13");
        Employee e13 = new Employee("Noa","Kirel","222333","146-723120",760,"Salary per shift - 760","2022-11-13");
        Employee e14 = new Employee("David","Bowie","666555","763-452120",430,"Salary per shift - 430","2022-11-13");
        Employee e15 = new Employee("Joni","Depp","121212","910-454120",700,"Salary per shift - 700","2022-11-13");

        //branches
        BranchStore b1 = new BranchStore("Super-Li","Tel-Aviv","08-6543210","24/7");
        //add employees to branch
        b1.addEmployee(e1);
        b1.addEmployee(e2);
        b1.addEmployee(e3);
        b1.addEmployee(e4);
        b1.addEmployee(e7);
        b1.addEmployee(e8);
        b1.addEmployee(e9);
        b1.addEmployee(e10);

        BranchStore b2 = new BranchStore("Super-Li","Jaffa","08-6442280","All week 10:00-22:00 except Saturday");
        //add employees to branch
        b2.addEmployee(e2);
        b2.addEmployee(e4);
        b2.addEmployee(e5);
        b2.addEmployee(e6);

        BranchStore b3 = new BranchStore("Super-Li","Haifa","08-6573452","Sunday, Tuesday and Thursday 10:00-16:00");
        //add employees to branch
        b3.addEmployee(e11);
        b3.addEmployee(e12);
        b3.addEmployee(e13);
        b3.addEmployee(e14);
        b3.addEmployee(e15);

        //add roles to employees
        e1.addRole(Role.SHIFTMANAGER);
        e1.addRole(Role.CASHIER);
        e2.addRole(Role.GENERAL);
        e2.addRole(Role.SECURITY);
        e3.addRole(Role.GENERAL);
        e3.addRole(Role.MAID);
        e4.addRole(Role.SHIFTMANAGER);
        e4.addRole(Role.STOCK);
        e5.addRole(Role.SHIFTMANAGER);
        e5.addRole(Role.STORAGE);
        e6.addRole(Role.SHIFTMANAGER);
        e6.addRole(Role.CASHIER);
        e7.addRole(Role.SHIFTMANAGER);
        e8.addRole(Role.SHIFTMANAGER);
        e8.addRole(Role.SECURITY);
        e9.addRole(Role.CASHIER);
        e9.addRole(Role.STOCK);
        e10.addRole(Role.GENERAL);
        e10.addRole(Role.CASHIER);
        e11.addRole(Role.STOCK);
        e11.addRole(Role.SECURITY);
        e12.addRole(Role.CASHIER);
        e12.addRole(Role.SHIFTMANAGER);
        e12.addRole(Role.STOCK);
        e13.addRole(Role.SECURITY);
        e14.addRole(Role.MAID);
        e15.addRole(Role.GENERAL);

        //add to system
        system.addEmployeeToList(e1);
        system.addEmployeeToList(e2);
        system.addEmployeeToList(e3);
        system.addEmployeeToList(e4);
        system.addEmployeeToList(e5);
        system.addEmployeeToList(e6);
        system.addEmployeeToList(e7);
        system.addEmployeeToList(e8);
        system.addEmployeeToList(e9);
        system.addEmployeeToList(e10);
        system.addEmployeeToList(e11);
        system.addEmployeeToList(e12);
        system.addEmployeeToList(e13);
        system.addEmployeeToList(e14);
        system.addEmployeeToList(e15);

        system.addBranchStoreToList(b1);
        system.addBranchStoreToList(b2);
        system.addBranchStoreToList(b3);


    }

    /**
     * Search an employee by id
     * @param system user
     * @return the required employee
     */
    public static Employee searchAnEmployee(HR_SystemManagement system)
    {
        Scanner scanner = new Scanner(System.in);
        while(true)
        {
            System.out.println("Enter the employees ID please");
            String id = scanner.nextLine();
            Employee e = system.findEmployeeByID(id);
            if(e == null)
                System.out.println("Invalid ID. Please try again");
            else
                return e;
        }
    }

    /**
     * Search a branch by id
     * @param system user
     * @return the required branch
     */
    public static BranchStore searchABranchStore(HR_SystemManagement system)
    {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("Enter the Branch ID please");
            int id = scanner.nextInt();
            BranchStore b = system.findBranchByID(id);
            if (b == null)
                System.out.println("Invalid ID. Please try again");
            else
                return b;
        }
    }

    /**
     * Main
     */
    public static void main(String[] args){

        /* The main object "HR" control */
        HR_SystemManagement system = new HR_SystemManagement();
        Scanner scanner = new Scanner(System.in);
        System.out.println("HELLO. Do you wish to upload the system with/without data? enter (0/1)");
        String data = scanner.nextLine();
        if(Objects.equals(data, "0"))
        {
            uploadData(system);
            for (BranchStore branchStore : system.getNetworkBranches())
            {
                branchStore.printBranchDetails();
                System.out.println("");
            }
        }

        //MENU
        String choice = "";
        while (choice != "7") {
            System.out.println("Hello HR manager. Welcome to Super-li system:");
            System.out.println("1. Employees");
            System.out.println("2. Branches");
            System.out.println("3. Constraints");
            System.out.println("4. Shifts");
            System.out.println("5. History");
            System.out.println("6. ManageShift - TEMPORARY MAIN");
            System.out.println("7. Exit");

            choice = scanner.nextLine();
            String c = "";
            int id;
            switch (choice) {
                case "1":
                    System.out.println("You chose Employees:");
                    System.out.println("1. Add new employee");
                    System.out.println("2. Update an existing employee");
                    System.out.println("3. Get employees information");
                    System.out.println("4. Calculate salary");
                    System.out.println("5. Print all network Employees");
                    System.out.println("6. Go Back");
                    c = scanner.nextLine();
                    switch (c)
                    {
                        case "1":
                            system.newEmployeeInNetwork();
                            break;
                        case "2":
                            system.updateEmployeesDetails(searchAnEmployee(system));
                            break;
                        case "3":
                            system.getEmployeesDetails(searchAnEmployee(system));
                            break;
                        case "4":
                            system.calculateSalary();
                            break;
                        case "5":
                            for (Employee employee : system.getNetworkEmployees())
                            {
                                employee.printEmployeeDetails();
                                System.out.println("");
                            }
                        case "6":
                            continue;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                            break;
                    }
                    break;
                case "2":
                    System.out.println("You chose Branches:");
                    System.out.println("1. Add new branch");
                    System.out.println("2. Update an existing branch");
                    System.out.println("3. Add employee to branch");
                    System.out.println("4. Remove employee from branch");
                    System.out.println("5. Print all Branches");
                    System.out.println("6. Go Back");
                    c = scanner.nextLine();
                    switch (c)
                    {
                        case "1":
                            system.newBranchInNetwork();
                            break;
                        case "2":
                            system.updateBranchDetails(searchABranchStore(system));
                        case "3":
                            system.addEmployeeToBranch(searchAnEmployee(system));
                            break;
                        case "4":
                            system.removeEmployeeFromBranch(searchAnEmployee(system));
                            break;
                        case "5":
                            for (BranchStore branchStore : system.getNetworkBranches())
                            {
                                branchStore.printBranchDetails();
                                System.out.println("");
                            }
                        case "6":
                            continue;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                            break;
                    }

                    break;
                case "3":
                    System.out.println("You chose Constraints:");
                    System.out.println("1. Ask constraints from all employees");
                    System.out.println("2. Update constraints to an employee");
                    System.out.println("3. Get Employees constraints");
                    System.out.println("4. Go Back");
                    c = scanner.nextLine();
                    switch (c)
                    {
                        case "1":
                            system.schedulingFromEmployees();
                            break;
                        case "2":
                            system.updateEmployeeConstrainsByID();
                            break;
                        case "3":
                            Employee employee = searchAnEmployee(system);
                            System.out.println(employee.getName()+ "constraints are: ");
                            employee.printEmployeesConstraints();

                        case "4":
                            continue;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                            break;
                    }
                    break;
                case "4":
                    System.out.println("You chose Shifts:");
                    System.out.println("1. Plan shifts for the day after tomorrow (Two day schedule)");
                    System.out.println("2. Change shift");
                    System.out.println("3. Add shift managers permissions");
                    System.out.println("4. Remove shift managers permissions");
                    System.out.println("5. Reset employees limit for next week");
                    System.out.println("6. Go Back");
                    c = scanner.nextLine();
                    switch (c) {
                        case "1":
                            system.setShift();
                            break;
                        case "2":
                            system.changeShiftSchedule();
                            break;
                        case "3":
                            system.addPermissionToShiftManagerForDailyShiftToday();
                            break;
                        case "4":
                            system.removePermissionToShiftManagerForDailyShiftToday();
                            break;
                        case "5":
                            system.resetEmployeesLimits();
                            break;
                        case "6":
                            continue;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                            break;
                    }
                    break;
                case "5":
                    System.out.println("You chose History:");
                    System.out.println("1. Get shift by a date");
                    System.out.println("2. Clear last moth history");
                    c = scanner.nextLine();
                    switch (c) {

                        case "1":
                            BranchStore b = searchABranchStore(system);
                            scanner.nextLine();
                            while (true)
                            {
                                try
                                {
                                    System.out.println("Enter the required date (YYYY-MM-DD):");
                                    String ans = scanner.nextLine();
                                    b.showShiftByDate(ans);
                                    break;
                                }
                                catch (Exception e)
                                {
                                    System.out.println("Wrong format or date");
                                }
                            }
                            break;
                        case "2":
                            for( BranchStore branch: system.getNetworkBranches()){branch.deleteHistory();}
                            System.out.println("All history is reset.");
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                            break;
                    }

                    break;
                case "6":
                    BranchStore branch_ = searchABranchStore(system);
                    DailyShift s = branch_.getShiftByDate(LocalDate.now().plusDays(2).toString());
                    scanner.nextLine();
                    if(s == null)
                        System.out.println("NO SHIFT YET");
                    else
                    {
                        System.out.println("Enter an employee ID");
                        String ans = scanner.nextLine();
                        ShiftManager shiftm = s.findEmployeeInShiftManager(ans);
                        ManageShift manageShift = new ManageShift(shiftm, s, LocalDate.now().plusDays(2));
                        System.out.println("Choose an option:");
                        System.out.println("1. Cancel an item");
                        System.out.println("2. Get Cancellation details");
                        System.out.println("3. Upload end-of-day report");
                        System.out.println("4. Get end-of-day report");
                        c = scanner.nextLine();
                        switch (c) {
                            case "1":
                                manageShift.cancelItem();
                                break;
                            case "2":
                                manageShift.getCancellation();
                                break;
                            case "3":
                                manageShift.uploadEndofDayReport();
                                break;
                            case "4":
                                File file = s.getEndOfDayReport();
                                System.out.println(file);
                                break;
                            default:
                                System.out.println("Invalid choice. Please try again.");
                                break;
                        }
                    }
                    break;
                case "7":
                    System.out.println("Exiting menu...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }

    }

}
