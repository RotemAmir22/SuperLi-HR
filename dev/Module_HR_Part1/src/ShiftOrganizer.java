package Module_HR_Part1.src;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;

public class ShiftOrganizer {

    /**
     * This function publish a suggestion for a daily shift every 24 hours.
     */
    public static DailyShift DailyShifts(List<Employee> listEmployees, int[][] openHours) {
        /*
        Get the current day, and pull the next for the scheduling
         */
        LocalDate currentDate = LocalDate.now();
        LocalDate nextDate = currentDate.plusDays(1);

        //create a new daily shift
        DailyShift dailyShift = new DailyShift(nextDate);
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
        int c;
        int i = 0;
        while(i < roles.length)
        {
            try{
                System.out.println("How much "+ roles[i] + " do you need for " + nextDate + " shift");
                c = scanner.nextInt();
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
        Map<Role, Employee> morningShift = new HashMap<>();
        Map<Role, Employee> eveningShift = new HashMap<>();
        int key;
        boolean[][] constraints;
        Map<String, Integer> days = new HashMap<>();
        Days[] tmp = Days.values();
        for(int d = 0; d < 7; d++){days.put(String.valueOf(tmp[d]), d);}
        Role roleName;
        int amount;
        Random random = new Random();
        int shiftChoice;
        /*
        This loop will put the employees at positions by role
         */
        for (Map.Entry<String, Integer> entry : rolesAmount.entrySet())
        {
            roleName = Role.valueOf(entry.getKey());
            amount = entry.getValue();
            /*
            Check if there is need for this role, if the employee can do it, and if he doesn't pass his weekly limitation
             */
            if (amount > 0 && listEmployees.get(i).canDoRole(roleName) && listEmployees.get(i).getShiftsLimit() > 0 ){
                constraints = listEmployees.get(i).getConstraints();
                /*
                 * The employee can work both shifts
                 */
                if(constraints[days.get(tomorrowString)][0] && constraints[days.get(tomorrowString)][1] && openHours[day][0] == 0 && openHours[day][1] == 0)
                {
                    shiftChoice = random.nextInt(2);
                    if(shiftChoice == 0)
                    {
                        morningShift.put(roleName, listEmployees.get(i));
                    }
                    else {
                        eveningShift.put(roleName, listEmployees.get(i));
                    }
                }
                /*
                 * The employee can work only morning shift
                 */
                else if(constraints[days.get(tomorrowString)][0] && openHours[day][0] == 0)
                {
                    morningShift.put(roleName, listEmployees.get(i));
                }
                /*
                 * The employee can work only evening shift
                 */
                else if(constraints[days.get(tomorrowString)][1] && openHours[day][1] == 0)
                {
                    eveningShift.put(roleName, listEmployees.get(i));
                }
                /*
                 * The employee can't work either
                 */
                else{continue;}
                /*
                 * Set the limitation of the employee
                 * He can't work more than 6 shifts a week
                 * Also set the needed roles to be -1
                 */
                listEmployees.get(i).setShiftsLimit(listEmployees.get(i).getShiftsLimit() - 1);
                key = rolesAmount.get(String.valueOf(roleName));
                key--;
                rolesAmount.put(String.valueOf(roleName), key);
            }
        }
        /*
        Set the shifts
         */
        dailyShift.setMorningShift(morningShift);
        dailyShift.setEveningShift(eveningShift);

        return dailyShift;
    }
}
