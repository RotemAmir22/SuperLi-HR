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
            System.out.println("Hello HR manage. Welcome to Super-li system. Pickup a choice:\n");
            System.out.println("1. Employees");
            System.out.println("2. Branches");
            System.out.println("3. Constraints");
            System.out.println("4. Shifts");
            System.out.println("5. History");
            System.out.println("6. Exit");

            choice = scanner.nextInt();
            int c = 0;
            switch (choice) {
                case 1:
                    System.out.println("You chose Employees\nPickup a choice:\n");
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
                            system.
                    }

                    break;
                case 2:
                    System.out.println("You chose Option 2.");
                    break;
                case 3:
                    System.out.println("You chose Option 3.");
                    break;
                case 4:
                    System.out.println("Exiting menu...");
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

        // - do shifts
        // - change shift
        // - set shift roles requirements
        // - set (add/remove) shift managers permissions

        // - ask constraints
        // - change constraints`
        // - add role

        // - add new branch
        // - add employee to branch
        // - remove employee from branch



        // 1.2 UPDATE (employees)
        // - add more info about an employee
        // - update terms of employee
        // - ask employees bank account
        // - add qualification to an employee
        // - change employee salary (bonus)





    }
}
