package Module_HR;

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
    }

    /**
     * check if the shift is valid
     * if invalid, sends an alert
     * @param rolesAmount: to check if all are update to 0
     */
    public static void checkShiftValidation(Map<String, Integer> rolesAmount, int numOfShiftManagers)
    {
        Scanner scanner = new Scanner(System.in);
        /*
        force the HR manager to choose shift manager
         */
        if(numOfShiftManagers != 0)
        {
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
        else{System.out.println("Daily shift is INVALID - Every shift requires a shift-manager!");}

    }

    /**
     * create a new shift manager to a specific shift
     * @param e: the employee for this role
     * @param shiftDate: the current shift
     * @param shiftSlot: morning/evening
     */
    public static void createShiftManager(Employee e, LocalDate shiftDate, int shiftSlot, DailyShift currentShift)
    {
        ShiftManagerGenerator tmp = new ShiftManagerGenerator();
        currentShift.addShiftManager(tmp.CreateShiftManager(e.getName(), e.getId(),shiftDate,shiftSlot));
    }

    /**
     * add employee to morning daily shift
     * @param dailyShift : shift to add to
     * @param morningShift: gets the map needed
     * @param role : what role the employee needs to do
     * @param employee : employee to add to morning shift
     */
    public static void insertIntoMorning(DailyShift dailyShift, Map<Role, Employee> morningShift,Role role, Employee employee)
    {
        morningShift.put(role, employee);
        employee.setShiftsLimit(employee.getShiftsLimit() - 1);
        dailyShift.setMorningShift(morningShift);
    }

    /**
     * add employee to evening daily shift
     * @param dailyShift : shift to add to
     * @param eveningShift: gets the map needed
     * @param role : what role the employee needs to do
     * @param employee : employee to add to morning shift
     */
    public static void insertIntoEvening(DailyShift dailyShift, Map<Role, Employee> eveningShift,Role role, Employee employee)
    {
        eveningShift.put(role, employee);
        employee.setShiftsLimit(employee.getShiftsLimit() - 1);
        dailyShift.setEveningShift(eveningShift);
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
    public static DailyShift DailyShifts(List<Employee> listEmployees, int[][] openHours, int shift, DailyShift dailyShift,Days weekDay) {
       //check if morning or evening shift
       if(shift !=0 && shift != 1){return null;}
       /* Get the current date, and pull the vent date for scheduling */
        LocalDate currentDate = LocalDate.now();
        LocalDate nextDate = currentDate.plusDays(weekDay.ordinal()+2);
        /* Reset employee's limit of shifts if the week is over */
        if(currentDate.toString().equals("Saturday"))
        {
            for (Employee employee : listEmployees){employee.setShiftsLimit(6);}
        }
        /* Get the day by index (0-6) */
        int day = nextDate.plusDays(1).getDayOfWeek().ordinal();
        /* Check if the branch is open this day */
        if(openHours[day][0] == 1 && openHours[day][1] == 1)
        {
            System.out.println("Tomorrow this branch is close.");
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
        int c;
        int i = 0;
        while(i < roles.length)
        {
            try{
                System.out.println("How much "+ roles[i] + " do you need for " + nextDate + " "+Shift.values()[shift].toString()+" shift?");
                c = scanner.nextInt();

                //check how many shift managers are in the shift
                if(Objects.equals(roles[i].toString(), "SHIFTMANAGER"))
                {
                    numOfShiftManagers = c;
                }

                //update role amount
                rolesAmount.put(String.valueOf(roles[i]), c);
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
        Map<Role, Employee> currentShift = new HashMap<>();
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
            if(roleName == Role.SHIFTMANAGER)
                numOfShiftManagers = amount;
            /* Check if there is need for this role:
               If the employee can do it, and if he doesn't pass his weekly limitation. */
            for (Employee employee : listEmployees) {
                /* get the employees constraints */
                constraints = employee.getConstraints();
                if (amount > 0 && employee.canDoRole(roleName) && employee.getShiftsLimit() > 0 && constraints[nextDate.getDayOfWeek().ordinal()][shift]) {
                    /* check where the employee can be and update */
                    if(shift == 0 && !dailyShift.isExistEvening(employee))
                    {
                        key = rolesAmount.get(String.valueOf(roleName));
                        key--;
                        rolesAmount.put(String.valueOf(roleName), key);
                        insertIntoMorning(dailyShift, currentShift, roleName, employee);
                        dailyShift.setMorningShift(currentShift);
                        employee.setCumulativeSalary(employee.getCumulativeSalary() + employee.getSalary());
                    }
                    else if(shift == 1 && !dailyShift.isExistMorning(employee))
                    {
                        key = rolesAmount.get(String.valueOf(roleName));
                        key--;
                        rolesAmount.put(String.valueOf(roleName), key);
                        insertIntoEvening(dailyShift, currentShift,roleName, employee);
                        dailyShift.setEveningShift(currentShift);
                        employee.setCumulativeSalary(employee.getCumulativeSalary() + employee.getSalary());
                    }
                    else // he can't to either
                        continue;
                    /* check if the current added is a shift-manager */
                    if(currentShift.get(Role.SHIFTMANAGER).getName().equals(employee.getName())) // the last adding
                    {
                        createShiftManager(employee, nextDate, shift, dailyShift);
                    }

                }
            }

        }
        /* Create and set a new shift */
        checkShiftValidation(rolesAmount, numOfShiftManagers); // check if there is a problem
        return dailyShift; //return a new daily shift
    }
}
