package Module_HR;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {


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

        int choice = 0;
        Scanner scanner = new Scanner(System.in);
        while (choice != 6) {
            System.out.println("Hello HR manager. Welcome to Super-li system:");
            System.out.println("1. Employees");
            System.out.println("2. Branches");
            System.out.println("3. Constraints");
            System.out.println("4. Shifts");
            System.out.println("5. History");
            System.out.println("6. Exit");

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
                            //system.calculateSalary(searchAnEmployee(system));
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
                            System.out.println("Enter the required date:");
                            String ans = scanner.nextLine();
                            b.getShiftByDate(ans).showMeSchedualing();
                            break;
                        case 2:
                            searchABranchStore(system).deleteHistory();
                            break;
                    }

                    break;
                case 6:
                    System.out.println("Exiting menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }

    }

}
