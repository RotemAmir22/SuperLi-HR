package Presentation;

import BussinesLogic.*;

import java.time.LocalDate;
import java.util.*;

/**
 * This class is in charge of all the shift organization, has user interactions
 * - creating a daily shift
 * - shift changes
 */
public class ShiftOrganizer {

    enum Shift{Morning, Evening}

    /**
     * update shift by HR manager
     * @param branchStore: from which store
     * @param date: what date
     * @param shift: morning -0 evening -1
     * @param choice: add -1 remove-2
     * @param employee: employee to update
     * @param role: what role he is doing
     */
    public static void changeShift(BranchStore branchStore, LocalDate date, int shift, int choice, Employee employee, Role role)
    {
        if(choice == 0)// add employee
            branchStore.getShiftsHistory().get(date).addEmployeeToShift(employee, role, shift);

        else //remove employee
        {
            branchStore.getShiftsHistory().get(date).removeEmployeeFromShift(employee, role, shift);
        }
        branchStore.getShiftsHistory().get(date).showMeSchedualing();
    }

    /**
     * check if the shift is valid
     * if invalid, sends an alert
     * @param rolesAmount: to check if all are update to 0
     */
    public static void checkShiftValidation(Map<String, Integer> rolesAmount)
    {
        Scanner scanner = new Scanner(System.in);
        //go over the roles and check if all of them are fulfilled
        for(Role role: Role.values())
        {
            if(rolesAmount.get(role.toString()) > 0)
            {
                System.out.println("Daily shift is INVALID !!!\nAsk backup from another branch? (y/n)");
                String replay = scanner.nextLine();
                if(Objects.equals(replay, "y")) {
                    System.out.println("ALL BRANCHES: please contact HR manager");
                }
                break;
            }
        }

    }

    /**
     * The main function in this class. Made the scheduling itself
     * @param listEmployees: the branches employees
     * @param openHours: the branches open hours (in this part - day and shift (morning or evening))
     * @param shift : morning - 0 or evening - 1shift
     * @param dailyShift : daily shift to update the inner shifts
     * @param weekDay : what day to schedule the shift
     * @return suggestion of a daily shift for the date requested
     */
    public static DailyShift DailyShifts(List<Employee> listEmployees, int[][] openHours, int shift, DailyShift dailyShift, Days weekDay) {
       //check if morning or evening shift
       if(shift !=0 && shift != 1){return null;}
       /* Get the current date, and pull the vent date for scheduling */
        LocalDate currentDate = LocalDate.now();
        LocalDate shiftDate = currentDate.plusDays(weekDay.ordinal()+2);
        /* Reset employee's limit of shifts if the week is over */
        if(currentDate.toString().equals("Saturday"))
        {
            for (Employee employee : listEmployees){employee.setShiftsLimit(6);}
        }
        /* Get the day by index (0-6) */
        int day = shiftDate.getDayOfWeek().ordinal();
        /* Check if the branch is open this day */
        if(openHours[day][0] == 1 && openHours[day][1] == 1)
        {
            System.out.println("On "+ shiftDate.plusDays(1).getDayOfWeek().toString() +" this branch is close.");
            return null;
        }
        //get information from manager
        System.out.println("REMINDER, Today's date: " + currentDate);
        /*
         * In this part we are asking info from HR manager about the specific shift
         */
        Map<String, Integer> rolesAmount = new HashMap<>();
        Role[] roles = Role.values();
        Scanner scanner = new Scanner(System.in);
        int numOfShiftManagers = 0;
        String c;
        int i = 0;
        while(i < roles.length)
        {
            try{
                System.out.println("How much "+ roles[i] + " do you need for " + shiftDate + " "+Shift.values()[shift].toString()+" shift?");
                c = scanner.nextLine();
                //update role amount - check if it's a positive integer
                if(Integer.parseInt(c) < 0) {throw new Exception("");}
                rolesAmount.put(String.valueOf(roles[i]), Integer.parseInt(c));
                //check how many shift managers are in the shift
                if(Objects.equals(roles[i].toString(), "SHIFTMANAGER"))
                {
                    numOfShiftManagers = Integer.parseInt(c);
                    if(numOfShiftManagers < 1){
                        System.out.println("Invalid input. Please enter at least one shift manager.");
                        continue;
                    }
                }
                i++;
            }
            catch (Exception e)
            {
                System.out.println("Invalid input. Please enter an integer.");
            }

        }
        /*
         * In this part we go over the employees and check who can fill which position
         * We will insert those maps to the new DailyShift
         */
        int key;
        boolean[][] constraints;

        Role roleName;
        int amount;
        /*
        This loop will put the employees at positions by role
         */
        for (Map.Entry<String, Integer> entry : rolesAmount.entrySet())
        {
            roleName = Role.valueOf(entry.getKey());
            amount = entry.getValue();
            /* Check if there is need for this role:
               If the employee can do it, and if he doesn't pass his weekly limitation. */
            if(amount <=0)
                continue;
            for (Employee employee : listEmployees) {
                /* get the employees constraints */
                constraints = employee.getConstraints();
                if (entry.getValue() > 0 && employee.canDoRole(roleName) && employee.getShiftsLimit() > 0 && constraints[shiftDate.getDayOfWeek().ordinal()][shift])
                {
                    /* check where the employee can be and update */
                    boolean addToShift = dailyShift.addEmployeeToShift(employee, roleName, shift);
                    if(addToShift)
                    {
                        key = rolesAmount.get(String.valueOf(roleName));
                        key--;
                        rolesAmount.put(String.valueOf(roleName), key);
                    }


                }
            }

        }
        /* Create and set a new shift */
        checkShiftValidation(rolesAmount); // check if there is a problem
        return dailyShift; //return a new daily shift
    }
}
