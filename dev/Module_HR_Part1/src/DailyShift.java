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
    private List<ShiftManager> shiftManagers;

    //constructor
    public DailyShift(LocalDate date) {
        this.date = date;
        this.morningShift = new HashMap<Role,Employee>();
        this.eveningShift = new HashMap<Role,Employee>();
        this.shiftManagers= new ArrayList<ShiftManager>();
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
    public List<ShiftManager> getShiftManagers() {return shiftManagers;}

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
    public void addShiftManager(ShiftManager shiftManager) {
        this.shiftManagers.add(shiftManager);
    }
    public void removeShiftManager(ShiftManager shiftManager) {
        this.shiftManagers.remove(shiftManager);
    }


    //change shift
    public void removeEmployeeFromShift(Employee employee, Role role, int shift)
    {

    }
    public void showMeSchedualing()
    {
        DayOfWeek dayOfWeek=this.date.getDayOfWeek();
        System.out.println("Daily shift - "+this.date+"\nMORNING:\n");
        Map<Role, Employee> sortedMap = new TreeMap<>(morningShift);
        for (Map.Entry<Role, Employee> entry : sortedMap.entrySet()) {
            Role key = entry.getKey();
            Employee e = entry.getValue();
            System.out.println("["+key.name()+": "+e.getName()+"]\n");
        }
        System.out.println("EVENING:\n");
        sortedMap = new TreeMap<>(eveningShift);
        for (Map.Entry<Role, Employee> entry : sortedMap.entrySet()) {
            Role key = entry.getKey();
            Employee e = entry.getValue();
            System.out.println("["+key.name()+": "+e.getName()+"]\n");
        }
    }
}
