package Module_HR_Part1.src;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;

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
        if(choice == 1)// add employee
            branchStore.getShiftsHistory().get(date).addEmployeeFromShift(employee, role, shift);

        else //remove employee
        {
            branchStore.getShiftsHistory().get(date).removeEmployeeFromShift(employee, role, shift);
        }
    }

    /**
     * check if the shift is legal
     * @param rolesAmount: to check if all are update to 0
     */
    public static void checkShiftValidation(Map<String, Integer> rolesAmount, int numOfShiftmanagers)
    {
        /*
        force the HR manager to choose shift manager
         */
        if(numOfShiftmanagers != 0)
        {
            for(Role role: Role.values())
            {
                if(rolesAmount.get(role.toString()) > 0)
                {
                    System.out.println("Daily shift is INVALID !!!");
                    break;
                }
            }
        }
        else{System.out.println("Daily shift is invalid - Every shift required a shift-manager!");}

    }

    /**
     * create a new shift manager to a specific shift
     * @param e: the employee for this role
     * @param shiftDate: the current shift
     * @param shiftSlot: morning/evening
     */
    public static void createShiftManager(Employee e, LocalDate shiftDate, int shiftSlot)
    {
        ShiftManagerGeneratore tmp = new ShiftManagerGeneratore();
        tmp.CreateShiftManager(e.getName(), e.getId(),shiftDate,shiftSlot);
    }

    public static void insertIntoMorning(DailyShift dailyShift, Map<Role, Employee> morningShift,Role role, Employee employee)
    {
        morningShift.put(role, employee);
        employee.setShiftsLimit(employee.getShiftsLimit() - 1);
        dailyShift.setMorningShift(morningShift);
    }

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
     * @return suggestion of a daily shift for tomorrow
     */
    public static DailyShift DailyShifts(List<Employee> listEmployees, int[][] openHours, int shift, DailyShift dailyShift) {
        /* Shift means Morning/Evening */
        if(shift !=0 && shift != 1){return null;}
        /* Get the current day, and pull the next for the scheduling */
        LocalDate currentDate = LocalDate.now();
        LocalDate nextDate = currentDate.plusDays(1);
        /* Reset employee's limit of shifts if the week is over */
        if(currentDate.toString().equals("Saturday"))
        {
            for (Employee employee : listEmployees){employee.setShiftsLimit(6);}
        }
        /* First, get tomorrow date */
        DayOfWeek tomorrow = LocalDate.now().plusDays(1).getDayOfWeek();
        Locale locale = Locale.ENGLISH;
        String tomorrowString = tomorrow.getDisplayName(TextStyle.FULL, locale);
        /* Get the day by index (0-6) */
        int day = Days.valueOf(tomorrowString).ordinal();
        /* Check if the branch is open this day */
        if(openHours[day][0] == 1 && openHours[day][1] == 1)
        {
            System.out.println("Tomorrow this branch is close.");
            return null;
        }

        //get information from manager
        System.out.println("Hello, Today's date: " + currentDate);
        /*
         * In this part we are asking info from HR manager about the specific shift
         */
        Map<String, Integer> rolesAmount = new HashMap<String,Integer>();
        Role[] roles = Role.values();
        Scanner scanner = new Scanner(System.in);
        int numOfShiftmanagers = 0;
        int c;
        int i = 0;
        while(i < roles.length)
        {
            try{
                System.out.println("How much "+ roles[i] + " do you need for " + nextDate + " "+Shift.values()[shift].toString()+" shift?");
                c = scanner.nextInt();
                if(Objects.equals(roles[i].toString(), "SHIFTMANAGER")) {numOfShiftmanagers = c;}
                rolesAmount.put(String.valueOf(roles[i]), c);
                i++;
            }
            catch (Exception e)
            {
                System.out.println("Invalid input. Please enter an integer.");
            }

        }
        /*
         * In this part we move on the employees and check who can fill which position
         * We will insert those maps to the new DailyShift
         */
        Map<Role, Employee> currentShift = new HashMap<>();
        int key;
        boolean[][] constraints;
        Map<String, Integer> days = new HashMap<>();
        Days[] tmp = Days.values();
        for(int d = 0; d < 7; d++){days.put(String.valueOf(tmp[d]), d);}
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
                numOfShiftmanagers = amount;
            /* Check if there is need for this role:
               If the employee can do it, and if he doesn't pass his weekly limitation. */
            for (Employee employee : listEmployees) {
                /* get the employees constraints */
                constraints = employee.getConstraints();
                if (amount > 0 && employee.canDoRole(roleName) && employee.getShiftsLimit() > 0 && constraints[days.get(tomorrowString)][shift]) {
                    /* check where the employee can be and update */
                    if(shift == 0 && !dailyShift.isExistEvening(employee))
                    {
                        key = rolesAmount.get(String.valueOf(roleName));
                        key--;
                        rolesAmount.put(String.valueOf(roleName), key);
                        insertIntoMorning(dailyShift, currentShift, roleName, employee);
                        dailyShift.setMorningShift(currentShift);
                    }
                    else if(shift == 1 && !dailyShift.isExistMorning(employee))
                    {
                        key = rolesAmount.get(String.valueOf(roleName));
                        key--;
                        rolesAmount.put(String.valueOf(roleName), key);
                        insertIntoEvening(dailyShift, currentShift,roleName, employee);
                        dailyShift.setEveningShift(currentShift);
                    }
                    else // he can't to either
                        continue;
                    /* check if the current added is a shift-manager */
                    if(currentShift.get(Role.SHIFTMANAGER).getName().equals(employee.getName())) // the last adding
                    {
                        createShiftManager(employee, nextDate, shift);
                    }

                }
            }

        }
        /* Create and set a new shift */
        checkShiftValidation(rolesAmount, numOfShiftmanagers); // check if there is a problem
        return dailyShift; //return a new daily shift
    }
}
