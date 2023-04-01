package Module_HR;

import java.io.File;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;


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


    /*change shift
    * remove from shift
    * remove from morning shift
    */
    public void removeEmployeeFromMorning(Employee employee, Role role)
    {
        this.morningShift.remove(role,employee);
        //update limit
        employee.setShiftsLimit(employee.getShiftsLimit()+1);
    }

    //remove from evening shift
    public void removeEmployeeFromEvening(Employee employee, Role role)
    {
        this.eveningShift.remove(role,employee);
        //update limit
        employee.setShiftsLimit(employee.getShiftsLimit()+1);
    }
    //according to shift this function refers to the right helper
    public void removeEmployeeFromShift(Employee employee, Role role, int shift)
    {
        if(role == Role.SHIFTMANAGER)
        {
            ShiftManager shiftManager = findEmployeeInShiftManager(employee.getId());
            if(shiftManager != null)
                removeShiftManager(shiftManager);
        }

        if(shift == 0)
            removeEmployeeFromMorning(employee,role);
        else
            removeEmployeeFromEvening(employee, role);
    }

    /* add to shift
    * remove from morning shift
    */
    public void addEmployeeToMorning(Employee employee, Role role)
    {
        this.morningShift.put(role,employee);
        //update limit
        employee.setShiftsLimit(employee.getShiftsLimit()-1);
    }

    //remove from evening shift
    public void addEmployeeToEvening(Employee employee, Role role)
    {
        this.eveningShift.put(role,employee);
        //update limit
        employee.setShiftsLimit(employee.getShiftsLimit()-1);
    }
    //according to shift this function refers to the right helper
    public void addEmployeeToShift(Employee employee, Role role, int shift)
    {
        if(shift == 0)
            addEmployeeToMorning(employee,role);
        else
            addEmployeeToEvening(employee, role);
    }

    public ShiftManager findEmployeeInShiftManager(String ID)
    {
        for (ShiftManager shiftManager : shiftManagers) {
            if (shiftManager.getId()==ID) {
                return shiftManager;
            }
        }
        return null;
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

    public boolean isExistMorning(Employee e)
    {
        return this.morningShift.containsValue(e);
    }

    public boolean isExistEvening(Employee e)
    {
        return this.eveningShift.containsValue(e);
    }
}
