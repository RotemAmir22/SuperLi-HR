package Module_HR_Part1.src;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
public class HR_SystemManagment {
    
    public List<BranchStore> branches;
    public static void main(String[] args) {

        //Create schedules
        Timer timer = new Timer();
        TimerTask dailyShift = new TimerTask() {
            @Override
            public void run() {
                List<Employee> listEmployees = null;
                //for loop that run on the branch list and collect the employees list
                ShiftOrganizer.DailyShifts(listEmployees); // Call the function to run every 24 hours
            }
        };
        // Schedule the task to run every 24 hours
        timer.schedule(dailyShift, 0, 24 * 60 * 60 * 1000);

    }
    /**
     * save cancellations->save in shift manager the details and counter in daily shift
     * save reports
     */
}
