package Module_HR_Part1.src;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;

public class ShiftOrganizer {

    /**
     * This function organized the daily shift every 24 hours.
     */
    public static DailyShift DailyShifts(Hashtable<String, Employee> listEmployees) {

        LocalDate currentDate = LocalDate.now();
        LocalDate nextDate = currentDate.plusDays(1);

        //create a new daily shift
        DailyShift dailyShift = new DailyShift(nextDate);

        //get information from manager
        System.out.println("Hello, Today's date: " + currentDate);
        /*
         * In this part we are asking info from HR manager about the specific shift
         */
        Map<String, Integer> rolesAmount = null;
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
         * First, get tomorrow date
         */
        DayOfWeek tomorrow = LocalDate.now().plusDays(1).getDayOfWeek();
        Locale locale = Locale.ENGLISH;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE", locale);
        String tomorrowString = tomorrow.getDisplayName(TextStyle.FULL, locale);
        /*
         * We will insert those maps to the new DailyShift
         */
        Map<Role, Employee> morningShift = null;
        Map<Role, Employee> eveningShift = null;
        int key;
        boolean[][] constraints;
        Enumeration<String> keys = listEmployees.keys();
        String role = keys.nextElement();
        Map<Days, Integer> days = null;
        Days[] tmp = Days.values();
        for(int d = 0; d < 7; d++){days.put(tmp[d], d);}
        Random random = new Random();
        int shiftChoice;
        /*
        This loop will put the employees at positions by role
         */
        while (keys.hasMoreElements()) {
            if (listEmployees.get(role).getShiftsLimit() > 0 && rolesAmount.get(role) > 0){
                constraints = listEmployees.get(role).getConstraints();
                /*
                 * The employee can work both shifts
                 */
                if(constraints[days.get(tomorrowString)][0] && constraints[days.get(tomorrowString)][1])
                {
                    shiftChoice = random.nextInt(2);
                    if(shiftChoice == 0)
                    {
                        morningShift.put(Role.valueOf(role), listEmployees.get(role));
                    }
                    else {
                        eveningShift.put(Role.valueOf(role), listEmployees.get(role));
                    }
                }
                /*
                 * The employee can work only morning shift
                 */
                else if(constraints[days.get(tomorrowString)][0])
                {
                    morningShift.put(Role.valueOf(role), listEmployees.get(role));
                }
                /*
                 * The employee can work only evening shift
                 */
                else if(constraints[days.get(tomorrowString)][1])
                {
                    eveningShift.put(Role.valueOf(role), listEmployees.get(role));
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
                listEmployees.get(role).setShiftsLimit(listEmployees.get(role).getShiftsLimit() - 1);
                key = rolesAmount.get(role);
                key--;
                rolesAmount.put(role, key);

            }
            role = keys.nextElement();
        }
        /*
        Set the shifts
         */
        dailyShift.setMorningShift(morningShift);
        dailyShift.setEveningShift(eveningShift);

        // Implement your DailyShifts function here
        // This function will be called every 24 hours
        // Ask the constraints from HR system
        // Save the result in a list
        // Run on list and fill the shifts (morning and evening)
        // create new DailyShift and return it.
        return null;
    }
}
