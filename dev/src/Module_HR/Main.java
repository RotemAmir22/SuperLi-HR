package Module_HR;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {

    public static void main(String[] args){

        /* The main object "HR" control */
        HR_SystemManagement system = new HR_SystemManagement();

        int choice = 0;
        Scanner scanner = new Scanner(System.in);
        while (choice != 4) {
            System.out.println("Hello HR manager. Welcome to Super-li system. Pickup a choice:\n");
            System.out.println("1. Employees");
            System.out.println("2. Branches");
            System.out.println("3. Constraints");
            System.out.println("4. Shifts");
            System.out.println("5. History");
            System.out.println("6. Exit");

            choice = scanner.nextInt();
            int c = 0;
            String id;
            switch (choice) {
                case 1:
                    System.out.println("You choose Employees\nPickup a choice:\n");
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
                            while(true)
                            {
                                System.out.println("Enter the employees ID please");
                                id = scanner.nextLine();
                                Employee e = system.findEmployeeByID(id);
                                if(e == null)
                                    System.out.println("Invalid ID. Please try again");
                                else{
                                    system.updateEmployeeDetails(e);
                                    break;
                                }
                            }
                            break;
                        case 3:
                            //
                            break;
                        case 4:
                            while(true)
                            {
                                System.out.println("Enter the employees ID please");
                                id = scanner.nextLine();
                                Employee e = system.findEmployeeByID(id);
                                if(e == null)
                                    System.out.println("Invalid ID. Please try again");
                                else{
                                    system.calculateSalary(e);
                                    break;
                                }
                            }
                            break;
                        case 5:
                            continue;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                            break;
                    }
                    break;
                case 2:
                    System.out.println("You choose Branches\nPickup a choice:\n");
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
                            //system.setBranchByID
                        case 3:
                            while(true)
                            {
                                System.out.println("Enter the employees ID please");
                                id = scanner.nextLine();
                                Employee e = system.findEmployeeByID(id);
                                if(e == null)
                                    System.out.println("Invalid ID. Please try again");
                                else{
                                    system.addEmployeeToBranch(e);
                                    break;
                                }
                            }
                            break;
                        case 4:
                            while(true)
                            {
                                System.out.println("Enter the employees ID please");
                                id = scanner.nextLine();
                                Employee e = system.findEmployeeByID(id);
                                if(e == null)
                                    System.out.println("Invalid ID. Please try again");
                                else{
                                    system.removeEmployeeFromBranch(e);
                                    break;
                                }
                            }
                            break;
                        case 5:
                            continue;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                            break;
                    }

                    break;
                case 3:
                    System.out.println("You choose Constraints\nPickup a choice:");
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
                    System.out.println("You chose Shifts\nPickup a choice:\n");
                    System.out.println("1. Plan shifts for tomorrow");
                    System.out.println("2. Change shift");
                    System.out.println("3. Set (add/remove) shift managers permissions");
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

                    }
                    break;
                case 5:
                    System.out.println("Exiting menu...");
                    break;
                case 6:
                    System.out.println("Exiting menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }


        // - get shift history by a date
        // - clear last moth history


        // 1.2 UPDATE (employees)
        // - add more info about an employee
        // - update terms of employee
        // - ask employees bank account
        // - add qualification to an employee
        // - change employee salary (bonus)





    }
}
