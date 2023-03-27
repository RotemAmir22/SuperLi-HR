package Module_HR_Part1.src;

import java.io.File;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


public class DailyShift {

    private LocalDate date;
    private File endOfDayReport;
    private Map<Role, Employee> morningShift;
    private Map<Role, Employee> eveningShift;

    //constructor
    public DailyShift(LocalDate date) {
        this.date = date;
        this.morningShift = new HashMap<Role,Employee>();
        this.eveningShift = new HashMap<Role,Employee>();
    }

    //getters
    public LocalDate getDate() {
        return date;
    }
    public Map<Role, Employee> getMorningShift() {
        return morningShift;
    }
    public Map<Role, Employee> getEveningShift() {
        return eveningShift;
    }
    public File getEndOfDayReport() {
        return endOfDayReport;
    }

    //setters
    public void setMorningShift(Map<Role, Employee> morningShift) {
        this.morningShift = morningShift;
    }
    public void setEveningShift(Map<Role, Employee> eveningShift) {
        this.eveningShift = eveningShift;
    }
    public void setEndOfDayReport(File endOfDayReport) {
        this.endOfDayReport = endOfDayReport;
    }

    public void showMeSchedualing()
    {
        DayOfWeek dayOfWeek=this.date.getDayOfWeek();
        if(dayOfWeek.toString().equals("SATURDAY"))
        {
            System.out.println("No shifts for Saturday");
        }
        System.out.println("Daily shift - "+this.date+"\nMORNING:\n");
        Map<Role, Employee> sortedMap = new TreeMap<>(morningShift);
        for (Map.Entry<Role, Employee> entry : sortedMap.entrySet()) {
            Role key = entry.getKey();
            Employee e = entry.getValue();
            System.out.println("["+key.name()+": "+e.getName()+"]\n");
        }
        if(!(dayOfWeek.toString().equals("Friday")))
        {
            System.out.println("EVENING:\n");
            sortedMap = new TreeMap<>(eveningShift);
            for (Map.Entry<Role, Employee> entry : sortedMap.entrySet()) {
                Role key = entry.getKey();
                Employee e = entry.getValue();
                System.out.println("["+key.name()+": "+e.getName()+"]\n");
            }
        }

    }

}
