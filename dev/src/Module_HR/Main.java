package Module_HR;

import java.io.File;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {

    /**
     * TODO add 20 employees and 3 branches to system
     * @param system upload data
     */
    public static void uploadData(HR_SystemManagement system)
    {

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


    public static void main(String[] args){

        /* The main object "HR" control */
        HR_SystemManagement system = new HR_SystemManagement();
        Scanner scanner = new Scanner(System.in);
        System.out.println("HELLO. Do you wish to upload the system with/without data? enter (0/1)");
        int data = scanner.nextInt();
        if(data == 0)
        {
            uploadData(system);
        }

        int choice = 0;
        while (choice != 7) {
            System.out.println("Hello HR manager. Welcome to Super-li system:");
            System.out.println("1. Employees");
            System.out.println("2. Branches");
            System.out.println("3. Constraints");
            System.out.println("4. Shifts");
            System.out.println("5. History");
            System.out.println("6. ManageShift - TEMPORARY MAIN");
            System.out.println("7. Exit");

            choice = scanner.nextInt();
            int c = 0;
            int id;
            switch (choice) {
                case 1:
                    System.out.println("You chose Employees:");
                    System.out.println("1. Add new employee");
                    System.out.println("2. Update an existing employee");
                    System.out.println("3. Get employees information");
                    System.out.println("4. Calculate salary for last month");
                    System.out.println("5. Go Back");
                    c = scanner.nextInt();
                    switch (c)
                    {
                        case 1:
                            system.newEmployeeInNetwork();
                            break;
                        case 2:
                            system.updateEmployeesDetails(searchAnEmployee(system));
                            break;
                        case 3:
                            system.getEmployeesDetails(searchAnEmployee(system));
                            break;
                        case 4:
                            System.out.println(searchAnEmployee(system).getCumulativeSalary());
                            break;
                        case 5:
                            continue;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                            break;
                    }
                    break;
                case 2:
                    System.out.println("You chose Branches:");
                    System.out.println("1. Add new branch");
                    System.out.println("2. Update an existing branch");
                    System.out.println("3. Add employee to branch");
                    System.out.println("4. Remove employee from branch");
                    System.out.println("5. Go Back");
                    c = scanner.nextInt();
                    switch (c)
                    {
                        case 1:
                            system.newBranchInNetwork();
                            break;
                        case 2:
                            system.updateBranchDetails(searchABranchStore(system));
                        case 3:
                            system.addEmployeeToBranch(searchAnEmployee(system));
                            break;
                        case 4:
                            system.removeEmployeeFromBranch(searchAnEmployee(system));
                            break;
                        case 5:
                            continue;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                            break;
                    }

                    break;
                case 3:
                    System.out.println("You chose Constraints:");
                    System.out.println("1. Ask constraints from all employees");
                    System.out.println("2. Update constraints to an employee");
                    System.out.println("3. Go Back");
                    c = scanner.nextInt();
                    switch (c)
                    {
                        case 1:
                            system.schedulingFromEmployees();
                            break;
                        case 2:
                            system.updateEmployeeConstrainsByID();
                            break;
                        case 3:
                            continue;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                            break;
                    }
                    break;
                case 4:
                    System.out.println("You chose Shifts:");
                    System.out.println("1. Plan shifts for tomorrow");
                    System.out.println("2. Change shift");
                    System.out.println("3. Add shift managers permissions");
                    System.out.println("4. Remove shift managers permissions");
                    System.out.println("5. Reset employees limit for next week");
                    System.out.println("6. Go Back");
                    c = scanner.nextInt();
                    switch (c) {
                        case 1:
                            system.setShift();
                            break;
                        case 2:
                            system.changeShiftSchedule();
                            break;
                        case 3:
                            system.addPermissionToShiftManagerForDailyShiftToday();
                            break;
                        case 4:
                            system.removePermissionToShiftManagerForDailyShiftToday();
                            break;
                        case 5:
                            system.resetEmployeesLimits();
                            break;
                        case 6:
                            continue;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                            break;
                    }
                    break;
                case 5:
                    System.out.println("You chose History:");
                    System.out.println("1. Get shift by a date");
                    System.out.println("2. Clear last moth history");
                    c = scanner.nextInt();
                    switch (c) {

                        case 1:
                            BranchStore b = searchABranchStore(system);
                            scanner.nextLine();
                            System.out.println("Enter the required date:");
                            String ans = scanner.nextLine();
                            b.getShiftByDate(ans).showMeSchedualing();
                            break;
                        case 2:
                            for( BranchStore branch: system.getNetworkBranches()){branch.deleteHistory();}
                            for( Employee e: system.getNetworkEmployees()){e.setCumulativeSalary(0);}
                            System.out.println("All history is reset.\nAll employees cumulative salary is reset to 0");
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                            break;
                    }

                    break;
                case 6:
                    BranchStore branch_ = searchABranchStore(system);
                    DailyShift s = branch_.getShiftByDate(LocalDate.now().toString());
                    System.out.println("Enter an employee ID");
                    String ans = scanner.nextLine();
                    ShiftManager shiftm = s.findEmployeeInShiftManager(ans);
                    ManageShift manageShift = new ManageShift(shiftm, s, LocalDate.now());
                    System.out.println("Choose an option:");
                    System.out.println("1. Cancel an item");
                    System.out.println("2. Upload end-of-day report");
                    System.out.println("3. Get end-of-day report");
                    c = scanner.nextInt();
                    switch (c) {
                        case 1:
                            manageShift.cancelItem();
                            break;
                        case 2:
                            manageShift.uploadEndofDayReport();
                            break;
                        case 3:
                            File file = s.getEndOfDayReport();
                            System.out.println(file);
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                            break;
                    }

                        case 7:
                    System.out.println("Exiting menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }

    }

}
