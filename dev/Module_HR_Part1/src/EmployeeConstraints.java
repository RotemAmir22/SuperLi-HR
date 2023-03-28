package Module_HR_Part1.src;

import Module_HR_Part1.src.Employee;

import java.util.Date;
import java.util.Scanner;

public class EmployeeConstraints {


    /**
     * Ask every employee about his constraints and update
     */
    public static void askForConstraints(Employee e)
    {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        int counter = 6; // 7 days a week, include 0
        Days[] days = Days.values();

        System.out.println("Hello " + e.getName());
        while(counter >= 0)
        {
            System.out.println("Please set your choices for " + days[counter]);
            System.out.println("1. Morning shift");
            System.out.println("2. Evening shift");
            System.out.println("3. Both");
            System.out.println("4. Neither");

            // Get user input
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            // Process the user's choice
            switch (choice) {
                case 1:
                    e.setConstrains(counter,0, true); // 0 means morning
                    e.setConstrains(counter,1, false);
                    break;
                case 2:
                    e.setConstrains(counter,1, true); // 1 means evening
                    e.setConstrains(counter,0, false);
                    break;
                case 3:
                    e.setConstrains(counter,0, true); // both OK
                    e.setConstrains(counter,1, true);
                    break;
                case 4:
                    e.setConstrains(counter,0, false);
                    e.setConstrains(counter,1, false);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            counter --;
        }

    }
}
